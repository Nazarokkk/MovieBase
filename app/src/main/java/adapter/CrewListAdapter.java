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


public class CrewListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Crew> crewItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CrewListAdapter(Activity activity, List<Crew> crewItems) {
        this.activity = activity;
        this. crewItems = crewItems;
    }

    @Override
    public int getCount() {
        return  crewItems.size();
    }

    @Override
    public Object getItem(int location) {
        return  crewItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_crew_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView movieImage = (NetworkImageView) convertView
                .findViewById(R.id.image);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView department = (TextView) convertView.findViewById(R.id.department);
        TextView job = (TextView) convertView.findViewById(R.id.job);



        // getting movie data for the row
        Crew c =  crewItems.get(position);

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
        name.setText(c.getName());

        //job
        job.setText(c.getJob());

        //department
        department.setText(c.getDepartment());


        if ( position % 2 == 0)
            convertView.setBackgroundResource(R.color.list_item_one);
        else
            convertView.setBackgroundResource(R.color.list_item_two);


        return convertView;
    }

}

