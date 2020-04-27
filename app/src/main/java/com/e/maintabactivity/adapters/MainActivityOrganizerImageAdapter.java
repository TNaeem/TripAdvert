package com.e.tripadvet.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.maintabactivity.R;

public class MainActivityOrganizerImageAdapter extends RecyclerView.Adapter<MainActivityOrganizerImageAdapter.HomeTabOrganizerImageAdapterViewHolder> {

    private int[] organizerImages;
    public MainActivityOrganizerImageAdapter(int [] organizerImages){
        this.organizerImages = organizerImages;
    }
    @NonNull
    @Override

    public HomeTabOrganizerImageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_organizer_image, parent, false);
        return new HomeTabOrganizerImageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeTabOrganizerImageAdapterViewHolder holder, int position) {
        int img = organizerImages[position];
        //holder.organizerImage.setImageAlpha(img);
    }

    @Override
    public int getItemCount() {
        return organizerImages.length;
    }

    public class HomeTabOrganizerImageAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView organizerImage;
           public HomeTabOrganizerImageAdapterViewHolder(View itemView){

            super(itemView);
            organizerImage = itemView.findViewById(R.id.organizer_image);
        }
    }

}

