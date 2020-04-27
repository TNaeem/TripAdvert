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
import com.e.maintabactivity.TripDetailsActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

public class ProfileTripsAdapter extends RecyclerView.Adapter<ProfileTripsAdapter.ProfileTripsAdapterViewHolder> {
    private String[] trips;
    private Context context;
    public ProfileTripsAdapter(Context context, String [] trips){
        this.context = context;
        this.trips = trips;
    }
    @NonNull
    @Override

    public ProfileTripsAdapter.ProfileTripsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_profile_trip_item, parent, false);
        return new ProfileTripsAdapter.ProfileTripsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileTripsAdapter.ProfileTripsAdapterViewHolder holder, int position) {
        String title = trips[position];
        holder.tripTitle.setText(title);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(v.getContext(), TripDetailsActivity.class));
            }
        });



    }

    @Override
    public int getItemCount() {
        return trips.length;
    }

    public class ProfileTripsAdapterViewHolder extends RecyclerView.ViewHolder{
        MaterialTextView tripTitle;
        MaterialTextView destination;
        ImageView tripImage;
        MaterialTextView description;
        MaterialCardView cardView;
        public ProfileTripsAdapterViewHolder(View itemView){

            super(itemView);
            cardView = itemView.findViewById(R.id.layout_profile_trip_item_card_view);
            tripTitle = itemView.findViewById(R.id.layout_profile_trip_title);
            destination = itemView.findViewById(R.id.layout_profile_trip_destination);
            description = itemView.findViewById(R.id.layout_profile_trip_description);
            tripImage = itemView.findViewById(R.id.layout_profile_trip_item_trip_image);
            cardView = itemView.findViewById(R.id.layout_profile_trip_item_card_view);
        }
    }
}
