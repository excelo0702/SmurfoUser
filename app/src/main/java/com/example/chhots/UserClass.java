package com.example.chhots;

public class UserClass {
    public UserClass() {
    }

    //videoId====routineId
    private String videoId;
    private String time;
    private String courseId;


    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    int k;

    public UserClass(String time, String courseId,int k) {
        this.time = time;
        this.courseId = courseId;
        this.k=k;
    }

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
