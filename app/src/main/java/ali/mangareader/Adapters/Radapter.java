package ali.mangareader.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ali.mangareader.Controllers.MangaDetaliedActivity;
import ali.mangareader.R;
import ali.mangareader.Taskbackup;
import ali.mangareader.entities.Manga;
import me.grantland.widget.AutofitHelper;
import me.grantland.widget.AutofitTextView;


public class Radapter
        extends RecyclerView.Adapter<Radapter.MangaHolder> {

    //Private data members
    private ArrayList<Manga> data;
    private Context context;
    String site;

    //Constructor:
    public Radapter(ArrayList<Manga> data, Context context,String site) {
        this.data = data;
        this.site=site;
        this.context = context;
    }


    @Override
    public MangaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_in_main_mangalist,
                parent, false);
        return new MangaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MangaHolder holder, int position) {
        Manga currentItem = data.get(position);
        holder.tvtitle.setText(currentItem.getName());

        holder.cardView.setOnClickListener(holder);
       // if (currentItem.getInfo() != null)
              if (currentItem.getCover().length() != 0)
                Picasso.with(context).load(currentItem.getCover()).into(holder.ivcover);
         YoYo.with(Techniques.BounceIn).playOn(holder.view);


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public  class MangaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivcover;
        AutofitTextView tvtitle;
        CardView cardView;
        View view;

        public MangaHolder(View itemView) {
            super(itemView);
            ivcover = (ImageView) itemView.findViewById(R.id.cover);
            tvtitle = (AutofitTextView) itemView.findViewById(R.id.manganame);
            AutofitHelper.create(tvtitle);
            cardView=(CardView) itemView.findViewById(R.id.imagecard);
            this.view=itemView;

        }


        @Override
        public void onClick(View v) {
            try {
            int position = getAdapterPosition();
            Manga manga = data.get(position);

                YoYo.with(Techniques.Bounce).playOn(view);

            String url= site+"/manga/"+manga.getMangaId()+"/";
                final String mangas=new Taskbackup().execute(url).get();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(context,MangaDetaliedActivity.class);
                        intent.putExtra("manga", mangas);
                        context.startActivity(intent);
                    }
                }).start();


            }  catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


        }


    }
}