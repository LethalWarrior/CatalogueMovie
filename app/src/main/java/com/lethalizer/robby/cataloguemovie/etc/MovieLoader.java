package com.lethalizer.robby.cataloguemovie.etc;

import android.content.AsyncTaskLoader;
import android.content.Context;

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

    private static final String API_KEY = "cd4a9bb196e603f0586b617cb18f78a3";
    private String query;
    private ArrayList<Movie> movies;
    private Boolean hasResult = false;

    public MovieLoader(final Context context, String query) {
        super(context);

        onContentChanged();
        this.query = query;
    }


    @Override
    public ArrayList<Movie> loadInBackground() {

        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<Movie> movies = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?" +
                "api_key="+API_KEY+"&query="+query.replace(" ", "%20");

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
