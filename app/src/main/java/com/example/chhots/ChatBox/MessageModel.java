package com.example.chhots.ChatBox;

public class MessageModel {
    private String video;
    private String image;
    private String message,time;
    int flag=0;

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public MessageModel(String message, String time, int flag, String video, String image) {
        this.video = video;
        this.image = image;
        this.message = message;
        this.time = time;
        this.flag = flag;
    }

    MessageModel(){}


}
