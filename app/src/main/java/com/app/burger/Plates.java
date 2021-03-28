package com.app.burger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Plates implements Serializable {
    private String id;
    private String name;
    private String description;
    private String image;
    private int price;

    public Plates() {
    }

    public Plates(String id, String name, String description,String image, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}
