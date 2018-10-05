package com.example.sanidhya.m_xpress.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sanidhya.m_xpress.IssueCard;
import com.example.sanidhya.m_xpress.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FeedListAdapter extends BaseAdapter {

    private List<IssueCard> feedList;
    private Activity activity;
    private LayoutInflater inflater;

    JSONObject data;
    TextView issue_title, upvote_count, comment_count, location, issue_category, timestamp;
    ImageView issue_image;

    public FeedListAdapter(Activity activity, List<IssueCard> feedList) {
        super();
        this.activity = activity;
        this.feedList = feedList;
    }

    @Override
    public int getCount() {
        return feedList.size();
    }

    @Override
    public Object getItem(int i) {
        return feedList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.issue_card, null);

        data = feedList.get(i).getData();

        issue_title = view.findViewById(R.id.issue_title);
        issue_image = view.findViewById(R.id.issue_image);
        timestamp = view.findViewById(R.id.timestamp);
        issue_category = view.findViewById(R.id.issue_category);
        upvote_count = view.findViewById(R.id.upvote_count);
        location = view.findViewById(R.id.location);
        comment_count = view.findViewById(R.id.comment_count);

        //TO-DO INITIALISE data WITH JSONObject

        try {
            issue_title.setText(data.getString("title"));
            Picasso.get()
                    .load(data.getString("image"))
                    .resize(view.getWidth(), 100)
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
        return view;
    }
}
