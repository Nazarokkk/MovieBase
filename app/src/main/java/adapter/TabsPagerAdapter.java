package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.nazarkorchak.movieview.CastFragment;
import com.example.nazarkorchak.movieview.CrewFragment;
import com.example.nazarkorchak.movieview.MovieFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // movie fragment activity
                return new MovieFragment();
            case 1:
                // actors fragment activity
                return new CastFragment();
            case 2:
                // actors fragment activity
                return new CrewFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}