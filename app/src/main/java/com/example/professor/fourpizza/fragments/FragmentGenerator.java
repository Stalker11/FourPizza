package com.example.professor.fourpizza.fragments;


import android.support.v4.app.Fragment;

public class FragmentGenerator {
    public static RestrauntsListFragment createRestrauntListFragment(){
        RestrauntsListFragment fragment = new RestrauntsListFragment();
        return fragment;
    }
    public static DetailsRestrauntFragment createDetailsReastrauntFragment(){
        DetailsRestrauntFragment fragment = new DetailsRestrauntFragment();
        return fragment;
    }
}
