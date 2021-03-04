package com.app.burger;

import java.util.List;

public class Users {
    String name;
    String state;
    int points;
    List<String> favPlates;

    public Users() {
    }

    public Users(String name, String state, int points, List<String> favPlates) {
        this.name = name;
        this.state = state;
        this.points = points;
        this.favPlates = favPlates;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public int getPoints() {
        return points;
    }

    public List<String> getFavPlates() {
        return favPlates;
    }
}
