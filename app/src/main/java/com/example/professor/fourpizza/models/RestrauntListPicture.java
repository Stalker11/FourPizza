package com.example.professor.fourpizza.models;

import com.google.gson.annotations.SerializedName;

public class RestrauntListPicture {
    @SerializedName("prefix")
    private String picturePrefix;
    @SerializedName("suffix")
    private String pictureSuffix;
    @SerializedName("width")
    private String width;
    @SerializedName("height")
    private String height;

    public String getPicturePrefix() {
        return picturePrefix;
    }

    public String getPictureSuffix() {
        return pictureSuffix;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }
}
