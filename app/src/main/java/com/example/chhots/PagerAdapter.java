package com.example.chhots;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chhots.bottom_navigation_fragments.trending;
import com.example.chhots.ui.Dashboard.favorite;
import com.example.chhots.ui.home.HomeFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private int tabs;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabs = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return new HomeFragment();

            case 1:
                return new favorite();
            case 2:
                return new trending();

            case 3:
                return new instructor();
                default:
                    return new HomeFragment();


        }

    }

    @Override
    public int getCount() {
        return tabs;
    }
}
