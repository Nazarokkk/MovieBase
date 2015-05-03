package com.example.nazarkorchak.movieview;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class UpcomingFragment extends Fragment {

    public UpcomingFragment() {
    }

    // Log tag
    private static final String TAG = "Error there"/*MainActivity.class.getSimpleName()*/;

    // Movies json url
    private static final String url = "http://api.themoviedb.org/3/movie/upcoming?api_key=7178226f3e2d3747b4c127e7d638cb71";
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w500";
    private ProgressDialog pDialog;
    private List<Movie> movieList = new ArrayList<Movie>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_fragment, container, false);


        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new CustomListAdapter(getActivity(), movieList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MovieActivity.class);
                intent.putExtra("movieID", Integer.toString(movieList.get((int)id).getId()));
                intent.putExtra("movie_title",movieList.get((int)id).getTitle());
                startActivity(intent);
            }
        });

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonMovie(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonMovie(response);
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
        return rootView;
    }

    private void parseJsonMovie(JSONObject response) {
        try {

            JSONArray movieArray = response.getJSONArray("results");

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject obj = (JSONObject) movieArray.get(i);

                Movie movie = new Movie();
                movie.setTitle(obj.getString("title"));

                String image = obj.isNull("poster_path") ? null : obj
                        .getString("poster_path");
                movie.setImageURL(IMAGE_URL + image);

                movie.setId(obj.getInt("id"));

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
