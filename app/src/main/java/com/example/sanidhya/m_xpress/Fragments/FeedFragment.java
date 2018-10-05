package com.example.sanidhya.m_xpress.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanidhya.m_xpress.Adapters.FeedPagerAdapter;
import com.example.sanidhya.m_xpress.R;

public class FeedFragment extends Fragment {

    protected FeedPagerAdapter adapter;
    private TabLayout tabs;
    private ViewPager viewPager;
    public FeedFragment() {
        // Required empty public constructor
    }
//    public static FeedFragment newInstance(String param1, String param2) {
//        FeedFragment fragment = new FeedFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        adapter = new FeedPagerAdapter(getActivity().getSupportFragmentManager());

        viewPager = v.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        tabs.setupWithViewPager(viewPager);
        return v;
    }

}
