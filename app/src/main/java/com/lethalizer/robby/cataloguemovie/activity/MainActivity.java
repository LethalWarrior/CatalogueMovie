package com.lethalizer.robby.cataloguemovie.activity;

import android.content.Intent;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.lethalizer.robby.cataloguemovie.R;
import com.lethalizer.robby.cataloguemovie.adapter.SearchMovieAdapter;
import com.lethalizer.robby.cataloguemovie.etc.ItemClickSupport;
import com.lethalizer.robby.cataloguemovie.etc.MovieLoader;
import com.lethalizer.robby.cataloguemovie.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<ArrayList<Movie>>{

    @BindView(R.id.edt_search_movie) EditText edtSearchMovie;
    @BindView(R.id.btn_search_movie) Button btnSearchMovie;
    @BindView(R.id.rv_movie) RecyclerView rvMovie;
    @BindView(R.id.pb_load) ProgressBar pb_load;

    private SearchMovieAdapter searchMovieAdapter;
    private boolean init = false;

    private static final String EXTRA_QUERY = "extra_query";
    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        searchMovieAdapter = new SearchMovieAdapter(this);
        searchMovieAdapter.notifyDataSetChanged();
        rvMovie.setLayoutManager(new LinearLayoutManager(this));
        rvMovie.setAdapter(searchMovieAdapter);

        btnSearchMovie.setOnClickListener(this);

        String query = edtSearchMovie.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_QUERY, query);

        getLoaderManager().initLoader(0, bundle, this);
        init = true;

        ItemClickSupport.addTo(rvMovie).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Movie movie = searchMovieAdapter.getMovieList().get(position);
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                detailIntent.putExtra(EXTRA_MOVIE, movie);
                startActivityForResult(detailIntent, 0);

            }
        });

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btn_search_movie) {

            String query = edtSearchMovie.getText().toString();

            if(TextUtils.isEmpty(query))return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_QUERY, query);
            getLoaderManager().restartLoader(0, bundle, this);
        }

    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {

        String query = "";
        if(args != null) {
            query = args.getString(EXTRA_QUERY);
        }

        if(init) {
        rvMovie.setVisibility(View.INVISIBLE);
        pb_load.setVisibility(View.VISIBLE);}
        return new MovieLoader(this, query);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {

        pb_load.setVisibility(View.INVISIBLE);
        rvMovie.setVisibility(View.VISIBLE);
        searchMovieAdapter.setMovieList(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

        searchMovieAdapter.setMovieList(null);

    }

}
