package com.example.sanidhya.m_xpress;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.sanidhya.m_xpress.Adapters.CommentAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IssueActivity extends AppCompatActivity {
    JSONObject generalData, commentData;
    boolean userHasVoted = false;
    int votes;
    int _id = 1;
    private static final String TAG = "IssueActivity";

    SwipeRefreshLayout mySwipeRefreshLayout;
    TextView issue_title, upvote_count, comment_count, location, issue_category, timestamp, issue_desc;
    ImageView issue_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

//        Intent intent = getIntent();
//        if(intent.getData() != null) {
            try {
//                generalData = new JSONObject(intent.getStringExtra("data"));
                generalData = new JSONObject("{ \"card_id\": 1, \"category\": \"MISC\", \"comment_count\": 2, \"image\": \"image\", \"lat\": 72.8403, \"lng\": 18.9488, \"timestamp\": \"2018-10-05 22:47:11\", \"title\": \"Card1\", \"upvotes\": 0, \"ward\": \"Fort\" }");
                _id = generalData.getInt("card_id");
            } catch (JSONException e) {
                e.printStackTrace();
//            }
        }
        getComments(_id);

        issue_title = findViewById(R.id.issue_title);
        issue_image = findViewById(R.id.issue_image);
        timestamp = findViewById(R.id.timestamp);
        issue_category = findViewById(R.id.issue_category);
        issue_desc = findViewById(R.id.issue_desc);
        upvote_count = findViewById(R.id.upvote_count);
        location = findViewById(R.id.location);

        inflateGeneralData();
        inflateComments();
        userHasVoted = hasUserVoted(_id);
        if(userHasVoted) {
            upvote_count.setTextColor(getResources().getColor(R.color.upvoteColor));
        }

        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);

        mySwipeRefreshLayout.setOnRefreshListener( () -> {
                    Log.i(TAG, "onRefresh called from SwipeRefreshLayout");

                    // This method performs the actual data-refresh operation.
                    // The method calls setRefreshing(false) when it's finished.
                    getComments(_id);
                    inflateComments();
                }
        );
    }
    public void inflateGeneralData() {
        try {
            issue_title.setText(generalData.getString("title"));
            issue_desc.setText(generalData.getString("description"));
            Picasso.get()
                    .load(generalData.getString("image"))
                    .into(issue_image);
            location.setText(generalData.getString("ward"));
            issue_category.setText(generalData.getString("category"));
            upvote_count.setText(generalData.getString("upvotes"));
            timestamp.setText(generalData.getString("timestamp"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void inflateComments() {
        RecyclerView recyclerView = findViewById(R.id.comments_recycler_view);
        CommentAdapter adapter = new CommentAdapter(getComments(_id));
        recyclerView.setAdapter(adapter);
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
    public ArrayList<Comment> getComments(int _id) {
        return new ArrayList<Comment>();
    }
    @Override
    public void onBackPressed() {
        try {
            if(generalData.getInt("upvotes")!= votes) {
                if(generalData.getInt("upvotes")> votes) {
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

}