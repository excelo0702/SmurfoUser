package com.example.chhots.ui.Dashboard;

public class ChatPeopleModel {
    String userId,userImageurl,userName;

    public ChatPeopleModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getuserImageurl() {
        return userImageurl;
    }

    public void setuserImageurl(String profile) {
        this.userImageurl = userImageurl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ChatPeopleModel(String userId, String userImageurl, String userName) {
        this.userId = userId;
        this.userImageurl = userImageurl;
        this.userName = userName;
    }
}
