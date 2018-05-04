package com.lethalizer.robby.cataloguemovie.fragment;


import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.lethalizer.robby.cataloguemovie.R;
import com.lethalizer.robby.cataloguemovie.activity.DetailActivity;
import com.lethalizer.robby.cataloguemovie.adapter.ShowMovieAdapter;
import com.lethalizer.robby.cataloguemovie.etc.MovieLoader;
import com.lethalizer.robby.cataloguemovie.etc.MovieLoaderType;
import com.lethalizer.robby.cataloguemovie.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>{

    @BindView(R.id.pb_load)
    ProgressBar pbLoad;

    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;

    private ShowMovieAdapter showMovieAdapter;
    public static final String EXTRA_MOVIE = "extra_movie";

    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ShowMovieAdapter.ViewHolderItemClickListener listener = new ShowMovieAdapter.ViewHolderItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Movie movie = showMovieAdapter.getMovieList().get(position);
                Intent detailIntent = new Intent(getContext(), DetailActivity.class);
                detailIntent.putExtra(EXTRA_MOVIE, movie);
                startActivityForResult(detailIntent, 0);
            }
        };

        showMovieAdapter = new ShowMovieAdapter(getContext());
        showMovieAdapter.setViewHolderLayout(R.layout.item_card_now_playing);
        showMovieAdapter.setListener(listener);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));

        rvMovie.setAdapter(showMovieAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        rvMovie.setVisibility(View.INVISIBLE);
        pbLoad.setVisibility(View.VISIBLE);
        return new  MovieLoader(getContext(), MovieLoaderType.NOW_PLAYING_MOVIE, null);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        pbLoad.setVisibility(View.INVISIBLE);
        rvMovie.setVisibility(View.VISIBLE);
        showMovieAdapter.setMovieList(data);
        showMovieAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        showMovieAdapter.setMovieList(null);
    }

}
