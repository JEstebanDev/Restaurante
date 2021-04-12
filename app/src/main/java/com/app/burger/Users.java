package com.app.burger;

import java.util.List;
import java.util.Map;

public class Users {
    String name;
    String state;
    int points;
    Map<String, Integer> favPlates;

    public Users() {
    }

    public Users(String name, String state, int points, Map<String, Integer> favPlates) {
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

    public Map<String, Integer> getFavPlates() {
        return favPlates;
    }

}
