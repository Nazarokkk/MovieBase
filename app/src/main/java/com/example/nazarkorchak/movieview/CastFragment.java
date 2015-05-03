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

import adapter.CastListAdapter;
import app.AppController;
import model.Cast;


public class CastFragment extends Fragment {

    public CastFragment() {
    }

    // Log tag
    private static final String TAG = "Error there"/*MainActivity.class.getSimpleName()*/;

    // Movies json url
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w500";
    private static final String url = "http://api.themoviedb.org/3/movie/";
    private static final String USER_KEY = "/credits?api_key=7178226f3e2d3747b4c127e7d638cb71";
    private ProgressDialog pDialog;
    private List<Cast> castList = new ArrayList<Cast>();
    private ListView listView;
    private CastListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.cast_crew_fragment_layout, container, false);

        final String movieID = getActivity().getIntent().getStringExtra("movieID");

        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new CastListAdapter(getActivity(), castList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PeopleActivity.class);
                intent.putExtra("peopleID", Double.toString(castList.get((int)id).getId()));
                intent.putExtra("peopleName", castList.get((int)id).getName());
                startActivity(intent);
            }
        });

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url + movieID + USER_KEY);
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
                    url + movieID + USER_KEY, null, new Response.Listener<JSONObject>() {

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

                Cast cast = new Cast();
                cast.setName(obj.getString("name"));

                String image = obj.isNull("profile_path") ? null : obj
                        .getString("profile_path");
                cast.setImageURL(IMAGE_URL + image);

                cast.setCharacter(obj.getString("character"));

                cast.setId(obj.getInt("id"));

                castList.add(cast);
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