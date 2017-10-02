package ali.mangareader.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


public class HTTPUtils {

    public static InputStream getInputStrem(String url) throws IOException {
        URL mUrl = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) mUrl.openConnection();
        InputStream in = con.getInputStream();

        return in;
    }


    public static InputStream getMangaStream(String site) throws IOException {

        HttpClient client=new DefaultHttpClient();
        HttpGet request = new HttpGet("https://doodle-manga-scraper.p.mashape.com/"+site);
        request.addHeader("X-Mashape-Key", "tt5jWuklk5msh6gISItSfzIresRvp1R4GNRjsnSZDCWRx9i94I");
        request.addHeader("Accept", "text/plain");
         HttpResponse execute = client.execute(request);
        InputStream content = execute.getEntity().getContent();

        return content;

    }
/*
    public static Bitmap loadBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;

        try {
            in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);

            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);

            copy(in, out);
            out.flush();

            final byte[] data = dataStream.toByteArray();
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 1;

            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (IOException e) {
        } finally {
            closeStream(in);
            closeStream(out);
        }

        return bitmap;
    }*/



}







