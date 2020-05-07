package com.example.chhots.category_view.Contest;

public class HostModel {
    private String info,imageUrl,contestId;

    public HostModel() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    public HostModel(String info, String imageUrl, String contestId) {
        this.info = info;
        this.imageUrl = imageUrl;
        this.contestId = contestId;
    }
}
