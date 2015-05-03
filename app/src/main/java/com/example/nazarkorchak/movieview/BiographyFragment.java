package com.example.nazarkorchak.movieview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import app.AppController;
import model.MovieAll;
import model.PeopleAll;

public class BiographyFragment extends Fragment {


// Log tag
    private static final String TAG = "Error there"/*MovieActivity.class.getSimpleName()*/;
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w500";
    private static final String url = "http://api.themoviedb.org/3/person/";
    private static final String USER_KEY = "?api_key=7178226f3e2d3747b4c127e7d638cb71";

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.people_layout, container, false);

        final String peopleID = getActivity().getIntent().getStringExtra("peopleID");

        final NetworkImageView movieImage = (NetworkImageView) v.findViewById(R.id.poster_path);
        final TextView name = (TextView) v.findViewById(R.id.name);
        final TextView birthday = (TextView) v.findViewById(R.id.birthday);
        final TextView placeOfBirth = (TextView) v.findViewById(R.id.place_of_birth);
        final TextView biography = (TextView) v.findViewById(R.id.biography);


        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url + peopleID + USER_KEY);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    // parseJsonMovieAll(new JSONObject(data));


                    JSONObject obj = (JSONObject)new JSONObject(data);

                    PeopleAll movie = new PeopleAll();


                    movie.setName(obj.getString("name"));

                    String image = obj.isNull("profile_path") ? null : obj
                            .getString("profile_path");
                    movie.setImageURL(IMAGE_URL + image);

                    movie.setBirthday(obj.getString("birthday"));
                    movie.setPlaceOfBirth(obj.getString("place_of_birth"));
                    movie.setBiography(obj.getString("biography"));




                    name.setText(movie.getName());
                    birthday.setText(movie.getBirthday());
                    placeOfBirth.setText(movie.getPlaceOfBirth());
                    biography.setText(movie.getBiography());



//image
                    if (movie.getImageURL() != null) {
                        movieImage.setImageUrl(movie.getImageURL(), imageLoader);
                        movieImage.setVisibility(View.VISIBLE);
                    }
                    else {
                        movieImage.setVisibility(View.GONE);
                    }

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
                    url + peopleID + USER_KEY, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());

                    if (response != null) {

                        try {

                            JSONObject obj = (JSONObject)response;


                            PeopleAll movie = new PeopleAll();

                            movie.setName(obj.getString("name"));

                            String image = obj.isNull("profile_path") ? null : obj
                                    .getString("profile_path");
                            movie.setImageURL(IMAGE_URL + image);

                            movie.setBirthday(obj.getString("birthday"));
                            movie.setPlaceOfBirth(obj.getString("place_of_birth"));
                            movie.setBiography(obj.getString("biography"));


                            name.setText(movie.getName());
                            birthday.setText(movie.getBirthday());
                            placeOfBirth.setText(movie.getPlaceOfBirth());
                            biography.setText(movie.getBiography());

                            //image
                            if (movie.getImageURL() != null) {
                                movieImage.setImageUrl(movie.getImageURL(), imageLoader);
                                movieImage.setVisibility(View.VISIBLE);
                            }
                            else {
                                movieImage.setVisibility(View.GONE);
                            }

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

        return v;
    }

}
