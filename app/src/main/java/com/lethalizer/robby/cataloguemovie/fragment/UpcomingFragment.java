package com.lethalizer.robby.cataloguemovie.fragment;


import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lethalizer.robby.cataloguemovie.R;
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
public class UpcomingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>{

    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;

    @BindView(R.id.pb_load)
    ProgressBar pbLoad;

    private ShowMovieAdapter showMovieAdapter;

    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showMovieAdapter = new ShowMovieAdapter(getContext());
        showMovieAdapter.setViewHolderLayout(R.layout.item_card_upcoming);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setAdapter(showMovieAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        rvMovie.setVisibility(View.INVISIBLE);
        pbLoad.setVisibility(View.VISIBLE);
        return new MovieLoader(getContext(), MovieLoaderType.UPCOMING_MOVIE, null);
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
