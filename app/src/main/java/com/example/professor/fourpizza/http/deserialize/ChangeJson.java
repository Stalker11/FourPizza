package com.example.professor.fourpizza.http.deserialize;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ChangeJson {
    public static final String TAG = ChangeJson.class.getSimpleName();
    public static JsonElement change(JsonElement json){
        JsonObject obj = json.getAsJsonObject().getAsJsonObject("response");
        JsonArray venues = obj.getAsJsonArray("venues");
        return venues;
    }
}
