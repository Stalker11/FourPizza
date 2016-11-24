package com.example.professor.fourpizza.http.deserialize;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class LikesDeserialize implements JsonDeserializer<String> {
    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject().getAsJsonObject("response");
        JsonObject likes = obj.getAsJsonObject("likes");
        JsonElement like = likes.get("summary");
        return like.toString();
    }
}
