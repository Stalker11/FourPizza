package com.example.professor.fourpizza.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.example.professor.fourpizza.R;

public class DetailsRestrauntFragment extends Fragment {
    private View view;
    private TabHost tabHost;
    private TabHost.TabSpec tabSpec;
    private static final String TAG = DetailsRestrauntFragment.class.getSimpleName();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_restraunt_fragment,null);
        tabHost = (TabHost)view.findViewById(R.id.tabHost);
        tabHost.setup();

        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.lin1);
        tabSpec.setIndicator("Кот");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.lin2);
        tabSpec.setIndicator("Кошка");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.lin3);
        tabSpec.setIndicator("Котёнок");
        tabHost.addTab(tabSpec);
      tabHost.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Log.d(TAG, "onClick: "+tabSpec.getTag());
          }
      });
        tabHost.setCurrentTab(0);
        return view;
    }
}
