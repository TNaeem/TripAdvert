package com.e.maintabactivity.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.maintabactivity.R;
import com.e.maintabactivity.models.ImageModel;
import com.e.maintabactivity.organizer.ImageSliderActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class TripDetailImagesAdapter extends RecyclerView.Adapter<TripDetailImagesAdapter.TripDetailImagesAdapterViewHolder> {
    private List<ImageModel> imageModelList;
    private Context context;
    List<String> images;
    public TripDetailImagesAdapter(Context context, List<ImageModel> imageModelList, List<String> images){
        this.context = context;
        this.imageModelList = imageModelList;
        this.images = images;
    }
    @NonNull
    @Override

    public TripDetailImagesAdapter.TripDetailImagesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_image, parent, false);
        return new TripDetailImagesAdapter.TripDetailImagesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripDetailImagesAdapterViewHolder holder, final int position) {
        final ImageModel imageModel = imageModelList.get(position);
        Picasso.get().load(imageModel.getImage()).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageSliderActivity.class);
                intent.putStringArrayListExtra("images",  (ArrayList<String>) images);
                //intent.putIntegerArrayListExtra("tripDetailImages",  (ArrayList<Integer>) images);
                intent.putExtra("current", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageModelList.size();
    }

    public class TripDetailImagesAdapterViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        public TripDetailImagesAdapterViewHolder(View itemView){

            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
