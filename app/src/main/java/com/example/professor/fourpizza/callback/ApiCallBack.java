package com.example.professor.fourpizza.callback;

import com.example.professor.fourpizza.models.PizzaRestraunt;
import com.example.professor.fourpizza.models.RestrauntListPicture;

import java.util.List;

public interface ApiCallBack {
    void onSucsess(List<PizzaRestraunt> restraunts, List<RestrauntListPicture> pictures);
    void onError(String errorMessage);
}
