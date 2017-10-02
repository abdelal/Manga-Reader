package ali.mangareader.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ali.mangareader.entities.Manga;

/**
 * Created by master on 20/12/15.
 */
public class IOUtils {

    public final int NUNBER_OF_ITEMS_TO_LOAD = 30;
    int loaded = NUNBER_OF_ITEMS_TO_LOAD;
    static GsonBuilder builder = new GsonBuilder();
    static Gson gson = builder.create();

    public static String readStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();

        String line = null;

        while ((line = reader.readLine()) != null) {
            total.append(line);
        }
        reader.close();
        return total.toString();
    }

    public static Object getJavaFromJson(String tojava,Object object ){

        return gson.fromJson(tojava,object.getClass());

    }



    public static void loadmoreitems(ArrayList<Manga> mangas, JSONArray json) throws JSONException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        for (int i = 0; i < json.length(); i++) {
            JSONObject child = json.getJSONObject(i);
            String childs = child.toString();
            Manga manga = gson.fromJson(childs, Manga.class);
            mangas.add(manga);
            System.out.println(mangas.get(i).getName());
        }


    }
}
