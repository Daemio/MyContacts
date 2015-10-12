package com.example.damian.mycontacts.model;

/**
 * Created by Admin on 01.10.2015.
 */
public class UserData {
    int id;
    String description;
    String imagePath;
    boolean favorite;

    /*
    public UserData(int id, String description, String imagePath, boolean favorite) {
        this.id = id;
        this.description = description;
        this.favorite = favorite;
        this.imagePath = imagePath;
    }
    */  //NEVER!


    public UserData(String description, String imagePath, boolean favorite) {
        this.description = description;
        this.favorite = favorite;
        this.imagePath = imagePath;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserData)) return false;

        UserData userData = (UserData) o;

        //if (getId() != userData.getId()) return false; //to avoid
        if (isFavorite() != userData.isFavorite()) return false;
        if (getDescription() != null ? !getDescription().equals(userData.getDescription()) : userData.getDescription() != null)
            return false;
        return !(getImagePath() != null ? !getImagePath().equals(userData.getImagePath()) : userData.getImagePath() != null);

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getImagePath() != null ? getImagePath().hashCode() : 0);
        result = 31 * result + (isFavorite() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", favorite=" + favorite +
                '}';
    }
}
