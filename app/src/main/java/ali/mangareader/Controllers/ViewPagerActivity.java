package ali.mangareader.Controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ali.mangareader.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewPagerActivity extends FragmentActivity {

    @Bind(R.id.note)
    TextView note;
    @Bind(R.id.text)
    RelativeLayout text;
    @Bind(R.id.pager)
    ViewPager pager;
    private ArrayList<String> IMAGES;
    String name;
    String id;
    private ViewPager page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_pages);
        ButterKnife.bind(this);
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        page = (ViewPager) findViewById(R.id.pager);
        IMAGES = (ArrayList<String>) getIntent().getSerializableExtra("Pages");
        name = getIntent().getStringExtra("name");
        id = (String) getIntent().getSerializableExtra("id");
        page.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @OnClick(R.id.note)
    public void onClick() {
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ViewPagerFragment fragment = new ViewPagerFragment();
            fragment.setAsset(IMAGES.get(position), name, id,IMAGES.size(), note);
            return fragment;
        }

        @Override
        public int getCount() {
            return IMAGES.size();
        }
    }


}