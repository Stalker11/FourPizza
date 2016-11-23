package com.example.professor.fourpizza;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.professor.fourpizza.callback.ApiCallBack;
import com.example.professor.fourpizza.callback.FragmentCreatedCallBack;
import com.example.professor.fourpizza.fragments.FragmentGenerator;
import com.example.professor.fourpizza.http.RequestPizza;
import com.example.professor.fourpizza.util.ProjectConstans;

public class MainActivity extends AppCompatActivity {
private final String TAG = MainActivity.class.getSimpleName();
    private FragmentTransaction trans;
    private FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        createFragment(FragmentGenerator.createRestrauntListFragment());
    }
    public void createFragment(Fragment fr){
        trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.fragment_container, fr, ProjectConstans.REPLACE_FRAGMENT);
        trans.addToBackStack(fr.getClass().getSimpleName());
        trans.commit();
    }


    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: "+fm.getBackStackEntryCount());
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
            Log.d(TAG, "onBackPressed: " + fm.findFragmentById(R.id.fragment_container)+"  "+fm.getBackStackEntryCount());
        }
        super.onBackPressed();
    }
}
