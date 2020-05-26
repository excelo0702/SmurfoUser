package com.example.SmurfoUser.bottom_navigation_fragments.Calendar;

public class CalendarModel {


    public CalendarModel() {
    }

    private String category,date,title,description,url,id;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CalendarModel(String category, String date, String title, String description, String url, String id) {
        this.category = category;
        this.date = date;
        this.title = title;
        this.description = description;
        this.url = url;
        this.id = id;
    }
}
