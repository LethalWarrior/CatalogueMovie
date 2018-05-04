package com.lethalizer.robby.cataloguemovie.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.lethalizer.robby.cataloguemovie.R;
import com.lethalizer.robby.cataloguemovie.adapter.SearchMovieAdapter;
import com.lethalizer.robby.cataloguemovie.adapter.TabFragmentAdapter;
import com.lethalizer.robby.cataloguemovie.fragment.NowPlayingFragment;
import com.lethalizer.robby.cataloguemovie.fragment.SearchFragment;
import com.lethalizer.robby.cataloguemovie.fragment.UpcomingFragment;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity{

    private static SharedPreferences preferences;
    private static Locale locale;
    private TabFragmentAdapter adapter;

    @BindView(R.id.tab_container)
    TabLayout tabContainer;

    @BindView(R.id.pager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        ButterKnife.bind(this);

        setupViewPager();

        tabContainer.setTabTextColors(getResources().getColor(R.color.colorPrimaryDark),
                getResources().getColor(android.R.color.white));
        tabContainer.setupWithViewPager(pager);
        tabContainer.setTabGravity(TabLayout.GRAVITY_FILL);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_language:
                showChangeLanguageDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showChangeLanguageDialog() {
        final String[] listItems = {"English", "Indonesian"};

        int checkedItem = 0;
        preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String lang = preferences.getString("My_Lang", "");
        if(lang.equals("in")) checkedItem = 1;

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(getResources().getString(R.string.title_dialog_lang_setting))
                .setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0) {
                            setLocale("en");
                            recreate();
                        }
                        else if(which == 1) {
                            setLocale("in");
                            recreate();
                        }
                    }
                });
        builder.show();
    }

    private void setLocale(String lang) {

        locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

    }

    public void loadLocale() {
        preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String lang = preferences.getString("My_Lang", "");
        setLocale(lang);
    }

    private void setupViewPager() {
        adapter = new TabFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new NowPlayingFragment(), getResources().getString(R.string.first_main_tab));
        adapter.addFragment(new UpcomingFragment(), getResources().getString(R.string.second_main_tab));
        adapter.addFragment(new SearchFragment(), getResources().getString(R.string.third_main_tab));
        pager.setAdapter(adapter);
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }

}
