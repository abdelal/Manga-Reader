package ali.mangareader.Controllers;

import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ali.mangareader.Adapters.Radapter;
import ali.mangareader.R;
import ali.mangareader.Taskbackup;
import ali.mangareader.dbaccess.Dao;
import ali.mangareader.entities.Manga;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MangListActivity extends AppCompatActivity {

    @Bind(R.id.rcmain)
    RecyclerView rcmain;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    public final int NUNBER_OF_ITEMS_TO_LOAD = 55;
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    String site = "mangareader.net";
    String mainsite = "mangareader.net?cover=1";
    ArrayList<Manga> mangas=new ArrayList<Manga>();
    ArrayList<Manga> manga;
    int numberofloadeditems;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    boolean notreached = true;
    Radapter radapter;
   // Dao dao= Dao.getSharedInstance(this);


     String jsons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        YoYo.with(Techniques.Landing).playOn(fab);

        try {

            jsons = new Taskbackup().execute(mainsite).get();
/*
        Cursor cu = dao.getCursor();
            while (cu.moveToNext()){
                System.out.println(jsons);
                System.out.println(cu.getString(cu.getColumnIndex("Value")));
            }
*/



            init();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void populate(JSONArray json) {
        for (int i = 0; i < NUNBER_OF_ITEMS_TO_LOAD; i++) {
            if (i + numberofloadeditems < json.length()) {
                JSONObject child = null;
                try {
                    child = json.getJSONObject(i + numberofloadeditems);
                    String childs = child.toString();
                    Manga manga = gson.fromJson(childs, Manga.class);/*
                    System.out.println(manga.getName());*/
                    mangas.add(manga);
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
                radapter.notifyDataSetChanged();
            }
        });
    }

    public void init() throws ExecutionException, InterruptedException, JSONException {

        final JSONArray json = new JSONArray(jsons);

        populate(json);
        manga = new ArrayList<>();
        radapter = new Radapter(mangas, this, site);
        rcmain.setAdapter(radapter);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rcmain.setLayoutManager(gridLayoutManager);
        }
        rcmain.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = rcmain.getChildCount();
                totalItemCount = gridLayoutManager.getItemCount();
                firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    numberofloadeditems = mangas.size();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            populate(json);
                        }
                    }).start();
                    notifyUIThread();
                    notifyUIThread();
                    loading = true;
                }
            }
        });
    }


    @OnClick(R.id.rcmain)
    public void onClick() {
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
    }


}
