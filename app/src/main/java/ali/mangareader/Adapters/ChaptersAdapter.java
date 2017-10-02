package ali.mangareader.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ali.mangareader.Controllers.ViewPagerActivity;
import ali.mangareader.R;
import ali.mangareader.Taskbackup;
import ali.mangareader.entities.Chapter;
import ali.mangareader.entities.ChapterDetalied;
import me.grantland.widget.AutofitHelper;


public class ChaptersAdapter
        extends RecyclerView.Adapter<ChaptersAdapter.ChapterHolder> {

    //Private data members
    private ArrayList<Chapter> data;
    private Context context;
    String site;
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();

    //Constructor:
    public ChaptersAdapter(ArrayList<Chapter> data, Context context, String site) {
        this.data = data;
        this.site = site;
        this.context = context;
    }


    @Override
    public ChapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.chapter_item,
                parent, false);
        return new ChapterHolder(itemView);
    }

    public void onBindViewHolder(ChapterHolder holder, int position) {

        Chapter currentItem = data.get(position);

        if (holder.chaptersName != null) {
            holder.chaptersName.setText(currentItem.getName());
            AutofitHelper.create(holder.chaptersName);
        }
        if (holder.chaptersID != null) {
            holder.chaptersID.setText(currentItem.getChapterId());
            AutofitHelper.create(holder.chaptersID);
        }
        if (holder.layout != null)
            holder.layout.setOnClickListener(holder);

        YoYo.with(Techniques.BounceIn).playOn(holder.view);


    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ChapterHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        View view;
        TextView chaptersName;
        TextView chaptersID;
        CardView layout;

        public ChapterHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            chaptersName = (TextView) itemView.findViewById(R.id.chaptersName);
            chaptersID = (TextView) itemView.findViewById(R.id.chaptersnumber);
            layout = (CardView) itemView.findViewById(R.id.chapter);

        }


        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    startnewActivity();
                }
            }).start();


        }

        private void startnewActivity() {
            try {
                int position = getAdapterPosition();
                Chapter chapter = data.get(position);
                String url = site + "/manga/" + chapter.MangaName() + "/" + chapter.getChapterId();
                String chapters = new Taskbackup().execute(url).get();
                ChapterDetalied chapterDetalied = gson.fromJson(chapters, ChapterDetalied.class);
                ArrayList<String> pages = new ArrayList<>();
                int i = 0;
                int size = chapterDetalied.getPages().size();
                while (i < size) {
                    pages.add(chapterDetalied.getPages().get(i).getUrl());
                    i++;
                }
                Intent intent = new Intent(context, ViewPagerActivity.class);
                intent.putExtra("Pages", pages);
                intent.putExtra("name", chapter.getName()).putExtra("id",chapter.getChapterId());
                context.startActivity(intent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}