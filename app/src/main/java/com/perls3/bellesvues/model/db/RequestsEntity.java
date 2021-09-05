package com.perls3.bellesvues.model.db;

import androidx.annotation.Keep;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class RequestsEntity {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    @Keep
    @TypeConverters(value = TypeConverter.class)
    private String date;
    private String localisation;
    private String comment;
    private String imageName;
    private boolean isResolved;
    private String user;


    public RequestsEntity(String date, String localisation, String comment, String imageName, boolean isResolved, String user) {

        this.date = date;
        this.localisation = localisation;
        this.comment = comment;
        this.imageName = imageName;
        this.isResolved = isResolved;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean resolved) {
        isResolved = resolved;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "RequestsEntity{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", localisation='" + localisation + '\'' +
                ", comment='" + comment + '\'' +
                ", imageName='" + imageName + '\'' +
                ", isResolved=" + isResolved +
                ", user='" + user + '\'' +
                '}';
    }
}


