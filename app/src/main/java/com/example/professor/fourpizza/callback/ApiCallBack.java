package com.example.professor.fourpizza.callback;

import com.example.professor.fourpizza.models.PizzaRestraunt;
import com.example.professor.fourpizza.models.RestrauntPictures;

import java.util.List;

public interface ApiCallBack {
    void onSucsess(List<PizzaRestraunt> restraunts, List<RestrauntPictures> pictures);
    void onError(String errorMessage);
}
