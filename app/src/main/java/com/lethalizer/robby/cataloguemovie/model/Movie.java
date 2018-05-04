package com.lethalizer.robby.cataloguemovie.model;

import android.content.SharedPreferences;

import com.lethalizer.robby.cataloguemovie.activity.MainActivity;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by robby on 4/13/18.
 */

public class Movie implements Serializable{

    private int id;
    private String title, overview, releaseDate, posterPath, backdropPath, score;

    public Movie(JSONObject object) {

        try {

            this.id  = object.getInt("id");
            this.title = object.getString("title");
            this.overview = object.getString("overview");
            this.releaseDate = formatDate(object.getString("release_date"));
            this.posterPath = "http://image.tmdb.org/t/p/w185"+object.getString("poster_path");
            this.backdropPath = "http://image.tmdb.org/t/p/w185"+object.getString("backdrop_path");
            this.score = Double.toString(Double.parseDouble(object.getString("vote_average")));

        }catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public int getId() {return this.id; }

    public void setId(int id) {this.id = id;}

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String overview) { this.overview = overview; }

    public String getReleaseDate() { return this.releaseDate; }

    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public String getPosterPath() {
        return this.posterPath;
    }

    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public String getBackdropPath() { return this.backdropPath; }

    public void setBackdropPath(String backdropPath) { this.backdropPath = backdropPath; }

    public String getScore() { return this.score; }

    public void setScore(String score) { this.score = score; }

    private String formatDate(String dateString) {

        SharedPreferences preferences = MainActivity.getPreferences();
        String lang = preferences.getString("My_Lang", "");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat targetFormat = new SimpleDateFormat("EEEE, dd MMM, yyyy", new Locale(lang));
        Date date = new Date();
        try {

            date = dateFormat.parse(dateString);
            return targetFormat.format(date);

        } catch (Exception ex) {

            ex.printStackTrace();

        }
        return null;
    }

}
