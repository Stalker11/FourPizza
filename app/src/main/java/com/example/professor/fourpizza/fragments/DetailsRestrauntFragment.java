package com.example.professor.fourpizza.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.professor.fourpizza.R;
import com.example.professor.fourpizza.adapters.ImagesAdapter;
import com.example.professor.fourpizza.adapters.ReviewsAdapter;
import com.example.professor.fourpizza.callback.LikesCallBack;
import com.example.professor.fourpizza.callback.PhotosCallBack;
import com.example.professor.fourpizza.callback.ReviewsCallBack;
import com.example.professor.fourpizza.http.RequestPizza;
import com.example.professor.fourpizza.models.PizzaRestraunt;
import com.example.professor.fourpizza.models.RestrauntPictures;
import com.example.professor.fourpizza.models.UsersReviews;
import com.example.professor.fourpizza.util.ProjectConstans;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailsRestrauntFragment extends Fragment {
    private View view;
    private TabHost tabHost;
    private TabHost.TabSpec tabSpec;
    private Context context;
    private PizzaRestraunt restraunt;
    private RestrauntPictures picture;
    private String details;
    private String photos;
    private String reviews;
    private String likes;
    private RecyclerView recycler;
    private ImagesAdapter imagesAdapter;
    private ReviewsAdapter reviewsAdapter;

    private static final String TAG = DetailsRestrauntFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_restraunt_fragment, null);
        context = getActivity().getBaseContext();
        restraunt = getArguments().getParcelable(ProjectConstans.PIZZA_RESTRAUNT_KEY);
        picture = getArguments().getParcelable(ProjectConstans.RESTRAUNT_PICTURE_KEY);

        new RequestPizza().requestLikes(restraunt.getId(), new LikesCallBacks());
        tabHost = (TabHost) view.findViewById(R.id.tabHost);
        tabHost.setup();

        details = context.getResources().getString(R.string.details);
        photos = context.getResources().getString(R.string.photos);
        reviews = context.getResources().getString(R.string.reviews);

        tabSpec = tabHost.newTabSpec(details);
        tabSpec.setContent(R.id.lin1);
        tabSpec.setIndicator(details);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(reviews);
        tabSpec.setContent(R.id.lin2);
        tabSpec.setIndicator(reviews);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(photos);
        tabSpec.setContent(R.id.lin3);
        tabSpec.setIndicator(photos);
        tabHost.addTab(tabSpec);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if(tabHost.getCurrentTabTag().equals(details)){
                    Log.d(TAG, "onTabChanged: " + tabHost.getCurrentTabTag());
                    setDetails(restraunt);
                }
                else if(tabHost.getCurrentTabTag().equals(reviews)){
                    if(reviewsAdapter == null) {
                        Log.d(TAG, "onTabChanged: " + tabHost.getCurrentTabTag());
                        new RequestPizza().requestReviews(restraunt.getId(), new ReviewCallBack());
                    }
                }
                else if(tabHost.getCurrentTabTag().equals(photos)){
                    if(imagesAdapter == null) {
                        new RequestPizza().requestPhotoForDetails(restraunt.getId(), new PhotoCallBack());
                        Log.d(TAG, "onTabChanged: " + tabHost.getCurrentTabTag());
                    }

                }

            }
        });
        tabHost.setCurrentTab(0);
        setDetails(restraunt);
        Log.d(TAG, "onCreateView: "+tabHost.getCurrentTab());
        return view;
    }
    private void setPhotos(List<RestrauntPictures> pictures){
        imagesAdapter = new ImagesAdapter(pictures);
        Log.d(TAG, "setPhotos: "+220);
        recycler = (RecyclerView)view.findViewById(R.id.photos_recycler);
        recycler.setAdapter(imagesAdapter);
        Log.d(TAG, "setPhotos: "+recycler+" "+ imagesAdapter);
        recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }
    private void setDetails(PizzaRestraunt restraunt){
        TextView name = (TextView)view.findViewById(R.id.details_restraunt_name);
        TextView distance = (TextView)view.findViewById(R.id.detail_distance);
        TextView adress = (TextView)view.findViewById(R.id.detail_adress);
        ImageView image = (ImageView)view.findViewById(R.id.restraunt_picture_detail);

        Picasso.with(context).load(pictureRequest(picture)).resize(700,300).error(R.drawable.movies_search_frag).into(image
                , new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });

        name.setText(restraunt.getRestrauntName());
        distance.setText(String.valueOf(restraunt.getDistance()));
        adress.setText(restraunt.getAdress());
    }
    private void setLikes(String likes, String worktime){
        TextView hours = (TextView)view.findViewById(R.id.detail_open_close_time);
        TextView like = (TextView)view.findViewById(R.id.detail_likes);

        hours.setText(worktime);
        like.setText(likes);
    }
    private void setReviews(List<UsersReviews> reviews){
        reviewsAdapter = new ReviewsAdapter(reviews);
        Log.d(TAG, "setPhotos: "+220);
        recycler = (RecyclerView)view.findViewById(R.id.review_recycler);
        recycler.setAdapter(reviewsAdapter);
        Log.d(TAG, "setPhotos: "+recycler+" "+reviewsAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(context));
    }
    private String pictureRequest(RestrauntPictures picture) {
        if (picture != null) {
            StringBuilder builder = new StringBuilder();
            builder.append(picture.getPicturePrefix())
                    .append(picture.getWidth())
                    .append("x")
                    .append(picture.getWidth())
                    .append(picture.getPictureSuffix());
            Log.d(TAG, "pictureRequest: " + builder.toString());
            return builder.toString();
        }
        return null;
    }
    private class LikesCallBacks implements LikesCallBack {

        @Override
        public void onSucsess(String likes, String workTime) {
            setLikes(likes,workTime);
        }

        @Override
        public void onError(String error) {

        }
    }
    private class PhotoCallBack implements PhotosCallBack{
        @Override
        public void onSucsess(List<RestrauntPictures> pictures) {
        setPhotos(pictures);
        }
    }
    private class ReviewCallBack implements ReviewsCallBack{
        @Override
        public void onSucsess(List<UsersReviews> reviews) {
            setReviews(reviews);
        }
    }
}
