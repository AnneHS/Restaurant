package com.example.anneh.restaurant;

import java.io.Serializable;

public class MenuItem implements Serializable {
    public String name, description, imageUrl, category;
    public float price;

    // Constructor
    public MenuItem(String name, String description, String imageUrl, float price, String category) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public float getPrice() {
        return price;
    }


    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
