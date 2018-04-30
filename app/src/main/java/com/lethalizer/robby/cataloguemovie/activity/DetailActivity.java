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

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_release_date) TextView tvReleaseDate;
    @BindView(R.id.tv_overview) TextView tvOverview;
    @BindView(R.id.tv_rating) TextView tvRating;
    @BindView(R.id.img_detail_poster) ImageView imgDetailPoster;
    @BindView(R.id.layout_detail_content) LinearLayout layoutDetailContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setTitle("Detail Movie");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        Movie movie = (Movie) getIntent().getSerializableExtra("extra_movie");

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvReleaseDate.setText(movie.getReleaseDate());
        tvRating.setText(movie.getScore());

        Glide.with(this)
                .load(movie.getPosterPath())
                .into(imgDetailPoster);

    }


}
