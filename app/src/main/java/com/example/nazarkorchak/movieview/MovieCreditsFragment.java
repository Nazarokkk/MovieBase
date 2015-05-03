package com.example.nazarkorchak.movieview;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import adapter.MovieCreditsListAdapter;
import app.AppController;
import model.Crew;
import model.MovieCredits;


public class MovieCreditsFragment extends Fragment {

    public MovieCreditsFragment() {
    }

    // Log tag
    private static final String TAG = "Error there"/*MainActivity.class.getSimpleName()*/;

    // Movies json url
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w500";
    private static final String url = "http://api.themoviedb.org/3/person/";
    private static final String USER_KEY = "/movie_credits?api_key=7178226f3e2d3747b4c127e7d638cb71";
    private ProgressDialog pDialog;
    private List<MovieCredits> movieCreditsList = new ArrayList<MovieCredits>();
    private ListView listView;
    private MovieCreditsListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.cast_crew_fragment_layout, container, false);

        final String peopleID = getActivity().getIntent().getStringExtra("peopleID");

        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new MovieCreditsListAdapter(getActivity(), movieCreditsList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MovieActivity.class);
                intent.putExtra("movieID", Double.toString(movieCreditsList.get((int) id).getId()));
                intent.putExtra("movie_title",movieCreditsList.get((int)id).getMovie());
                startActivity(intent);
            }
        });

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url + peopleID + USER_KEY);
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
                    url + peopleID + USER_KEY, null, new Response.Listener<JSONObject>() {

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

            JSONArray movieArray = response.getJSONArray("cast");

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject obj = (JSONObject) movieArray.get(i);

                MovieCredits movie = new MovieCredits();
                movie.setCharacter(obj.getString("character"));
                movie.setMovie(obj.getString("original_title"));

                String image = obj.isNull("poster_path") ? null : obj
                        .getString("poster_path");
                movie.setImageURL(IMAGE_URL + image);

                movie.setId(obj.getInt("id"));

                movieCreditsList.add(movie);
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