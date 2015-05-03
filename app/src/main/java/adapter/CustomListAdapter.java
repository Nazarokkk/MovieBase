package adapter;

import app.AppController;
import model.Cast;
import model.Movie;

import java.util.List;

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

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Movie> movieItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Movie> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView movieImage = (NetworkImageView) convertView
                .findViewById(R.id.image);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView popularity = (TextView) convertView.findViewById(R.id.popularity);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        // getting movie data for the row
        Movie m = movieItems.get(position);

        // image
        //movieImage.setImageUrl(m.getImageURL(), imageLoader);

                 if (m.getImageURL() != null) {
            movieImage.setImageUrl(m.getImageURL(), imageLoader);
            movieImage.setVisibility(View.VISIBLE);
        }
              else {
            movieImage.setVisibility(View.GONE);
        }

        // title
        title.setText(m.getTitle());

        // rating
        rating.setText("Rating: " + String.valueOf(m.getRating()));

        // popularuty
        popularity.setText("Popularity: " + String.valueOf(m.getPopularity()));


        // release year
        year.setText(String.valueOf(m.getYear()));

        if ( position % 2 == 0)
        convertView.setBackgroundResource(R.color.list_item_one);
        else
        convertView.setBackgroundResource(R.color.list_item_two);


        return convertView;
    }

}
