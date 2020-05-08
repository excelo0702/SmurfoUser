package com.example.chhots.category_view.Courses;

public class CourseThumbnail {

    private String CourseName,CourseId,CourseImage;

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getCourseImage() {
        return CourseImage;
    }

    public void setCourseImage(String courseImage) {
        CourseImage = courseImage;
    }

    public CourseThumbnail(String courseName, String courseId, String courseImage) {
        CourseName = courseName;
        CourseId = courseId;
        CourseImage = courseImage;
    }

    public CourseThumbnail() {
    }
}
