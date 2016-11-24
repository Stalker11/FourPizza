package com.example.professor.fourpizza.http.deserialize;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class TimeWorkDeserialize implements JsonDeserializer<String> {
    private final String TAG = TimeWorkDeserialize.class.getSimpleName();
    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject().getAsJsonObject("response");
        Log.d(TAG, "deserialize:time "+obj);
        JsonObject hours = obj.getAsJsonObject("hours");
        Log.d(TAG, "deserialize:time "+hours);
        JsonArray timeFrames = hours.getAsJsonArray("timeframes");
        Log.d(TAG, "deserialize:time "+timeFrames);
        String startTime = null;
        String endTime = null;
        try{
            JsonObject open = (JsonObject) timeFrames.get(0);

            JsonArray  time = open.getAsJsonArray("open");
            JsonObject objTime = (JsonObject) time.get(0);

            startTime = objTime.getAsJsonObject().get("start").toString();
            endTime = objTime.getAsJsonObject().get("end").toString();
            Log.d(TAG, "deserialize:time "+startTime+" "+endTime);
        }catch (NullPointerException e){

        }
        return createTime(startTime,endTime);
    }
    private String createTime(String startTime, String endTime){
        if(startTime == null || endTime == null) return null;
        char [] start = changeTimeString(startTime).toCharArray();
        char [] end = changeTimeString(endTime).toCharArray();

        StringBuilder build = new StringBuilder();
        build.append("Start:")
                .append(start[0])
                .append(start[1])
                .append("-")
                .append(start[2])
                .append(start[3])
                .append(";  ")
                .append("End:")
                .append(end[0])
                .append(end[1])
                .append("-")
                .append(end[2])
                .append(end[3])
                .append(";");
        return build.toString();
    }
    private String changeTimeString(String changeString){
        return changeString.substring(1,changeString.length()-1);
    }
}
