package ali.mangareader.Controllers;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ali.mangareader.Adapters.ChaptersAdapter;
import ali.mangareader.R;
import ali.mangareader.Taskbackup;
import ali.mangareader.Utils.IOUtils;
import ali.mangareader.entities.Chapter;
import ali.mangareader.entities.MangaDetailed;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import me.grantland.widget.AutofitHelper;
import me.grantland.widget.AutofitTextView;

public class MangaDetaliedActivity extends AppCompatActivity {

    /*    @Bind(R.id.toolbar)
        Toolbar toolbar;*/
    @Bind(R.id.coverdetailed)
    ImageView coverdetailed;
    @Bind(R.id.chaptersRC)
    RecyclerView chaptersRC;
    @Bind(R.id.nameD)
    TextView nameD;
    @Bind(R.id.genre)
    TextView genre;
    @Bind(R.id.fabManga)
    FloatingActionButton fab;
    String site;
    MangaDetailed manga;
    ChaptersAdapter adapter;
    ArrayList<Chapter> chapters;
    @Bind(R.id.info)
    TextView info;
    @Bind(R.id.datanotfound)
    AutofitTextView datanotfound;
    private Gson gson;
    public final int NUNBER_OF_ITEMS_TO_LOAD = 20;
    int numberofloadeditems;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    JSONArray json;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_detalied);
        ButterKnife.bind(this);

        String mangas = getIntent().getExtras().getString("manga");
        manga = new MangaDetailed();
        chapters = new ArrayList<Chapter>();
        manga = (MangaDetailed) IOUtils.getJavaFromJson(mangas, manga);

    }

    @OnClick(R.id.fabManga)
    void showInfo() {
        if (manga.getInfo() != null) {
            final MaterialDialog mMaterialDialog = new MaterialDialog(this);
            mMaterialDialog.setTitle("Info")
                    .setMessage(manga.getInfo())
                    .setPositiveButton("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();

                        }
                    });
            mMaterialDialog.show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            YoYo.with(Techniques.Landing).playOn(fab);
            loaditems();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    void loaditems() throws ExecutionException, InterruptedException, JSONException {

        nameD.setText(manga.getName());
        genre.setText(manga.getGenres().toString());
        Picasso.with(this).load(manga.getCover()).into(coverdetailed);
        YoYo.with(Techniques.RotateIn).playOn(coverdetailed);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        String href = "/manga/" + manga.getHref();
        site = "mangareader.net";

        String jsonS = new Taskbackup().execute(site + href).get();
        JSONObject jsonObject = new JSONObject(jsonS);


        if (jsonObject.has("error")) {
            site = "mangafox.me";
            jsonS = new Taskbackup().execute(site + href).get();
            jsonObject = new JSONObject(jsonS);
        }
        if (jsonObject.has("error")) {
            site = "mangastream.com";
            jsonS = new Taskbackup().execute(site + href).get();
            jsonObject = new JSONObject(jsonS);
        }
        if (!jsonObject.has("error")) {
            final JSONObject finalJsonObject = jsonObject;
            json = jsonObject.getJSONArray("chapters");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        load(layoutManager);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            datanotfound.setText("Cant find Chapters");

            AutofitHelper.create(datanotfound);
        }

    }

    private void load(final LinearLayoutManager layoutManager) throws JSONException {
        // final JSONArray json = jsonObject.getJSONArray("chapters");
        new Thread(new Runnable() {
            @Override
            public void run() {
                populate(json);
            }
        }).start();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {


                adapter = new ChaptersAdapter(chapters, context, site);
                chaptersRC.setAdapter(adapter);
                chaptersRC.setLayoutManager(layoutManager);
                chaptersRC.addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        visibleItemCount = chaptersRC.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                        if (loading) {
                            if (totalItemCount > previousTotal) {
                                loading = false;
                                previousTotal = totalItemCount;
                            }
                        }
                        if (!loading && (totalItemCount - visibleItemCount)
                                <= (firstVisibleItem + visibleThreshold)) {
                            // End has been reached
                            numberofloadeditems = chapters.size();
                            //    if(manga.size()==0)
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    populate(json);
                                }
                            }).start();
                            notifyUIThread();
                            loading = true;
                        }
                    }
                });
            }
        });
    }

    public void populate(JSONArray json) {
        Gson gson = new Gson();

        for (int i = 0; i < NUNBER_OF_ITEMS_TO_LOAD; i++) {
            if (i + numberofloadeditems < json.length()) {
                JSONObject child = null;
                try {
                    child = json.getJSONObject(i + numberofloadeditems);
                    String childs = child.toString();
                    Chapter chapter = gson.fromJson(childs, Chapter.class);
                    chapter.setManganame(manga.getHref());
                    chapters.add(chapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void notifyUIThread() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

}
