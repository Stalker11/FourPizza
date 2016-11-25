package com.example.professor.fourpizza.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.professor.fourpizza.R;
import com.example.professor.fourpizza.models.RestrauntPictures;
import com.example.professor.fourpizza.models.UsersReviews;
import com.example.professor.fourpizza.util.RecyclerOnItemClickListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> implements
        RecyclerOnItemClickListener {
    private static final String TAG = ReviewsAdapter.class.getSimpleName();
    private View view;
    private ViewHolder viewHolder;
    private Context context;
    private List<UsersReviews> reviews;
    private RecyclerOnItemClickListener listener;

    public ReviewsAdapter(List<UsersReviews> reviews) {
        this.reviews = reviews;
    }

    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_layout, parent,false);
        viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ViewHolder holder, int position) {
        if(reviews != null){
            holder.userName.setText(userName(reviews.get(position).getUserFirstName()
                    ,reviews.get(position).getUserLastName()));
            holder.userReview.setText(reviews.get(position).getTextReview());
            Log.d(TAG, "onBindViewHolder: "+reviews.size());
            Picasso.with(context).load(pictureRequest(reviews.get(position)))
                    .error(R.drawable.movies_search_frag)
                    .into(holder.restrauntPicture, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "onSuccess: load");
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        if(reviews != null) return reviews.size();
        return 0;
    }

    @Override
    public void onItemClick(int position, View v) {

    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView userName;
        public TextView userReview;
        public ImageView restrauntPicture;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            restrauntPicture = (ImageView) itemView.findViewById(R.id.user_photo);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            userReview = (TextView) itemView.findViewById(R.id.user_review);
        }
    }
    public void setOnItemClickListener(RecyclerOnItemClickListener clickListener) {
        this.listener = clickListener;
    }
    private String pictureRequest(UsersReviews reviews) {
        if (reviews != null) {
            StringBuilder builder = new StringBuilder();
            builder.append(reviews.getUserPhotoPrefix())
                    .append(75)
                    .append("x")
                    .append(75)
                    .append(reviews.getUserPhotoSuffix());
            Log.d(TAG, "pictureRequest: " + builder.toString());
            return builder.toString();
        }
        return null;
    }
    private String userName(String firstName, String lastName){
            StringBuilder builder = new StringBuilder();
            builder.append(firstName)
                    .append(" ")
                    .append(lastName);
            Log.d(TAG, "pictureRequest: " + builder.toString());
            return builder.toString();
        }
    }

