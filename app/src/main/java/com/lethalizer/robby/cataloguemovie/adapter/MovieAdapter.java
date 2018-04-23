package com.lethalizer.robby.cataloguemovie.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lethalizer.robby.cataloguemovie.R;
import com.lethalizer.robby.cataloguemovie.model.Movie;

import java.util.ArrayList;

/**
 * Created by robby on 4/14/18.
 */

public class MovieAdapter extends BaseAdapter {

    private ArrayList<Movie> movieList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setMovieList(ArrayList<Movie> movieList) {

        this.movieList = movieList;
        notifyDataSetChanged();

    }

    public void addMovie(final Movie movie) {

        this.movieList.add(movie);
        notifyDataSetChanged();

    }

    public void clearMovies() {

        this.movieList.clear();

    }

    @Override
    public int getCount() {
        if(movieList == null) return 0;
        return movieList.size();
    }

    @Override
    public Movie getItem(int position) {
        return this.movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null) {

            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_row_movie, null);
            holder.tvMovieTitle = (TextView) convertView.findViewById(R.id.tv_movie_title);
            holder.tvMovieOverview = (TextView) convertView.findViewById(R.id.tv_movie_overview);
            holder.tvReleaseDateItem = (TextView) convertView.findViewById(R.id.tv_release_date_item);
            holder.imgMoviePoster = (ImageView) convertView.findViewById(R.id.img_poster);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }
        holder.tvMovieTitle.setText(movieList.get(position).getTitle());
        holder.tvMovieOverview.setText(movieList.get(position).getOverview());
        holder.tvReleaseDateItem.setText(movieList.get(position).getReleaseDate());
        Glide.with(parent.getContext())
                .load(movieList.get(position).getPosterPath())
                .into(holder.imgMoviePoster);

        return convertView;
    }

    //ViewHolder for ListView
    private static class ViewHolder {

        TextView tvMovieTitle;
        TextView tvMovieOverview;
        TextView tvReleaseDateItem;
        ImageView imgMoviePoster;

    }
}
