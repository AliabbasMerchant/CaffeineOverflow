package com.example.sanidhya.m_xpress.Fragments;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.sanidhya.m_xpress.Adapters.FeedPagerAdapter;
import com.example.sanidhya.m_xpress.MainActivity;
import com.example.sanidhya.m_xpress.R;

public class FeedFragment extends Fragment {

    private static final String TAG = "MapFeedFragment";
    protected FeedPagerAdapter adapter;
    private TabLayout tabs;
    private ViewPager viewPager;
    public FeedFragment() {
        // Required empty public constructor
    }
//    public static MapFeedFragment newInstance(String param1, String param2) {
//        MapFeedFragment fragment = new MapFeedFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        SearchView searchView = new SearchView(((MainActivity) getContext()).getSupportActionBar().getThemedContext());
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        tabs = v.findViewById(R.id.tabs);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        adapter = new FeedPagerAdapter(fm);

        viewPager = v.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        Log.d("MapFeedFragment", "View_pager loading");
        tabs.setupWithViewPager(viewPager);
        return v;
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_search:
//                Log.e(TAG, "onOptionsItemSelected: ");
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
//        super.onCreateOptionsMenu(menu, menuInflater);
//        menu.clear();
//        menuInflater.inflate(R.menu.options_menu, menu);
//        MenuItem item = menu.findItem(R.id.menu_search);
//        SearchView searchView = new SearchView(((MainActivity) getContext()).getSupportActionBar().getThemedContext());
//        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
//        MenuItemCompat.setActionView(item, searchView);
//    }
}
