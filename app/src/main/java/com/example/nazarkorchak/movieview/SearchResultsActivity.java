package com.example.nazarkorchak.movieview;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import adapter.CustomListAdapter;
import app.AppController;
import model.Movie;

public class SearchResultsActivity extends Activity{

    // Log tag
    private static final String TAG = "Error there"/*MainActivity.class.getSimpleName()*/;

    // Movies json url
    private static final String url = "http://api.themoviedb.org/3/search/movie?query=";
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w500";
    private static final String API_KEY = "&api_key=7178226f3e2d3747b4c127e7d638cb71";
    private ProgressDialog pDialog;


    private ListView list;
    private List<Movie> movieList = new ArrayList<Movie>();
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // get the action bar
        ActionBar actionBar = getActionBar();

        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);

        list = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, movieList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplication(), MovieActivity.class);
                intent.putExtra("movieID", Integer.toString(movieList.get((int) id).getId()));
                startActivity(intent);
            }
        });

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    /**
     * Handling intent data
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            /**
             * Use this query to display search results like
             * 1. Getting the data from SQLite and showing in listview
             * 2. Making webrequest and displaying the data
             * For now we just display the query only
             */

            Cache cache = AppController.getInstance().getRequestQueue().getCache();
            Cache.Entry entry = cache.get(url + query + API_KEY);
            if (entry != null) {
                // fetch the data from cache
                try {
                    String data = new String(entry.data, "UTF-8");
                    try {
                            JSONArray movieArray = new JSONObject(data).getJSONArray("results");

                            for (int i = 0; i < movieArray.length(); i++) {
                                JSONObject obj = (JSONObject) movieArray.get(i);

                                Movie movie = new Movie();
                                movie.setTitle(obj.getString("title"));

                                String image = obj.isNull("poster_path") ? null : obj
                                        .getString("poster_path");
                                movie.setImageURL(IMAGE_URL + image);

                                movie.setRating(((Number) obj.get("vote_average"))
                                        .doubleValue());
                                movie.setYear(obj.getString("release_date"));
                                movie.setPopularity(obj.getDouble("popularity"));
                                movie.setId(obj.getInt("id"));

                                movieList.add(movie);
                            }

                            // notify data changes to list adapater
                            adapter.notifyDataSetChanged();
                            onDestroy();
                        }
                     catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            } else {
                // making fresh volley request and getting json
                JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                        url + query + API_KEY, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.d(TAG, "Response: " + response.toString());
                        if (response != null) {

                            try {

                                JSONArray movieArray = response.getJSONArray("results");

                                for (int i = 0; i < movieArray.length(); i++) {
                                    JSONObject obj = (JSONObject) movieArray.get(i);

                                    Movie movie = new Movie();
                                    movie.setTitle(obj.getString("title"));

                                    String image = obj.isNull("poster_path") ? null : obj
                                            .getString("poster_path");
                                    movie.setImageURL(IMAGE_URL + image);

                                    movie.setRating(((Number) obj.get("vote_average"))
                                            .doubleValue());
                                    movie.setYear(obj.getString("release_date"));
                                    movie.setPopularity(obj.getDouble("popularity"));
                                    movie.setId(obj.getInt("id"));

                                    movieList.add(movie);
                                }

                                // notify data changes to list adapater
                                adapter.notifyDataSetChanged();
                                onDestroy();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                    }
                });

                // Adding request to volley request queue
                AppController.getInstance().addToRequestQueue(jsonReq);
            }

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
