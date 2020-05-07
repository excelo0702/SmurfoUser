package com.example.chhots.bottom_navigation_fragments.Explore;

public class VideoModel {
    private String user;
    private String comment;
    private String category;
    private String uri;
    private String title;
    private String videoId;
    private int upvote,downvote,views;

    public VideoModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getUpvote() {
        return upvote;
    }

    public void setUpvote(int upvote) {
        this.upvote = upvote;
    }

    public int getDownvote() {
        return downvote;
    }

    public void setDownvote(int downvote) {
        this.downvote = downvote;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public VideoModel(String user,String title, String category , String videoId, String comment, String uri,  int upvote, int downvote, int views) {
        this.user = user;
        this.comment = comment;
        this.category = category;
        this.uri = uri;
        this.title = title;
        this.videoId = videoId;
        this.upvote = upvote;
        this.downvote = downvote;
        this.views = views;
    }
}
