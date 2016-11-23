package com.example.professor.fourpizza.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PizzaRestraunt implements Parcelable{
    private String id;
    private String restrauntName;
    private String phone;
    private String twitter;
    private String facebook;
    private int distance;
    private String adress;
    private String category;

    public String getId() {
        return id;
    }

    public String getRestrauntName() {
        return restrauntName;
    }

    public String getPhone() {
        return phone;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public int getDistance() {
        return distance;
    }

    public String getAdress() {
        return adress;
    }

    public String getCategory() {
        return category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRestrauntName(String restrauntName) {
        this.restrauntName = restrauntName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(facebook);
        parcel.writeString(twitter);
        parcel.writeString(category);
        parcel.writeString(phone);
        parcel.writeString(adress);
        parcel.writeString(restrauntName);
        parcel.writeInt(distance);

    }
    public static final Parcelable.Creator<PizzaRestraunt> CREATOR =
            new Parcelable.Creator<PizzaRestraunt>() {

                public PizzaRestraunt createFromParcel(Parcel in) {
                    return new PizzaRestraunt(in);
                }

                public PizzaRestraunt[] newArray(int size) {
                    return new PizzaRestraunt[size];
                }
            };
    private PizzaRestraunt(Parcel parcel) {
        id = parcel.readString();
        restrauntName = parcel.readString();
        phone = parcel.readString();
        category = parcel.readString();
        facebook = parcel.readString();
        twitter = parcel.readString();
        adress = parcel.readString();
        distance = parcel.readInt();
    }
    public PizzaRestraunt(){

    }
}
