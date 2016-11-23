package com.example.professor.fourpizza.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RestrauntPictures implements Parcelable{
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(picturePrefix);
        parcel.writeString(pictureSuffix);
        parcel.writeString(width);
        parcel.writeString(height);
    }
    public static final Parcelable.Creator<RestrauntPictures> CREATOR =
            new Parcelable.Creator<RestrauntPictures>() {

                public RestrauntPictures createFromParcel(Parcel in) {
                    return new RestrauntPictures(in);
                }

                public RestrauntPictures[] newArray(int size) {
                    return new RestrauntPictures [size];
                }
            };

    private RestrauntPictures(Parcel parcel) {
        picturePrefix = parcel.readString();
        pictureSuffix = parcel.readString();
        width = parcel.readString();
        height = parcel.readString();
    }

}
