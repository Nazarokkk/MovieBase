package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.nazarkorchak.movieview.R;

import java.util.List;

import app.AppController;
import model.Crew;
import model.MovieCredits;


public class MovieCreditsListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<MovieCredits> movieItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public MovieCreditsListAdapter(Activity activity, List<MovieCredits> movieItems) {
        this.activity = activity;
        this. movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return  movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return  movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.movie_credits_layout, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView movieImage = (NetworkImageView) convertView
                .findViewById(R.id.image);
        TextView movie = (TextView) convertView.findViewById(R.id.movie);
        TextView character = (TextView) convertView.findViewById(R.id.character);




        // getting movie data for the row
        MovieCredits c =  movieItems.get(position);

        // image
        //movieImage.setImageUrl(m.getImageURL(), imageLoader);

        if (c.getImageURL() != null) {
            movieImage.setImageUrl(c.getImageURL(), imageLoader);
            movieImage.setVisibility(View.VISIBLE);
        }
        else {
            movieImage.setVisibility(View.GONE);
        }

        // name
        movie.setText(c.getMovie());

        //job
        character.setText(c.getCharacter());

        if ( position % 2 == 0)
            convertView.setBackgroundResource(R.color.list_item_one);
        else
            convertView.setBackgroundResource(R.color.list_item_two);


        return convertView;
    }

}

