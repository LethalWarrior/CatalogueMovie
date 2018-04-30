package com.lethalizer.robby.cataloguemovie.etc;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.bumptech.glide.load.model.stream.StreamUriLoader;
import com.lethalizer.robby.cataloguemovie.BuildConfig;
import com.lethalizer.robby.cataloguemovie.model.Movie;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by robby on 4/14/18.
 */

public class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    private final String API_KEY = "cd4a9bb196e603f0586b617cb18f78a3";
    private String query;
    private ArrayList<Movie> movies;
    private Boolean hasResult = false;
    private MovieLoaderType loaderType;

    public MovieLoader(final Context context, MovieLoaderType loaderType, String query) {
        super(context);
        onContentChanged();
        this.loaderType = loaderType;
        this.query = query;
    }

    @Override
    public ArrayList<Movie> loadInBackground() {

        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<Movie> movies = new ArrayList<>();
        String url = getUrl();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {

                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for(int i=0; i<list.length(); i++) {

                        JSONObject movieObject = list.getJSONObject(i);
                        Movie movie = new Movie(movieObject);
                        movies.add(movie);

                    }

                }catch (Exception ex) {

                    ex.printStackTrace();

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return movies;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private String getUrl() {
        switch (loaderType) {
            case SEARCH_MOVIE:
                return "https://api.themoviedb.org/3/search/movie?" +
                        "api_key="+API_KEY+"&query="+query.replace(" ", "%20");
            case NOW_PLAYING_MOVIE:
                return "https://api.themoviedb.org/3/movie/now_playing?" +
                        "api_key="+API_KEY;
            case UPCOMING_MOVIE:
                return "https://api.themoviedb.org/3/movie/upcoming?" +
                        "api_key="+API_KEY;
            default:
                return null;
        }
    }

    @Override
    public void deliverResult(ArrayList<Movie> data) {
        this.movies = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(takeContentChanged())
            forceLoad();
        else if(hasResult)
            deliverResult(movies);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if(hasResult) {
            movies = null;
            hasResult = false;
        }
    }
}
