package ali.mangareader;

import android.os.AsyncTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.net.MalformedURLException;
import ali.mangareader.Utils.HTTPUtils;
import ali.mangareader.Utils.IOUtils;


public class Taskbackup extends AsyncTask<String, Void, String> {

    String jsono;

    @Override
    protected String doInBackground(String... params) {
        String sjson ="";
        try {

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
             sjson = IOUtils.readStream(HTTPUtils.getMangaStream(params[0].toString()));
            return sjson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sjson;
    }


}
