package com.example.professor.fourpizza.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.professor.fourpizza.MainActivity;
import com.example.professor.fourpizza.R;
import com.example.professor.fourpizza.adapters.PizzaRestraunrsAdapter;
import com.example.professor.fourpizza.callback.FragmentCreatedCallBack;
import com.example.professor.fourpizza.http.RequestPizza;
import com.example.professor.fourpizza.models.PizzaRestraunt;
import com.example.professor.fourpizza.callback.ApiCallBack;
import com.example.professor.fourpizza.models.RestrauntPictures;
import com.example.professor.fourpizza.util.ItemDecorationView;
import com.example.professor.fourpizza.util.ProjectConstans;
import com.example.professor.fourpizza.util.PizzaRestrauntOnItemClickListener;

import java.util.List;

public class RestrauntsListFragment extends Fragment {
    private View view;
    private static final String TAG = RestrauntsListFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restraunt_fragment, null);
        context = getActivity().getApplicationContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.restraunts_recycler);
        new RequestPizza().requestPizza(new RequestCallBack());
        Log.d(TAG, "onCreateView: ");
        return view;
    }
      private void setRestrauntsList(List<PizzaRestraunt> restrauntsList, List<RestrauntPictures> pictures, String err){
        if (restrauntsList != null) {
            Log.d(TAG, "setRestrauntsList: ");
          //  CustomProgressDialog.showProgressDialog();
            PizzaRestraunrsAdapter adapter = new PizzaRestraunrsAdapter(null,restrauntsList,pictures);
            recyclerView.addItemDecoration(new ItemDecorationView());
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new PizzaRestrauntOnItemClickListener() {
                @Override
                public void onItemClick(int position, View v, PizzaRestraunt restraunt, RestrauntPictures picture) {
                    Log.d(TAG, "setText: " + position);
                   /* MainActivity act = (MainActivity) getActivity();
                    CustomProgressDialog.showProgressDialog();
                    act.createFragment(FragmentsGenerator.detailsFragment(films.get(position)));*/
                    Log.d(TAG, "onItemClick: "+position);
                    ((MainActivity)getActivity()).createFragment(FragmentGenerator.createDetailsReastrauntFragment(restraunt,picture));
                }
            });
        } else {
            String error = getArguments().getString(ProjectConstans.NOT_FOUND);
            Log.d(TAG, "onCreateView: " + error);

            PizzaRestraunrsAdapter adapter = new PizzaRestraunrsAdapter(error, null,null);
            recyclerView.setAdapter(adapter);
        }
    }
    private class RequestCallBack implements ApiCallBack{

        @Override
        public void onSucsess(List<PizzaRestraunt> restraunts, List<RestrauntPictures> pictures) {
            setRestrauntsList(restraunts,pictures,null);
            Log.d(TAG, "onSucsess: ");
        }

        @Override
        public void onError(String errorMessage) {
           setRestrauntsList(null,null,errorMessage);
            Log.d(TAG, "onError: ");
        }
    }
}
