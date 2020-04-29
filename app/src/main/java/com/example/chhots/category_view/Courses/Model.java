package com.example.chhots.category_view.Courses;

public class Model {

    String name,dance_form;
    int imageId;

    public Model(String name, String dance_form, int imageId) {
        this.name = name;
        this.dance_form = dance_form;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDance_form() {
        return dance_form;
    }

    public void setDance_form(String dance_form) {
        this.dance_form = dance_form;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
