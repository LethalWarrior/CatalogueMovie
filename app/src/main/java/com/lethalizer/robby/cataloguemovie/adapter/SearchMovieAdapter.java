package com.lethalizer.robby.cataloguemovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lethalizer.robby.cataloguemovie.R;
import com.lethalizer.robby.cataloguemovie.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by robby on 4/14/18.
 */

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.MovieHolder> {

    private ArrayList<Movie> movieList = new ArrayList<>();
    private Context context;

    public SearchMovieAdapter(Context context) {
        this.context = context;
    }

    public void setMovieList(ArrayList<Movie> movieList) {

        this.movieList = movieList;
        notifyDataSetChanged();

    }

    public ArrayList<Movie> getMovieList() {
        return movieList;
    }

    public void addMovie(final Movie movie) {

        this.movieList.add(movie);
        notifyDataSetChanged();

    }

    public void clearMovies() {

        this.movieList.clear();

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return getMovieList().size();
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row_movie, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {

        holder.tvMovieTitle.setText(getMovieList().get(position).getTitle());
        holder.tvMovieOverview.setText(getMovieList().get(position).getOverview());
        holder.tvReleaseDateItem.setText(getMovieList().get(position).getReleaseDate());

        Glide.with(context)
                .load(getMovieList().get(position).getPosterPath())
                .into(holder.imgMoviePoster);

    }


    //ViewHolder for ListView
    class MovieHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_movie_title) TextView tvMovieTitle;
        @BindView(R.id.tv_movie_overview) TextView tvMovieOverview;
        @BindView(R.id.tv_release_date_item) TextView tvReleaseDateItem;
        @BindView(R.id.img_poster) ImageView imgMoviePoster;


        public MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
