package com.example.professor.fourpizza.callback;
import com.example.professor.fourpizza.models.PizzaRestraunt;
import com.example.professor.fourpizza.models.RestrauntListPicture;

import java.util.List;

public class RequestCallBack  implements ApiCallBack{

    @Override
    public void onSucsess(List<PizzaRestraunt> restraunts, List<RestrauntListPicture> pictures) {

    }

    @Override
    public void onError(String errorMessage) {

    }
}
