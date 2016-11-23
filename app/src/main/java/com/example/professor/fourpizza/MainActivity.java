package com.example.professor.fourpizza;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.professor.fourpizza.callback.ApiCallBack;
import com.example.professor.fourpizza.callback.FragmentCreatedCallBack;
import com.example.professor.fourpizza.callback.RequestCallBack;
import com.example.professor.fourpizza.fragments.FragmentGenerator;
import com.example.professor.fourpizza.http.RequestPizza;
import com.example.professor.fourpizza.util.ProjectConstans;

public class MainActivity extends AppCompatActivity implements FragmentCreatedCallBack {
private final String TAG = MainActivity.class.getSimpleName();
    private FragmentTransaction trans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createFragment(FragmentGenerator.createRestrauntListFragment());
    }
    public void createFragment(Fragment fr){
        trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.fragment_container, fr, ProjectConstans.REPLACE_FRAGMENT);
        trans.addToBackStack(fr.getClass().getSimpleName());
        trans.commit();
    }

    @Override
    public void onCreateFragment(ApiCallBack callBack) {
        new RequestPizza().requestPizza(callBack);
        Log.d(TAG, "onCreate: "+callBack);
    }

}
