package com.app.burger;

public class Plates {
    private String name;
    private String image;
    private int price;

    public Plates() {
    }

    public Plates(String name, String image, int price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }


    public String getImage() {
        return image;
    }


    public int getPrice() {
        return price;
    }
}
