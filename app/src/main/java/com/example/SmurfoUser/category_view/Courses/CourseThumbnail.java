package com.example.SmurfoUser.category_view.Courses;

public class CourseThumbnail {

    private String CourseName,CourseId,CourseImage;
    private String instructorId;

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

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public CourseThumbnail(String courseName, String courseId, String courseImage, String instructorId) {
        CourseName = courseName;
        CourseId = courseId;
        CourseImage = courseImage;
        this.instructorId = instructorId;
    }

    public CourseThumbnail() {
    }
}
