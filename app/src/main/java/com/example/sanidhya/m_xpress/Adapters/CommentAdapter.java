package com.example.sanidhya.m_xpress.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sanidhya.m_xpress.Comment;
import com.example.sanidhya.m_xpress.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter {

    private ArrayList<Comment> commentArrayList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView commentText, commentTime;

        MyViewHolder(View v){
            super(v);
            commentText = v.findViewById(R.id.comment_text);
            commentTime = v.findViewById(R.id.comment_time);
        }
    }

    public  CommentAdapter(ArrayList<Comment> listofcomments){
        this.commentArrayList = listofcomments;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comment_layout, viewGroup, false);
        return  new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder holder = (MyViewHolder) viewHolder;
        Comment comment= commentArrayList.get(i);
        holder.commentText.setText(comment.getCommentText());
        holder.commentTime.setText(comment.getCommentTime());
    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
//        Comment comment= commentArrayList.get(i);
//        viewHolder.commentText.setText(comment.getCommentText());
//        viewHolder.commentTime.setText(comment.getCommentTime());
//    }

    @Override
    public int getItemCount() {
        return commentArrayList.size();
    }
}
