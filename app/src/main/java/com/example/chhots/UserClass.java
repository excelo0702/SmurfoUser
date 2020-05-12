package com.example.chhots;

public class UserClass {
    public UserClass() {
    }

    private String videoId;
    private String time;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public UserClass(String videoId, String time) {
        this.videoId = videoId;
        this.time = time;
    }
}
