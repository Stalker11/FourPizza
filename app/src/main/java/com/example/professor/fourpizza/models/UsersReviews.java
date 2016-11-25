package com.example.professor.fourpizza.models;

public class UsersReviews {
    private String textReview;
    private String userFirstName;
    private String userLastName;
    private String userPhotoPrefix;
    private String userPhotoSuffix;

    public String getUserPhotoPrefix() {
        return userPhotoPrefix;
    }

    public void setUserPhotoPrefix(String userPhotoPrefix) {
        this.userPhotoPrefix = userPhotoPrefix;
    }

    public String getUserPhotoSuffix() {
        return userPhotoSuffix;
    }

    public void setUserPhotoSuffix(String userPhotoSuffix) {
        this.userPhotoSuffix = userPhotoSuffix;
    }

    public String getTextReview() {
        return textReview;
    }

    public void setTextReview(String textReview) {
        this.textReview = textReview;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }


}
