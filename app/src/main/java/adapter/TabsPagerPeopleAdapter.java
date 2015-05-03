package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.nazarkorchak.movieview.BiographyFragment;
import com.example.nazarkorchak.movieview.MovieCreditsFragment;


public class TabsPagerPeopleAdapter extends FragmentPagerAdapter {

    public TabsPagerPeopleAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // biography fragment activity
                return new BiographyFragment();
            case 1:
                // films fragment activity
                return new MovieCreditsFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}