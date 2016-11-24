package com.example.professor.fourpizza.http;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Request {
    @POST("/v2/venues/search?")
    Call<JsonElement> getPlaces(@Query(value = "client_id", encoded = true) String clientId,
                                @Query(value = "client_secret", encoded = true) String clientSecret,
                                @Query(value = "ll", encoded = true) String longLat,
                                @Query(value = "query", encoded = true) String query,
                                @Query(value = "radius", encoded = true) int radius,
                                @Query(value = "v", encoded = true) String date);

    @GET("/v2/venues/{id}/photos?")
    Call<JsonElement> getPhotos(@Path("id") String placeId,
                                @Query(value = "client_id", encoded = true) String clientId,
                                @Query(value = "client_secret", encoded = true) String clientSecret,
                                @Query(value = "v", encoded = true) String date);

    @GET("/v2/venues/{id}/likes?")
    Call<JsonElement> getLikes(@Path("id") String placeId,
                               @Query(value = "client_id", encoded = true) String clientId,
                               @Query(value = "client_secret", encoded = true) String clientSecret,
                               @Query(value = "v", encoded = true) String date);

    @GET("/v2/venues/{id}/hours?")
    Call<JsonElement> getWorkTime(@Path("id") String placeId,
                               @Query(value = "client_id", encoded = true) String clientId,
                               @Query(value = "client_secret", encoded = true) String clientSecret,
                               @Query(value = "v", encoded = true) String date);

}
