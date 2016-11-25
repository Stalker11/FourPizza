package com.example.professor.fourpizza.http.deserialize;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ChangeJson {
    public static final String TAG = ChangeJson.class.getSimpleName();
    public static JsonElement changeRestrauntJson(JsonElement json){
        JsonObject obj = json.getAsJsonObject().getAsJsonObject("response");
        JsonArray venues = obj.getAsJsonArray("venues");
        return venues;
    }
    public static JsonElement changePictureJson(JsonElement json){
        JsonObject obj = json.getAsJsonObject().getAsJsonObject("response");
        JsonObject photos = obj.getAsJsonObject().getAsJsonObject("photos");
        JsonArray items = photos.getAsJsonArray("items");
        return items;
    }
    public static JsonElement changeReviewsJson(JsonElement json){
        JsonObject obj = json.getAsJsonObject().getAsJsonObject("response");
        JsonObject likes = obj.getAsJsonObject().getAsJsonObject("tips");
        JsonArray items = likes.getAsJsonArray("items");
        return items;
    }
}
