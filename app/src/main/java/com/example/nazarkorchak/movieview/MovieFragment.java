package com.example.nazarkorchak.movieview;


    import android.os.Bundle;
    import android.support.v4.app.Fragment;
    import android.support.v4.app.NavUtils;
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

public class MovieFragment extends Fragment {


    // Log tag
    private static final String TAG = "Error there"/*MovieActivity.class.getSimpleName()*/;
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w500";
    private static final String url = "http://api.themoviedb.org/3/movie/";
    private static final String USER_KEY = "?api_key=7178226f3e2d3747b4c127e7d638cb71";
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            setHasOptionsMenu(true);

            View v = inflater.inflate(R.layout.movie_fragment_layout, container, false);

            final String movieID = getActivity().getIntent().getStringExtra("movieID");

            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();

            final NetworkImageView movieImage = (NetworkImageView) v.findViewById(R.id.poster_path);
            final TextView originalTitle = (TextView) v.findViewById(R.id.originalTitle);
            final TextView genre = (TextView) v.findViewById(R.id.genre);
            final TextView status = (TextView) v.findViewById(R.id.status);
            final TextView releaseDate = (TextView) v.findViewById(R.id.release_date);
            final TextView description = (TextView) v.findViewById(R.id.description);
            final TextView runtime = (TextView) v.findViewById(R.id.runtime);
            final TextView budget = (TextView) v.findViewById(R.id.budget);
            final TextView revenue = (TextView) v.findViewById(R.id.revenue);
            final TextView tagline = (TextView) v.findViewById(R.id.tagline);
            final TextView prod_comp = (TextView) v.findViewById(R.id.productionCompanies);
            final TextView prod_countr = (TextView) v.findViewById(R.id.productionCountries);


            Cache cache = AppController.getInstance().getRequestQueue().getCache();
            Cache.Entry entry = cache.get(url + movieID + USER_KEY);
            if (entry != null) {
                // fetch the data from cache
                try {
                    String data = new String(entry.data, "UTF-8");
                    try {

                        JSONObject obj = (JSONObject)new JSONObject(data);

                        MovieAll movie = new MovieAll();

                        movie.setOriginalTitle(obj.getString("original_title"));

                        String image = obj.isNull("poster_path") ? null : obj
                                .getString("poster_path");
                        movie.setImageURL(IMAGE_URL + image);
//genre
                        JSONArray genreArr = obj.getJSONArray("genres");
                        ArrayList<String> genres = new ArrayList<String>();

                        for(int i=0;i<genreArr.length();i++){

                            JSONObject genreone = genreArr.getJSONObject(i);

                            genres.add((String) genreone.getString("name"));
                        }
                        movie.setGenre(genres);

                        String genreStr = "";
                        for (String str : movie.getGenre()) {
                            genreStr += str + "/ ";
                        }
                        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                                genreStr.length() - 2) : genreStr;
                        genre.setText(genreStr);

                        //prod_comp

                        JSONArray prod_compArr = obj.getJSONArray("production_companies");
                        ArrayList<String> prod_company = new ArrayList<String>();

                        for(int i=0;i<prod_compArr.length();i++){

                            JSONObject prod_compone = prod_compArr.getJSONObject(i);

                            prod_company.add((String) prod_compone.getString("name"));
                        }

                        String prod_compStr = "";
                        for (String str : prod_company) {
                            prod_compStr += str + "/ ";
                        }
                        prod_compStr = prod_compStr.length() > 0 ? prod_compStr.substring(0,
                                prod_compStr.length() - 2) : prod_compStr;
                        prod_comp.setText(prod_compStr);

                        //production_countries

                        JSONArray prod_countrArr = obj.getJSONArray("production_countries");
                        ArrayList<String> prod_country = new ArrayList<String>();

                        for(int i=0;i<prod_countrArr.length();i++){

                            JSONObject prod_countrpone = prod_countrArr.getJSONObject(i);

                            prod_country.add((String) prod_countrpone.getString("name"));
                        }

                        String prod_countrStr = "";
                        for (String str : prod_country) {
                            prod_countrStr += str + "/ ";
                        }
                        prod_countrStr = prod_countrStr.length() > 0 ? prod_countrStr.substring(0,
                                prod_countrStr.length() - 2) : prod_countrStr;

                        prod_countr.setText(prod_countrStr);



                        movie.setReleaseDate(obj.getString("release_date"));
                        movie.setStatus(obj.getString("status"));
                        movie.setDescription(obj.getString("overview"));

                        movie.setBudget(obj.getString("budget"));
                        movie.setRuntime(obj.getString("runtime"));
                        movie.setRevenue(obj.getString("revenue"));
                        movie.setTagline(obj.getString("tagline"));


                        originalTitle.setText(movie.getOriginalTitle());
                        status.setText(movie.getStatus());
                        releaseDate.setText(movie.getReleaseDate());
                        description.setText(movie.getDescription());

                        budget.setText(movie.getBudget());
                        runtime.setText(movie.getRuntime());
                        revenue.setText(movie.getRevenue());

                        //tagline
                        if(movie.getTagline() != null)
                        {
                            tagline.setText(movie.getTagline());
                        }
                        else tagline.setText("Nothing found");

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
                        url + movieID + USER_KEY, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.d(TAG, "Response: " + response.toString());

                        if (response != null) {

                            try {

                                JSONObject obj = (JSONObject)response;

                                MovieAll movie = new MovieAll();
                                movie.setOriginalTitle(obj.getString("original_title"));

                                String image = obj.isNull("poster_path") ? null : obj
                                        .getString("poster_path");
                                movie.setImageURL(IMAGE_URL + image);

                                //image
                                if (movie.getImageURL() != null) {
                                    movieImage.setImageUrl(movie.getImageURL(), imageLoader);
                                    movieImage.setVisibility(View.VISIBLE);
                                }
                                else {
                                    movieImage.setVisibility(View.GONE);
                                }


                                //prod_comp

                                JSONArray prod_compArr = obj.getJSONArray("production_companies");
                                ArrayList<String> prod_company = new ArrayList<String>();

                                for(int i=0;i<prod_compArr.length();i++){

                                    JSONObject prod_compone = prod_compArr.getJSONObject(i);

                                    prod_company.add((String) prod_compone.getString("name"));
                                }

                                String prod_compStr = "";
                                for (String str : prod_company) {
                                    prod_compStr += str + "/ ";
                                }
                                prod_compStr = prod_compStr.length() > 0 ? prod_compStr.substring(0,
                                        prod_compStr.length() - 2) : prod_compStr;
                                prod_comp.setText(prod_compStr);

                                //production_countries

                                JSONArray prod_countrArr = obj.getJSONArray("production_countries");
                                ArrayList<String> prod_country = new ArrayList<String>();

                                for(int i=0;i<prod_countrArr.length();i++){

                                    JSONObject prod_countrpone = prod_countrArr.getJSONObject(i);

                                    prod_country.add((String) prod_countrpone.getString("name"));
                                }

                                String prod_countrStr = "";
                                for (String str : prod_country) {
                                    prod_countrStr += str + "/ ";
                                }
                                prod_countrStr = prod_countrStr.length() > 0 ? prod_countrStr.substring(0,
                                        prod_countrStr.length() - 2) : prod_countrStr;

                                prod_countr.setText(prod_countrStr);


                                movie.setReleaseDate(obj.getString("release_date"));
                                movie.setStatus(obj.getString("status"));
                                movie.setDescription(obj.getString("overview"));
                                movie.setBudget(obj.getString("budget"));
                                movie.setRuntime(obj.getString("runtime"));
                                movie.setRevenue(obj.getString("revenue"));
                                movie.setTagline(obj.getString("tagline"));

                                originalTitle.setText(movie.getOriginalTitle());
                                status.setText(movie.getStatus());
                                releaseDate.setText(movie.getReleaseDate());
                                description.setText(movie.getDescription());
                                budget.setText(movie.getBudget());
                                runtime.setText(movie.getRuntime());
                                revenue.setText(movie.getRevenue());

                                //tagline
                                if(movie.getTagline() != null)
                                {
                                    tagline.setText(movie.getTagline());
                                }
                                else tagline.setText("Nothing found");

                                //genre
                                JSONArray genreArr = obj.getJSONArray("genres");
                                ArrayList<String> genres = new ArrayList<String>();
                                for(int i=0;i<genreArr.length();i++){

                                    JSONObject genreone = genreArr.getJSONObject(i);

                                    genres.add((String) genreone.getString("name"));
                                }

                                String genreStr = "";
                                for (String str : genres) {
                                    genreStr += str + "/ ";
                                }
                                genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                                        genreStr.length() - 2) : genreStr;
                                genre.setText(genreStr);


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

