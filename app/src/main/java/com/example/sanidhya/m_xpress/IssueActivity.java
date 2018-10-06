package com.example.sanidhya.m_xpress;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sanidhya.m_xpress.Adapters.FeedListAdapter;
import com.example.sanidhya.m_xpress.Adapters.CommentAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

        Intent intent = getIntent();
        if(intent.getData() != null) {
            try {
                generalData = new JSONObject(intent.getStringExtra("data"));
                _id = generalData.getInt("card_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
        hasUserVoted(_id);
        if(userHasVoted) {
            upvote_count.setTextColor(getResources().getColor(R.color.upvoteColor));
        }


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
    public void hasUserVoted(int _id) {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String URL = Constants.FEED_URL;
//        StringRequest sr = new StringRequest(Request.Method.GET, URL, response -> {
//            Log.e(TAG, "onResponse: " + response);
//            if(response.equals("1")) {
//                userHasVoted = true;
//            }
//        }, error -> Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show());
//        queue.add(sr);
        userHasVoted = false;
    }
    public ArrayList<Comment> getComments(int _id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = Constants.COMMENTS_URL + "?card_id=" + _id;
        StringRequest sr = new StringRequest(Request.Method.GET, URL, response -> {
            Log.e(TAG, "onResponse: " + response);
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(response);
                // todo
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show());
        queue.add(sr);
        return new ArrayList<>();
    }

    @Override
    public void onBackPressed() {
        try {
            if(generalData.getInt("upvotes")!= votes) {
                String URL;
                if(generalData.getInt("upvotes")> votes) {
//                    URL = Constants.FEED_URL; //downvote
                }
                else {
//                    URL = Constants.FEED_URL; //upvote
                }
//                RequestQueue queue = Volley.newRequestQueue(this);
//                StringRequest sr = new StringRequest(Request.Method.GET, URL, response -> {
//                    Log.e(TAG, "onResponse: " + response);
//                }, error -> Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void onLocationPressed(View view) {
        try {
            double latitude = generalData.getDouble("lat"), longitude = generalData.getDouble("lng");
            String url = "https://www.google.com/maps/search/?api=1&query="+latitude+","+longitude;
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
            startActivity(intent);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void upload_comment(String text) {
        String _id = getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE).getString(Constants.UID_PREF, "");
        if(_id != null) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String URL = Constants.COMMENT_URL + "?card_id=" + _id + "&user_id=" + _id + "&text=" + text;
            StringRequest sr = new StringRequest(Request.Method.GET, URL, response -> {
                Log.e(TAG, "onResponse: " + response);
            }, error -> Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show());
            queue.add(sr);
        }
        else {
            Toast.makeText(this, "Please Login!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}