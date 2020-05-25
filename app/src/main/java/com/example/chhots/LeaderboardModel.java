package com.example.chhots;

public class LeaderboardModel {
    private String name,points;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public LeaderboardModel(String name, String points) {
        this.name = name;
        this.points = points;
    }

    public LeaderboardModel() {
    }
}
