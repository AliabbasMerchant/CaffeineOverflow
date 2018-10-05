package com.example.sanidhya.m_xpress.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanidhya.m_xpress.Adapters.FeedListAdapter;
import com.example.sanidhya.m_xpress.Constants;
import com.example.sanidhya.m_xpress.IssueCard;
import com.example.sanidhya.m_xpress.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrendingFeedFragment extends Fragment {
    private static final String TAG = "TrendingFeedFragment";

    public TrendingFeedFragment() {
        // Required empty public constructor
    }


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
        View view = inflater.inflate(R.layout.fragment_trending_feed, container, false);


        RequestQueue queue = Volley.newRequestQueue(getContext());
        String URL = Constants.FEED_URL; // TODO
        StringRequest sr = new StringRequest(Request.Method.GET, URL, response -> {
            Log.e(TAG, "onResponse: " + response);
            List<IssueCard> issueCardList;
            JSONObject jsonObject;
            JSONArray jsonArray;
            FeedListAdapter feedListAdapter;
            ListView listView;
            issueCardList = new ArrayList<>();
            IssueCard card;

//            String jsonString = "{\"cards\":[{ \"card_id\": 1, \"category\": \"MISC\", \"comment_count\": 2, \"image\": \"image\", \"lat\": 72.8403, \"lng\": 18.9488, \"timestamp\": \"2018-10-05 22:47:11\", \"title\": \"Card1\", \"upvotes\": 0, \"ward\": \"Fort\" }, { \"card_id\": 2, \"category\": \"SERVICES\", \"comment_count\": 0, \"image\": \"image\", \"lat\": 27.8403, \"lng\": 18.9488, \"timestamp\": \"2018-10-05 23:35:20\", \"title\": \"Card2\", \"upvotes\": 0, \"ward\": \"Chira Bazar-Kalbadevi\" }]}";
            try {
                jsonObject = new JSONObject(response);
                jsonArray = jsonObject.getJSONArray("cards");
                for (int i = 0; i < jsonArray.length(); i++) {
                    card = new IssueCard(getContext(), (JSONObject) jsonArray.get(i));
                    issueCardList.add(card);
                }
                feedListAdapter = new FeedListAdapter(getActivity(), issueCardList);
                listView = view.findViewById(R.id.feed_recycler_view);
                listView.setAdapter(feedListAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getContext(), "That didn't work!", Toast.LENGTH_SHORT).show());
        queue.add(sr);

        return view;
    }

}

