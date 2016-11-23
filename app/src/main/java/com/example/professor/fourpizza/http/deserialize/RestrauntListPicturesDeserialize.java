package com.example.professor.fourpizza.http.deserialize;

import android.util.Log;

import com.example.professor.fourpizza.models.RestrauntListPicture;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class RestrauntListPicturesDeserialize implements JsonDeserializer<RestrauntListPicture>{
    private static final String TAG = RestrauntListPicturesDeserialize.class.getSimpleName();
    @Override
    public RestrauntListPicture deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject().getAsJsonObject("response");
        JsonObject photos = obj.getAsJsonObject().getAsJsonObject("photos");
        JsonArray array = photos.getAsJsonArray("items");
        try{
            Log.d(TAG, "deserialize:pictures "+array.get(0));
            return new Gson().fromJson(array.get(0),RestrauntListPicture.class);
        }catch (Exception e){

        }
        return null;
    }
}
