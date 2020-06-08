package com.example.today.models;

public class FeaturedHelperClass {
    String image;
    String title, description;

    public FeaturedHelperClass(String image, String title, String description) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
