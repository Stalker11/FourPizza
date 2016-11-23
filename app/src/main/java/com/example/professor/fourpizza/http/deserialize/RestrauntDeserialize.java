package com.example.professor.fourpizza.http.deserialize;

import android.util.Log;

import com.example.professor.fourpizza.models.PizzaRestraunt;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class RestrauntDeserialize implements JsonDeserializer<PizzaRestraunt> {
    public static final String TAG = RestrauntDeserialize.class.getSimpleName();

    @Override
    public PizzaRestraunt deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        PizzaRestraunt restraunt = new PizzaRestraunt();
        //  Log.d(TAG, "deserialize: "+json);
        // JsonElement objects = json.getAsJsonArray();
        JsonElement id = json.getAsJsonObject().get("id");
        JsonObject contact = json.getAsJsonObject().getAsJsonObject("contact");
        JsonObject location = json.getAsJsonObject().getAsJsonObject("location");
        JsonArray array = json.getAsJsonObject().getAsJsonArray("categories");
        JsonArray adress = location.getAsJsonObject().getAsJsonArray("formattedAddress");
        JsonElement category = null;
        Log.d(TAG, "deserialize:test " + array);
        try {
            category = array.get(0);
            Log.d(TAG, "deserialize:test " + category);
        } catch (Exception e) {

        }
        try {
            String phone = contact.getAsJsonObject().get("formattedPhone").toString();
            Log.d(TAG, "deserialize: " + phone);
            restraunt.setPhone(phone);
        } catch (NullPointerException e) {

        }
        try {
            String twitter = contact.getAsJsonObject().get("twitter").toString();
            Log.d(TAG, "deserialize: " + twitter);
            restraunt.setTwitter(twitter);
        } catch (NullPointerException e) {

        }
        try {
            String facebook = contact.getAsJsonObject().get("facebookName").toString();
            Log.d(TAG, "deserialize: " + facebook);
            restraunt.setFacebook(facebook);
        } catch (NullPointerException e) {

        }

        try {
            String idObject = id.toString();
            Log.d(TAG, "deserialize: " + idObject);
            String restrauntName = json.getAsJsonObject().get("name").toString();
            Log.d(TAG, "deserialize: " + restrauntName);

            int distance = Integer.valueOf(location.getAsJsonObject().get("distance").toString());
            Log.d(TAG, "deserialize: " + distance);
            StringBuilder build = new StringBuilder();
            for (JsonElement str : adress) {
                build.append(str);
            }
            String adressPlace = build.toString();
            String categoryPlace = category.getAsJsonObject().get("name").toString();
            restraunt.setId(idObject);
            restraunt.setRestrauntName(restrauntName);
            restraunt.setDistance(Integer.valueOf(distance));
            restraunt.setAdress(adressPlace);
            restraunt.setCategory(categoryPlace);

        } catch (NullPointerException e) {
            Log.d(TAG, "deserialize: rrr " + e.getMessage());
        } catch (Exception e) {
            Log.d(TAG, "deserialize: dd " + e.getMessage());
        }

        return restraunt;
    }
}
