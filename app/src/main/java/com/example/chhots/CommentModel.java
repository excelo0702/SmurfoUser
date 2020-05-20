package com.example.chhots;

public class CommentModel {
    private String comment,time,userId,userImage,userName;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public CommentModel(String comment, String time, String userId, String userImage, String userName) {
        this.comment = comment;
        this.time = time;
        this.userId = userId;
        this.userImage = userImage;
        this.userName = userName;
    }
}
