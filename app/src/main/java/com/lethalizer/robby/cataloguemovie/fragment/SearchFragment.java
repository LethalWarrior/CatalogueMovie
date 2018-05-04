package com.lethalizer.robby.cataloguemovie.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.lethalizer.robby.cataloguemovie.R;
import com.lethalizer.robby.cataloguemovie.activity.DetailActivity;
import com.lethalizer.robby.cataloguemovie.adapter.SearchMovieAdapter;
import com.lethalizer.robby.cataloguemovie.etc.MovieLoader;
import com.lethalizer.robby.cataloguemovie.etc.MovieLoaderType;
import com.lethalizer.robby.cataloguemovie.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>,
    View.OnClickListener{

    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;

    @BindView(R.id.pb_load)
    ProgressBar pbLoad;

    @BindView(R.id.edt_search_movie)
    EditText edtSearchMovie;

    @BindView(R.id.btn_search_movie)
    Button btnSearchMovie;

    private SearchMovieAdapter searchMovieAdapter;
    private boolean init = false;


    private static final String EXTRA_QUERY = "extra_query";
    public static final String EXTRA_MOVIE = "extra_movie";

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);

        btnSearchMovie.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchMovieAdapter.RecyclerViewItemClickListener listener = new SearchMovieAdapter.RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Movie movie = searchMovieAdapter.getMovieList().get(position);
                Intent detailIntent = new Intent(getContext(), DetailActivity.class);
                detailIntent.putExtra(EXTRA_MOVIE, movie);
                startActivityForResult(detailIntent, 0);
            }
        };

        searchMovieAdapter = new SearchMovieAdapter(getContext());
        searchMovieAdapter.notifyDataSetChanged();
        searchMovieAdapter.setListener(listener);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setAdapter(searchMovieAdapter);

        String query = edtSearchMovie.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_QUERY, query);

        getLoaderManager().initLoader(0, bundle, this);
        init = true;
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {

        String query = null;
        if (args!=null) {
            query = args.getString(EXTRA_QUERY);
        }

        if(init){rvMovie.setVisibility(View.INVISIBLE);
        pbLoad.setVisibility(View.VISIBLE);}
        return new MovieLoader(getContext(), MovieLoaderType.SEARCH_MOVIE, query);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        pbLoad.setVisibility(View.INVISIBLE);
        rvMovie.setVisibility(View.VISIBLE);

        searchMovieAdapter.setMovieList(data);
        searchMovieAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        searchMovieAdapter.setMovieList(null);
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

}
