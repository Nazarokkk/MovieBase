package adapter;

import app.AppController;
import model.Movie;
import model.People;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.nazarkorchak.movieview.MovieImageView;
import com.example.nazarkorchak.movieview.R;

import static com.example.nazarkorchak.movieview.MovieImageView.ResponseObserver;

public class CustomListPeopleAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<People> peopleItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListPeopleAdapter(Activity activity, List<People> peopleItems) {
        this.activity = activity;
        this.peopleItems = peopleItems;
    }

    @Override
    public int getCount() {
        return peopleItems.size();
    }

    @Override
    public Object getItem(int location) {
        return peopleItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_people_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView movieImage = (NetworkImageView) convertView
                .findViewById(R.id.image);
        TextView name = (TextView) convertView.findViewById(R.id.name);


        // getting movie data for the row
        People p = peopleItems.get(position);

        // image
        //movieImage.setImageUrl(m.getImageURL(), imageLoader);

        if (p.getImageURL() != null) {
            movieImage.setImageUrl(p.getImageURL(), imageLoader);
            movieImage.setVisibility(View.VISIBLE);
        }
        else {
            movieImage.setVisibility(View.GONE);
        }

        // name
        name.setText(p.getName());


        if ( position % 2 == 0)
            convertView.setBackgroundResource(R.color.list_item_one);
        else
            convertView.setBackgroundResource(R.color.list_item_two);


        return convertView;
    }

}
