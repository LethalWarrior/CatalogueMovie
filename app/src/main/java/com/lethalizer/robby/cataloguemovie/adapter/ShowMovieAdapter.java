package com.lethalizer.robby.cataloguemovie.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lethalizer.robby.cataloguemovie.R;
import com.lethalizer.robby.cataloguemovie.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by robby on 4/25/18.
 */

public class ShowMovieAdapter extends RecyclerView.Adapter<ShowMovieAdapter.ViewHolder> {

    private ArrayList<Movie> movieList = new ArrayList<Movie>();
    private Context context;

    public ShowMovieAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }

    public void addMovie(final Movie movie) {

        this.movieList.add(movie);
        notifyDataSetChanged();

    }

    public void clearMovies() {

        this.movieList.clear();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_now_playing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvTitle.setText(getMovieList().get(position).getTitle());
        holder.tvOverview.setText(getMovieList().get(position).getTitle());
        holder.tvReleaseDate.setText(getMovieList().get(position).getTitle());

        Glide.with(context)
                .load(getMovieList().get(position).getPosterPath())
                .into(holder.imgPoster);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_poster)
        ImageView imgPoster;

        @BindView(R.id.tv_movie_title)
        TextView tvTitle;

        @BindView(R.id.tv_movie_overview)
        TextView tvOverview;

        @BindView(R.id.tv_release_date_item)
        TextView tvReleaseDate;

        @BindView(R.id.btn_detail)
        Button btnDetail;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

}
