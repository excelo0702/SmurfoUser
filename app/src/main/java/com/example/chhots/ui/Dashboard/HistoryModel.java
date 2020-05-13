package com.example.chhots.ui.Dashboard;

public class HistoryModel {
    public HistoryModel() {
    }
    private String title,description,url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HistoryModel(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }
}
