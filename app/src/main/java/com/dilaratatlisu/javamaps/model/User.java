package com.dilaratatlisu.javamaps.model;

import androidx.room.PrimaryKey;

public class User {

    private String name;
    private String email;
    private String username;
    private String password;
    private String imageUrl;
    private String bio;
    private String id;

    public User() {
    }

    public User(String name, String username, String imageUrl, String bio) {
        this.name = name;
        this.username = username;
        this.imageUrl = imageUrl;
        this.bio = bio;
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String userName, String password, String imageUrl, String bio, String id) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.imageUrl = imageUrl;
        this.bio = bio;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
