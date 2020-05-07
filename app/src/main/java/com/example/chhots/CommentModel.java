package com.example.chhots;

public class CommentModel {
    private String comment,time,user;

    public CommentModel() { }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public CommentModel(String comment, String time, String user) {
        this.comment = comment;
        this.time = time;
        this.user = user;
    }
}
