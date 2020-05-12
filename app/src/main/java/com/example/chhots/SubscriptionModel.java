package com.example.chhots;

public class SubscriptionModel {
    public SubscriptionModel() {
    }
    String time,videoId;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public SubscriptionModel(String time, String videoId) {
        this.time = time;
        this.videoId = videoId;
    }
}
