package com.example.professor.fourpizza.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.professor.fourpizza.R;
import com.example.professor.fourpizza.models.PizzaRestraunt;
import com.example.professor.fourpizza.models.RestrauntPictures;
import com.example.professor.fourpizza.util.PizzaRestrauntOnItemClickListener;
import com.example.professor.fourpizza.util.RecyclerOnItemClickListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> implements
        RecyclerOnItemClickListener {
    private static final String TAG = ImagesAdapter.class.getSimpleName();
    private View view;
    private ViewHolder viewHolder;
    private Context context;
    private List<RestrauntPictures> pictures;
    private RecyclerOnItemClickListener listener;

    public ImagesAdapter(List<RestrauntPictures> pictures) {
        this.pictures = pictures;
        Log.d(TAG, "ImagesAdapter: "+pictures.size());
    }

    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.images_restraunt_container
                , parent, false);
        viewHolder = new ViewHolder(view);
        context = parent.getContext();
        Log.d(TAG, "onCreateViewHolder: wwww");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (pictures != null) {
            Log.d(TAG, "onBindViewHolder: "+pictures.size());
            Picasso.with(context).load(pictureRequest(pictures.get(position)))
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
        if(pictures != null){
            return pictures.size();
        }
        return 0;
    }

    private String pictureRequest(RestrauntPictures picture) {
        if (picture != null) {
            StringBuilder builder = new StringBuilder();
            builder.append(picture.getPicturePrefix())
                    .append(250)
                    .append("x")
                    .append(250)
                    .append(picture.getPictureSuffix());
            Log.d(TAG, "pictureRequest: " + builder.toString());
            return builder.toString();
        }
        return null;
    }

    @Override
    public void onItemClick(int position, View v) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public ImageView restrauntPicture;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            restrauntPicture = (ImageView) itemView.findViewById(R.id.image_container);
        }
    }
    public void setOnItemClickListener(RecyclerOnItemClickListener clickListener) {
        this.listener = clickListener;
    }
}
