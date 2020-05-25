package com.example.chhots.ui.Dashboard.HistoryPackage;

public class HistoryModel {
    public HistoryModel() {
    }
    private String title,description,url,date,category,id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public HistoryModel(String title, String description, String url, String date, String category, String id) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.date = date;
        this.category = category;
        this.id = id;
    }
}
