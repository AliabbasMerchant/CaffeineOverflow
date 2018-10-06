package com.example.sanidhya.m_xpress.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.example.sanidhya.m_xpress.Fragments.*;

public class FeedPagerAdapter extends FragmentStatePagerAdapter {

    public FeedPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        if(i == 0){
            return new MapFeedFragment();
        }
        return new TrendingFeedFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Recent";
            case 1: return "Trending";

            default: return null;
        }
    }
}

