package com.e.tripadvet.Organizer.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.maintabactivity.R;
import com.e.maintabactivity.Slider;

public class OrganizerProfileGalleryAdapter extends RecyclerView.Adapter<OrganizerProfileGalleryAdapter.OrganizerProfileGalleryAdapterViewHolder> {
    private int[] trips;
    private Context context;
    public OrganizerProfileGalleryAdapter(Context context, int [] trips){
        this.context = context;
        this.trips = trips;
    }
    @NonNull
    @Override

    public OrganizerProfileGalleryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_image_item, parent, false);
        return new OrganizerProfileGalleryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizerProfileGalleryAdapter.OrganizerProfileGalleryAdapterViewHolder holder, final int position) {

        holder.tripImage.setImageResource(trips[position]);
        holder.tripImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Slider.class);
                intent.putExtra("POSITION", position);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return trips.length;
    }

    public class OrganizerProfileGalleryAdapterViewHolder extends RecyclerView.ViewHolder{

        ImageView tripImage;

        public OrganizerProfileGalleryAdapterViewHolder(View itemView){
            super(itemView);
            tripImage = itemView.findViewById(R.id.grid_image_item);

        }
    }
}

