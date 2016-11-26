package com.example.professor.fourpizza.http;

import android.util.Log;

import com.example.professor.fourpizza.callback.ApiCallBack;
import com.example.professor.fourpizza.callback.LikesCallBack;
import com.example.professor.fourpizza.callback.PhotosCallBack;
import com.example.professor.fourpizza.callback.ReviewsCallBack;
import com.example.professor.fourpizza.http.deserialize.ChangeJson;
import com.example.professor.fourpizza.http.deserialize.LikesDeserialize;
import com.example.professor.fourpizza.http.deserialize.RestrauntDeserialize;
import com.example.professor.fourpizza.http.deserialize.RestrauntListPicturesDeserialize;
import com.example.professor.fourpizza.http.deserialize.ReviewsDeserialize;
import com.example.professor.fourpizza.http.deserialize.TimeWorkDeserialize;
import com.example.professor.fourpizza.models.PizzaRestraunt;
import com.example.professor.fourpizza.models.RestrauntPictures;
import com.example.professor.fourpizza.models.UsersReviews;
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
    private List<RestrauntPictures> pictures = new ArrayList<>();
    private List<UsersReviews> reviews = new ArrayList<>();
    private RestrauntPictures restrauntPictures;
    private String likes;
    private String workTime;
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
                Gson gson = new GsonBuilder().registerTypeAdapter(PizzaRestraunt.class,
                        new RestrauntDeserialize()).create();
                Log.d(TAG, "onResponse: " + call.request().toString());
                restraunts = gson.fromJson(ChangeJson.changeRestrauntJson(response.body())
                        , new TypeToken<ArrayList<PizzaRestraunt>>() {
                        }.getType());
                Collections.sort(restraunts, new Comparator<PizzaRestraunt>() {
                    @Override
                    public int compare(PizzaRestraunt pizzaRestraunt, PizzaRestraunt t1) {
                        if (pizzaRestraunt.getDistance() < t1.getDistance()) return -1;
                        if (pizzaRestraunt.getDistance() == t1.getDistance()) return 0;
                        if (pizzaRestraunt.getDistance() > t1.getDistance()) return 1;
                        return 0;
                    }
                });
                for (PizzaRestraunt res : restraunts) {
                    requestPhotos(res.getId(), callBack);
                }
                          }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                callBack.onError(t.getMessage());
            }
        });
    }

    private void requestPhotos(String objectId, final ApiCallBack callBack) {
        Call<JsonElement> result = request.getPhotos(changeIdString(objectId), ProjectConstans.CLIENT_ID
                , ProjectConstans.CLIENT_SECRET, getDate());
        Log.d(TAG, "requestPhotos: " + changeIdString(objectId) + " " + getDate());

        result.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Gson gson = new GsonBuilder().registerTypeAdapter(RestrauntPictures.class,
                        new RestrauntListPicturesDeserialize()).create();
               try {
                    restrauntPictures = gson.fromJson(response.body(), RestrauntPictures.class);
                } catch (NullPointerException e) {
                    Log.d(TAG, "onResponse: null" + e.getMessage());
                }
                pictures.add(restrauntPictures);
                if (pictures.size() == restraunts.size()) {
                    callBack.onSucsess(restraunts, pictures);
                    Log.d(TAG, "onResponse: " + pictures.size());
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void requestLikes(final String objectId, final LikesCallBack callBack) {
        Call<JsonElement> result = request.getLikes(changeIdString(objectId), ProjectConstans.CLIENT_ID
                , ProjectConstans.CLIENT_SECRET, getDate());
        result.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Gson gson = new GsonBuilder().registerTypeAdapter(String.class, new LikesDeserialize()).create();
                try {
                    requestWorkTime(objectId, callBack);
                    likes = gson.fromJson(response.body(), String.class);
                    Log.d(TAG, "onResponse: " + likes);

                } catch (NullPointerException e) {
                    Log.d(TAG, "onResponse: null" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

        Log.d(TAG, "requestPhotos: " + changeIdString(objectId) + " " + getDate());
    }

    private void requestWorkTime(String objectId, final LikesCallBack callBack) {
        Call<JsonElement> result = request.getWorkTime(changeIdString(objectId), ProjectConstans.CLIENT_ID
                , ProjectConstans.CLIENT_SECRET, getDate());
        result.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Gson gson = new GsonBuilder().registerTypeAdapter(String.class, new TimeWorkDeserialize()).create();
                try {
                    workTime = gson.fromJson(response.body(), String.class);
                    callBack.onSucsess(likes, workTime);
                    Log.d(TAG, "onResponse: " + workTime);

                } catch (NullPointerException e) {
                    Log.d(TAG, "onResponse: null" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    public void requestPhotoForDetails(String objectId, final PhotosCallBack callBack) {
        Call<JsonElement> result = request.getPhotos(changeIdString(objectId), ProjectConstans.CLIENT_ID
                , ProjectConstans.CLIENT_SECRET, getDate());
        result.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d(TAG, "onResponse details: " + ChangeJson.changePictureJson(response.body()) + "  " + response.message() + "  " + call.request().toString());
                //  Gson gson = new GsonBuilder().registerTypeAdapter(RestrauntPictures.class, new RestrauntPicturesDeserialize()).create();
                Gson gson = new GsonBuilder().create();
                try {
                    pictures = gson.fromJson(ChangeJson.changePictureJson(response.body())
                            , new TypeToken<ArrayList<RestrauntPictures>>() {
                            }.getType());
                    //Log.d(TAG, "onResponse details: " + pictures.get(1).getPicturePrefix());
                    callBack.onSucsess(pictures);
                } catch (NullPointerException e) {
                    Log.d(TAG, "onResponse: null" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }
     public void requestReviews(String objectId, final ReviewsCallBack callBack){
         Call<JsonElement> result = request.getReviews(changeIdString(objectId), ProjectConstans.CLIENT_ID
                 , ProjectConstans.CLIENT_SECRET, getDate());
         result.enqueue(new Callback<JsonElement>() {
             @Override
             public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                 Gson gson = new GsonBuilder().registerTypeAdapter(UsersReviews.class,
                         new ReviewsDeserialize()).create();
                 Log.d(TAG, "onResponse details: " + ChangeJson.changeReviewsJson(response.body()) + "  " + response.message() + "  " + call.request().toString());
                 try {
                     reviews = gson.fromJson(ChangeJson.changeReviewsJson(response.body())
                             , new TypeToken<ArrayList<UsersReviews>>() {
                             }.getType());
                     Log.d(TAG, "onResponse details: " + reviews);
                     callBack.onSucsess(reviews);
                     for (UsersReviews u:reviews) {
                         Log.d(TAG, "onResponse details: " + u.getUserFirstName());
                     }

                   //  callBack.onSucsess(pictures);
                 } catch (Exception e) {
                     Log.d(TAG, "onResponse: null" + e.getMessage());
                 }
             }

             @Override
             public void onFailure(Call<JsonElement> call, Throwable t) {

             }
         });
     }
    private String getLocation() {
        List<String> loc = new ParserXml().parseGeolocation();
        StringBuilder builder = new StringBuilder();
        builder.append(loc.get(0))
                .append(",")
                .append(loc.get(1));
        Log.d(TAG, "setLocation: " + builder.toString());
        return builder.toString();
    }

    private String getDate() {
        List<String> time = new ParserXml().parseGeolocation();
        return time.get(2);
    }

    private String changeIdString(String objectId) {
        return objectId.substring(1, objectId.length() - 1);
    }

}
