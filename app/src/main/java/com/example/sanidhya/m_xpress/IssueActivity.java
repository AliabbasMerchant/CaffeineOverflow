package com.example.sanidhya.m_xpress;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class IssueActivity extends AppCompatActivity {
    JSONObject generalData, commentData;
    boolean userHasVoted = false;
    int votes;
    int _id = 1;
    private static final String TAG = "IssueActivity";

    TextView upvote_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        Intent intent = getIntent();
        if(intent.getData() != null) {
            try {
                generalData = new JSONObject(intent.getStringExtra("data"));
                _id = generalData.getInt("_id"); // TODO
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        getComments(_id);
        inflateGeneralData();
        inflateComments();
        userHasVoted = hasUserVoted(_id);
        if(userHasVoted) {
            upvote_count.setTextColor(getResources().getColor(R.color.upvoteColor));
        }
    }
    public void inflateGeneralData() {
    }
    public void inflateComments() {
//        todo
    }
    public void onVotePressed(View view) {
        votes = Integer.parseInt(upvote_count.getText().toString());
        if(userHasVoted) {
            userHasVoted = false;
            votes--;
            upvote_count.setTextColor(Color.parseColor("#808080"));
        } else {
            userHasVoted = true;
            votes++;
            upvote_count.setTextColor(getResources().getColor(R.color.upvoteColor));
        }
        upvote_count.setText(Integer.toString(votes));
    }
    public boolean hasUserVoted(int _id) {
        return false;
        // todo
    }
    public void getComments(int _id) {
        // todo
    }
    @Override
    public void onBackPressed() {
        try {
            if(generalData.getInt("UpvoteCount")!= votes) {
                if(generalData.getInt("UpvoteCount")> votes) {
                    // TODO upvote
                }
                else {
                    // TODO downvote
                }
            }
        } catch (JSONException e) {// TODO vote
            e.printStackTrace();
        }
    }
    public void onLocationPressed(View view) {
        double latitude = 0, longitude = 0;
        // todo
        String url = "https://www.google.com/maps/search/?api=1&query="+latitude+","+longitude;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
        startActivity(intent);
    }
    mySwipeRefreshLayout.setOnRefreshListener(
            new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            Log.i(TAG, "onRefresh called from SwipeRefreshLayout");

            // This method performs the actual data-refresh operation.
            // The method calls setRefreshing(false) when it's finished.
            getComments(_id);
            inflateComments();
        }
    }
);
}

