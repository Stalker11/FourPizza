package com.example.professor.fourpizza.http.deserialize;

import android.util.Log;

import com.example.professor.fourpizza.models.UsersReviews;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ReviewsDeserialize implements JsonDeserializer<UsersReviews> {
    public static final String TAG = ReviewsDeserialize.class.getSimpleName();

    @Override
    public UsersReviews deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        UsersReviews review = new UsersReviews();
        Log.d(TAG, "deserialize: 110" + json);
        review.setTextReview(json.getAsJsonObject().get("text").toString());
        JsonObject user = json.getAsJsonObject().getAsJsonObject("user");

        review.setUserFirstName(user.get("firstName").toString());
        try {
            review.setUserLastName(user.get("lastName").toString());
        } catch (Exception e) {

        }
        Log.d(TAG, "deserialize: 110" + user);
        JsonObject photo = user.getAsJsonObject().getAsJsonObject("photo");
        review.setUserPhotoPrefix(photo.get("prefix").toString());
        review.setUserPhotoSuffix(photo.get("suffix").toString());
        Log.d(TAG, "deserialize: 110" + photo);
        Log.d(TAG, "deserialize: 110" + review.getUserFirstName() + " " + review.getUserLastName() + " "
                + review.getTextReview() + " " + review.getUserPhotoSuffix() + "  " + review.getUserPhotoPrefix());
        return review;
    }
}
