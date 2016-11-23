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
import com.example.professor.fourpizza.callback.RequestCallBack;
import com.example.professor.fourpizza.models.PizzaRestraunt;
import com.example.professor.fourpizza.callback.ApiCallBack;
import com.example.professor.fourpizza.models.RestrauntListPicture;
import com.example.professor.fourpizza.util.ItemDecorationView;
import com.example.professor.fourpizza.util.ProjectConstans;
import com.example.professor.fourpizza.util.RecycleViewOnItemClickListener;

import java.util.List;

public class RestrauntsListFragment extends Fragment {
    private View view;
    private static final String TAG = RestrauntsListFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restraunt_fragment, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.restraunts_recycler);
        Log.d(TAG, "onCreateView: ");
        return view;
    }

    @Override
    public void onAttach(Context context) {
        ((FragmentCreatedCallBack)getActivity()).onCreateFragment(new RequestCallBack());
        Log.d(TAG, "onAttachFragment: "+(new RequestCallBack()));
        super.onAttach(context);
    }

    private void setRestrauntsList(List<PizzaRestraunt> restrauntsList,List<RestrauntListPicture> pictures, String err){
        if (restrauntsList != null) {
          //  CustomProgressDialog.showProgressDialog();
            PizzaRestraunrsAdapter adapter = new PizzaRestraunrsAdapter(null,restrauntsList,pictures);
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new ItemDecorationView());
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
            adapter.setOnItemClickListener(new RecycleViewOnItemClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Log.d(TAG, "setText: " + position);
                   /* MainActivity act = (MainActivity) getActivity();
                    CustomProgressDialog.showProgressDialog();
                    act.createFragment(FragmentsGenerator.detailsFragment(films.get(position)));*/
                    Log.d(TAG, "onItemClick: "+position);
                    ((MainActivity)getActivity()).createFragment(FragmentGenerator.createDetailsReastrauntFragment());
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
        public void onSucsess(List<PizzaRestraunt> restraunts, List<RestrauntListPicture> pictures) {
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
