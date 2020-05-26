package com.example.SmurfoUser.category_view.routine;

public class RoutineThumbnailModel {
    String title,instructor_name,routine_views,routine_level,routineId,routineThumbnail,instructorId;

    String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public RoutineThumbnailModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructor_name() {
        return instructor_name;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public void setInstructor_name(String instructor_name) {
        this.instructor_name = instructor_name;
    }

    public String getRoutine_views() {
        return routine_views;
    }

    public void setRoutine_views(String routine_views) {
        this.routine_views = routine_views;
    }

    public String getRoutine_level() {
        return routine_level;
    }

    public void setRoutine_level(String routine_level) {
        this.routine_level = routine_level;
    }

    public String getRoutineId() {
        return routineId;
    }

    public void setRoutineId(String routineId) {
        this.routineId = routineId;
    }

    public String getRoutineThumbnail() {
        return routineThumbnail;
    }

    public void setRoutineThumbnail(String routineThumbnail) {
        this.routineThumbnail = routineThumbnail;
    }

    public RoutineThumbnailModel(String title, String instructor_name, String routine_views, String routine_level, String routineId, String routineThumbnail,String instructorId,String category) {
        this.title = title;
        this.instructor_name = instructor_name;
        this.routine_views = routine_views;
        this.routine_level = routine_level;
        this.routineId = routineId;
        this.routineThumbnail = routineThumbnail;
        this.instructorId = instructorId;
        this.category = category;
    }
}
