package com.example.sanidhya.m_xpress;

public class Comment {
    String commentText;
    String commentTime;

    public Comment(String commentText, String commentTime) {
        this.commentText = commentText;
        this.commentTime = commentTime;
    }


    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
}
