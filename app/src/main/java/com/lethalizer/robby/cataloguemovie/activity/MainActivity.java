package com.lethalizer.robby.cataloguemovie.activity;

import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.os.AsyncTask;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lethalizer.robby.cataloguemovie.R;
import com.lethalizer.robby.cataloguemovie.adapter.MovieAdapter;
import com.lethalizer.robby.cataloguemovie.etc.MovieLoader;
import com.lethalizer.robby.cataloguemovie.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<ArrayList<Movie>>, ListView.OnItemClickListener{

    private EditText edtSearchMovie;
    private Button btnSearchMovie;
    private ListView lvMovie;
    private MovieAdapter movieAdapter;
    private ProgressBar pb_load;
    private boolean init = false;

    private static final String EXTRA_QUERY = "extra_query";
    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSearchMovie = (EditText) findViewById(R.id.edt_search_movie);
        btnSearchMovie = (Button) findViewById(R.id.btn_search_movie);
        lvMovie = (ListView) findViewById(R.id.lv_movie);
        pb_load = (ProgressBar) findViewById(R.id.pb_load);

        movieAdapter = new MovieAdapter(this);
        movieAdapter.notifyDataSetChanged();
        lvMovie.setAdapter(movieAdapter);
        lvMovie.setOnItemClickListener(this);

        btnSearchMovie.setOnClickListener(this);

        String query = edtSearchMovie.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_QUERY, query);

        getLoaderManager().initLoader(0, bundle, this);
        init = true;

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
        lvMovie.setVisibility(View.INVISIBLE);
        pb_load.setVisibility(View.VISIBLE);}
        return new MovieLoader(this, query);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {

        pb_load.setVisibility(View.INVISIBLE);
        lvMovie.setVisibility(View.VISIBLE);
        movieAdapter.setMovieList(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

        movieAdapter.setMovieList(null);

    }

    // Implementation for onItemClick Listener from ListView
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Movie movie = movieAdapter.getItem(position);
        Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
        detailIntent.putExtra(EXTRA_MOVIE, movie);
        startActivityForResult(detailIntent, 0);

    }
}
