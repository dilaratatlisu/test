package com.dilaratatlisu.javamaps.model;

public class Places {

    private String title;
    private String location;
    private String description;
    private String image;

    public Places(String title, String location, String description, String image) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
