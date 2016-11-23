package com.example.professor.fourpizza.http;

import android.util.Log;

import com.example.professor.fourpizza.callback.ApiCallBack;
import com.example.professor.fourpizza.http.deserialize.ChangeJson;
import com.example.professor.fourpizza.http.deserialize.RestrauntDeserialize;
import com.example.professor.fourpizza.http.deserialize.RestrauntListPicturesDeserialize;
import com.example.professor.fourpizza.models.PizzaRestraunt;
import com.example.professor.fourpizza.models.RestrauntListPicture;
import com.example.professor.fourpizza.util.ParserXml;
import com.example.professor.fourpizza.util.ProjectConstans;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestPizza {
    private List<PizzaRestraunt> restraunts = new ArrayList<>();
    private List<RestrauntListPicture> pictures = new ArrayList<>();
    private RestrauntListPicture restrauntPicture;
    private static final String TAG = RequestPizza.class.getSimpleName();
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ProjectConstans.URL_SERVER)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private Request request = retrofit.create(Request.class);

    public void requestPizza(final ApiCallBack callBack) {
        Call<JsonElement> result = request.getPlaces(ProjectConstans.CLIENT_ID, ProjectConstans.CLIENT_SECRET,
                getLocation(), "pizza", 800, getDate());
        Log.d(TAG, "requestPizza: " + request.toString());
        result.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Gson gson = new GsonBuilder().registerTypeAdapter(PizzaRestraunt.class, new RestrauntDeserialize()).create();
                Log.d(TAG, "onResponse: "+call.request().toString());
               // restraunt = gson.fromJson(response.body().toString(), PizzaRestraunt.class);
                restraunts = gson.fromJson(ChangeJson.change(response.body())
                        , new TypeToken<ArrayList<PizzaRestraunt>>() {
                        }.getType());
                Collections.sort(restraunts, new Comparator<PizzaRestraunt>() {
                    @Override
                    public int compare(PizzaRestraunt pizzaRestraunt, PizzaRestraunt t1) {
                        if(pizzaRestraunt.getDistance() < t1.getDistance())return -1;
                        if(pizzaRestraunt.getDistance() == t1.getDistance())return 0;
                        if(pizzaRestraunt.getDistance() > t1.getDistance())return 1;
                        return 0;
                    }
                });
                for (PizzaRestraunt res:restraunts) {
                    requestPhotos(res.getId(),callBack);
                }
              //  Log.d(TAG, "onResponse: " + restraunt.getId());
                Log.d(TAG, "onResponse: " + response.body() + "  " + response.message() + "  " + call.request().toString());
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                callBack.onError(t.getMessage());
            }
        });
    }

    public void requestPhotos(String objectId, final ApiCallBack callBack) {
        Call<JsonElement> result = request.getPhotos(changeIdString(objectId), ProjectConstans.CLIENT_ID
                , ProjectConstans.CLIENT_SECRET, getDate());
        Log.d(TAG, "requestPhotos: "+changeIdString(objectId)+" "+getDate());

        result.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Gson gson = new GsonBuilder().registerTypeAdapter(RestrauntListPicture.class, new RestrauntListPicturesDeserialize()).create();
                Log.d(TAG, "onResponse: " + response.body() + "  " + response.message() + "  " + call.request().toString());

                try {
                    restrauntPicture = gson.fromJson(response.body(), RestrauntListPicture.class);
                    Log.d(TAG, "onResponse: "+restrauntPicture.getWidth()+" "+restrauntPicture.getHeight()+" "+restrauntPicture.getPictureSuffix()+" "+restrauntPicture.getPicturePrefix());
                }catch (NullPointerException e){
                    Log.d(TAG, "onResponse: null"+e.getMessage());
                }
                 pictures.add(restrauntPicture);
                if(pictures.size() == restraunts.size()){
                    callBack.onSucsess(restraunts,pictures);
                    Log.d(TAG, "onResponse: "+pictures.size());
                }
             
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
    private String getLocation(){
        List<String> loc = new ParserXml().parseGeolocation();
        StringBuilder builder = new StringBuilder();
        builder.append(loc.get(0))
                .append(",")
                .append(loc.get(1));
        Log.d(TAG, "setLocation: "+builder.toString());
        return builder.toString();
    }
    private String getDate(){
        List<String> time = new ParserXml().parseGeolocation();
        return time.get(2);
    }
    private String changeIdString(String objectId){
        return objectId.substring(1,objectId.length()-1);
    }
     
}
