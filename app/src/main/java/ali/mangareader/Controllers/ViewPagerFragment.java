package ali.mangareader.Controllers;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.concurrent.ExecutionException;

import ali.mangareader.R;

public class ViewPagerFragment extends Fragment {

    private static final String BUNDLE_ASSET = "asset";

    private String url;
    private String name;
    private String id;
    private int number;
    private String filename;
    private TextView note;
    public ViewPagerFragment() {
    }

    public void setAsset(String asset, String name, String id, int number, TextView note) {
        this.name = name;
        this.id = id;
        this.number = number;
        this.url = asset;
        filename = name + id + number;
        this.note=note;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_pages, container, false);


         new Thread(new Runnable() {
             @Override
             public void run() {
                 try {
                     loaditems(rootView);
                 } catch (ExecutionException e) {
                     e.printStackTrace();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
         }).start();
        return rootView;
    }

    private void loaditems(View rootView) throws ExecutionException, InterruptedException {
       final SubsamplingScaleImageView imageView = (SubsamplingScaleImageView) rootView.findViewById(R.id.imageView);

      Ion.with(getActivity())
                   .load(url).asBitmap().setCallback(new FutureCallback<Bitmap>() {
          @Override
          public void onCompleted(Exception e, final Bitmap result) {
              if (e == null){
                 getActivity().runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         imageView.setImage(ImageSource.bitmap(result));
                         note.setText("chapter Number :"+id);
                     }
                 });

          }}
      });


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        View rootView = getView();
        if (rootView != null) {
            outState.putString(BUNDLE_ASSET, url);
        }
    }

}