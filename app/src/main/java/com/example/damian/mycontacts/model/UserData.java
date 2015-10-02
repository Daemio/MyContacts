package com.example.damian.mycontacts.model;

/**
 * Created by Admin on 01.10.2015.
 */
public class UserData {
    String description;
    String number;
    boolean favorite;
    String imagePath;

    public UserData(String description, String number, boolean favorite, String imagePath) {
        this.description = description;
        this.number = number;
        this.favorite = favorite;
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
