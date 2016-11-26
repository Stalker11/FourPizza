package com.example.professor.fourpizza.fragments;

import android.os.Bundle;

import com.example.professor.fourpizza.models.PizzaRestraunt;
import com.example.professor.fourpizza.models.RestrauntPictures;
import com.example.professor.fourpizza.util.ProjectConstans;

public class FragmentGenerator {
    public static RestrauntsFragment createRestrauntListFragment() {
        RestrauntsFragment fragment = new RestrauntsFragment();
        return fragment;
    }

    public static DetailsRestrauntFragment createDetailsReastrauntFragment(PizzaRestraunt restraunt
            , RestrauntPictures picture) {
        DetailsRestrauntFragment fragment = new DetailsRestrauntFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ProjectConstans.PIZZA_RESTRAUNT_KEY, restraunt);
        bundle.putParcelable(ProjectConstans.RESTRAUNT_PICTURE_KEY, picture);
        fragment.setArguments(bundle);
        return fragment;
    }
}
