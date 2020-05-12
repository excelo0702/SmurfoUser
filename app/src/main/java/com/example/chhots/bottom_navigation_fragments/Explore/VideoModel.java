package com.example.chhots.bottom_navigation_fragments.Explore;

public class VideoModel {
    private String user,title,category,description,url,thumbnail;

    public String getThumbnail() {
        return thumbnail;
    }

    private String contestId;
    private String courseId,price;
    private String videoId;
    private String like,share,view;
    private String sub_category;

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public VideoModel(String user, String title, String category, String description, String url, String thumbnail, String contestId, String courseId, String price, String videoId, String like, String share, String view, String sub_category) {
        this.user = user;
        this.title = title;
        this.category = category;
        this.description = description;
        this.url = url;
        this.thumbnail = thumbnail;
        this.contestId = contestId;
        this.courseId = courseId;
        this.price = price;
        this.videoId = videoId;
        this.like = like;
        this.share = share;
        this.view = view;
        this.sub_category = sub_category;
    }

    public VideoModel() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
