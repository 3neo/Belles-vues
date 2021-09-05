package com.perls3.bellesvues.model.fb;

public class FbRequest {
private String address;
private String comment;
private String date ;
private String email ;
private boolean isResolved ;
private double latitude ;
private String location ;
private double longitude ;
private String pictureURL ;
private String theUsName ;
private String theUsPhoneNumber ;


    public FbRequest() {
    }

    public FbRequest(String address, String comment, String date, String email, boolean isResolved, double latitude, String location, double longitude, String pictureURL, String theUsName, String theUsPhoneNumber) {
        this.address = address;
        this.comment = comment;
        this.date = date;
        this.email = email;
        this.isResolved = isResolved;
        this.latitude = latitude;
        this.location = location;
        this.longitude = longitude;
        this.pictureURL = pictureURL;
        this.theUsName = theUsName;
        this.theUsPhoneNumber = theUsPhoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean resolved) {
        isResolved = resolved;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getTheUsName() {
        return theUsName;
    }

    public void setTheUsName(String theUsName) {
        this.theUsName = theUsName;
    }

    public String getTheUsPhoneNumber() {
        return theUsPhoneNumber;
    }

    public void setTheUsPhoneNumber(String theUsPhoneNumber) {
        this.theUsPhoneNumber = theUsPhoneNumber;
    }

    @Override
    public String toString() {
        return "FbRequest{" +
                "address='" + address + '\'' +
                ", comment='" + comment + '\'' +
                ", date='" + date + '\'' +
                ", email='" + email + '\'' +
                ", isResolved=" + isResolved +
                ", latitude=" + latitude +
                ", location='" + location + '\'' +
                ", longitude=" + longitude +
                ", pictureURL='" + pictureURL + '\'' +
                ", theUsName='" + theUsName + '\'' +
                ", theUsPhoneNumber='" + theUsPhoneNumber + '\'' +
                '}';
    }
}
