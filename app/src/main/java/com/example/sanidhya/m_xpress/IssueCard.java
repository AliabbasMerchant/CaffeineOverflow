package com.example.sanidhya.m_xpress;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class IssueCard extends CardView implements View.OnClickListener {
    public JSONObject getData() {
        return data;
    }

    JSONObject data;
    TextView issue_title, upvote_count, comment_count, location, issue_category, timestamp;
    ImageView issue_image;

    //    TextView issue_desc;
    public IssueCard(Context context) {
        super(context);
        setOnClickListener(this);
        LayoutInflater.from(getContext()).inflate(R.layout.issue_card, this, true);
    }
    public  IssueCard(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        setOnClickListener(this);
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
        timestamp = findViewById(R.id.timestamp);
        issue_category = findViewById(R.id.issue_category);
//        issue_desc = findViewById(R.id.issue_desc);
        upvote_count = findViewById(R.id.upvote_count);
        location = findViewById(R.id.location);
        comment_count = findViewById(R.id.comment_count);

        try {
            issue_title.setText(data.getString("title"));
//            issue_desc.setText(data.getString("description"));
            Picasso.get()
                    .load(data.getString("image"))
                    .resize(getWidth(), 100)
                    .centerCrop()
                    .into(issue_image);
            location.setText(data.getString("ward"));
            comment_count.setText(data.getString("comment_count"));
            upvote_count.setText(data.getString("upvotes"));
            timestamp.setText(data.getString("timestamp"));
            issue_category.setText(data.getString("category"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View view) {
        Intent i = new Intent(view.getContext(), IssueActivity.class);
        i.putExtra("data", data.toString());
        view.getContext().startActivity(i);

    }
}
