package com.lethalizer.robby.cataloguemovie.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lethalizer.robby.cataloguemovie.R;
import com.lethalizer.robby.cataloguemovie.adapter.TabFragmentAdapter;
import com.lethalizer.robby.cataloguemovie.fragment.NowPlayingFragment;
import com.lethalizer.robby.cataloguemovie.fragment.SearchFragment;
import com.lethalizer.robby.cataloguemovie.fragment.UpcomingFragment;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.tab_container)
    TabLayout tabContainer;

    @BindView(R.id.pager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupViewPager();

        tabContainer.setTabTextColors(getResources().getColor(R.color.colorPrimaryDark),
                getResources().getColor(android.R.color.white));
        tabContainer.setupWithViewPager(pager);
        tabContainer.setTabGravity(TabLayout.GRAVITY_FILL);

    }

    private void setupViewPager() {
        TabFragmentAdapter adapter = new TabFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new NowPlayingFragment(), "Now Playing");
        adapter.addFragment(new UpcomingFragment(), "Upcoming");
        adapter.addFragment(new SearchFragment(), "Search Movie");
        pager.setAdapter(adapter);
    }

}
