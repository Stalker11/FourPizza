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
import com.example.professor.fourpizza.models.PizzaRestraunt;
import com.example.professor.fourpizza.models.RestrauntPictures;
import com.example.professor.fourpizza.util.PizzaRestrauntOnItemClickListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PizzaRestraunrsAdapter extends RecyclerView.Adapter<PizzaRestraunrsAdapter.ViewHolder> implements
        PizzaRestrauntOnItemClickListener {
    public static final String TAG = PizzaRestraunrsAdapter.class.getSimpleName();
    private PizzaRestrauntOnItemClickListener clickListener;
    private List<PizzaRestraunt> restraunts;
    private List<RestrauntPictures> pictures;
    private String errors;
    private ViewHolder viewHolder;
    private Context context;
    private View view;

    public PizzaRestraunrsAdapter(String error, List<PizzaRestraunt> restraunts, List<RestrauntPictures> pictures) {
        this.errors = error;
        this.restraunts = restraunts;
        this.pictures = pictures;
        Log.d(TAG, "PizzaRestraunrsAdapter: " + restraunts.size() + " " + pictures.size() + " " + error);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restraunts_list, parent, false);
        viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (restraunts != null) {
            holder.restrauntName.setText(restraunts.get(position).getRestrauntName());
            holder.distance.setText(String.valueOf(restraunts.get(position).getDistance()));
            //  holder.restrauntAdress.setText(restraunts.get(position).getAdress());
            Log.d(TAG, "onBindViewHolder: " + restraunts.size() + " " + pictures.size());
            if (pictures != null) {
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

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(holder.getAdapterPosition(), view
                            , restraunts.get(holder.getAdapterPosition()), pictures.get(holder.getAdapterPosition()));
                }
            });
        }
        if (errors != null) {
            holder.restrauntAdress.setText(errors);
        }
        if (errors == null && restraunts == null) {
            // Log.d(TAG, "onBindViewHolder: " + 55555555);
            // holder.restrauntAdress.setText(context.getResources().getString(R.string.nothing_saved_films));
        }
    }

    @Override
    public int getItemCount() {
        if (restraunts != null) {
            return restraunts.size();
        }
        if (errors != null) {
            return 1;
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView restrauntName;
        public TextView distance;
        public TextView restrauntAdress;
        public View itemView;
        public ImageView restrauntPicture;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            restrauntName = (TextView) itemView.findViewById(R.id.restraunt_name);
            distance = (TextView) itemView.findViewById(R.id.distance);
            // restrauntAdress = (TextView) itemView.findViewById(R.id.restraunt_adress);
            restrauntPicture = (ImageView) itemView.findViewById(R.id.restraunt_picture);
        }
    }

    public void setOnItemClickListener(PizzaRestrauntOnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onItemClick(int position, View v, PizzaRestraunt restraunt, RestrauntPictures picture) {

    }

    private String pictureRequest(RestrauntPictures picture) {
        if (picture != null) {
            StringBuilder builder = new StringBuilder();
            builder.append(picture.getPicturePrefix())
                    .append(75)
                    .append("x")
                    .append(75)
                    .append(picture.getPictureSuffix());
            Log.d(TAG, "pictureRequest: " + builder.toString());
            return builder.toString();
        }
        return null;
    }
}
