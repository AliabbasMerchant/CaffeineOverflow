package com.example.sanidhya.m_xpress;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class IssueCard extends ConstraintLayout {
    JSONObject data;
    TextView issue_title, upvote_count, comment_count, location, issue_category;
    ImageView issue_image;
    TextView issue_desc;
    public IssueCard(Context context) {
        super(context);
        LayoutInflater.from(getContext()).inflate(R.layout.issue_card, this, true);
    }
    public IssueCard(Context context, JSONObject data) {
        super(context);
        this.data = data;
        LayoutInflater.from(getContext()).inflate(R.layout.issue_card, this, true);
        inflate_data();
    }
    public void inflate_data() {
        issue_title = findViewById(R.id.issue_title);
        issue_image = findViewById(R.id.issue_image);
        issue_category = findViewById(R.id.issue_category);
//        issue_desc = findViewById(R.id.issue_desc);
        upvote_count = findViewById(R.id.upvote_count);
        location = findViewById(R.id.location);
        comment_count = findViewById(R.id.comment_count);

        try {
            issue_title.setText(data.getString("Title"));
//            issue_desc.setText(data.getString("Desc"));
            Picasso.get()
                    .load(data.getString("ImageURL"))
                    .resize(getWidth(), 100)
                    .centerCrop()
                    .into(issue_image);
            location.setText(data.getString("Location"));
            comment_count.setText(data.getString("CommentCount"));
            upvote_count.setText(data.getString("UpvoteCount"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
