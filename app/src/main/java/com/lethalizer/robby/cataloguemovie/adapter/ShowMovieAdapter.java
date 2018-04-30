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
    private int viewHolderLayout;
    private Context context;
    private ViewHolderItemClickListener listener;

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

    public void setViewHolderLayout(int viewHolderLayout) {
        this.viewHolderLayout = viewHolderLayout;
    }

    public ViewHolderItemClickListener getListener() {
        return listener;
    }

    public void setListener(ViewHolderItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewHolderLayout, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvTitle.setText(getMovieList().get(position).getTitle());
        holder.tvOverview.setText(getMovieList().get(position).getOverview());
        holder.tvReleaseDate.setText(getMovieList().get(position).getReleaseDate());

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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ViewHolderItemClickListener listener;

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

        public ViewHolder(View itemView, ViewHolderItemClickListener listener) {
            super(itemView);

            this.listener = listener;
            ButterKnife.bind(this, itemView);
            btnDetail.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    public interface ViewHolderItemClickListener {
        void onClick(View view, int position);
    }
}
