package com.lethalizer.robby.cataloguemovie.activity;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lethalizer.robby.cataloguemovie.R;
import com.lethalizer.robby.cataloguemovie.model.Movie;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvReleaseDate, tvOverview, tvRating;
    private ImageView imgDetailPoster;
    private LinearLayout layoutDetailContent;
    private ListView lvCast;

    private static final String EXTRA_MOVIE_ID = "extra_movie_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setTitle("Detail Movie");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        tvOverview = (TextView) findViewById(R.id.tv_overview);
        tvRating = (TextView)findViewById(R.id.tv_rating);

        //imgBackdrop = (ImageView) findViewById(R.id.img_backdrop);
        imgDetailPoster = (ImageView) findViewById(R.id.img_detail_poster);

        layoutDetailContent = (LinearLayout) findViewById(R.id.layout_detail_content);

        Movie movie = (Movie) getIntent().getSerializableExtra(MainActivity.EXTRA_MOVIE);

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvReleaseDate.setText(movie.getReleaseDate());
        tvRating.setText(movie.getScore());

        Glide.with(this)
                .load(movie.getPosterPath())
                .into(imgDetailPoster);
        /*Glide.with(this)
                .load(movie.getBackdropPath())
                .into(imgBackdrop);*/

    }


}
