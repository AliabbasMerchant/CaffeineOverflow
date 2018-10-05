package com.example.sanidhya.m_xpress.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.sanidhya.m_xpress.IssueCard;
import com.example.sanidhya.m_xpress.R;

import java.util.List;

public class FeedListAdapter extends BaseAdapter {

    private List<IssueCard> feedList;
    private Activity activity;
    private LayoutInflater inflater;

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
        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view == null)
            view = inflater.inflate(R.layout.issue_card, null);



        return view;
    }
}
