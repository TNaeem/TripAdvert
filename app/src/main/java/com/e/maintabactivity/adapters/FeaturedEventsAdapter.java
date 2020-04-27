package com.e.maintabactivity.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.maintabactivity.TripDetailsActivity;
import com.e.maintabactivity.organizer.OrganizerProfileActivity;
import com.e.maintabactivity.R;

public class FeaturedEventsAdapter extends RecyclerView.Adapter<FeaturedEventsAdapter.FeaturedEventsAdapterViewHolder> {

    private String[] trips;
    Context context;
    public FeaturedEventsAdapter(Context context, String [] trips){
        this.context = context;
        this.trips = trips;
    }
    @NonNull
    @Override

    public FeaturedEventsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_single_trip, parent, false);
        return new FeaturedEventsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedEventsAdapterViewHolder holder, int position) {
        String title = trips[position];
        holder.tripTitle.setText(title);
        holder.tripImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(v.getContext(), TripDetailsActivity.class));
            }
        });
        holder.organizerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(v.getContext(), OrganizerProfileActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return trips.length;
    }

    public class FeaturedEventsAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView tripTitle;
        TextView tripPrice;
        ImageView tripImage;
        ImageView organizerImage;
        TextView discount;
        public FeaturedEventsAdapterViewHolder(View itemView){

            super(itemView);
            tripTitle = itemView.findViewById(R.id.trip_title);
            tripImage = itemView.findViewById(R.id.trip_image);
            tripPrice = itemView.findViewById(R.id.trip_price);
            organizerImage = itemView.findViewById(R.id.trip_organizer);
            discount = itemView.findViewById(R.id.trip_discount);

        }
    }

}

