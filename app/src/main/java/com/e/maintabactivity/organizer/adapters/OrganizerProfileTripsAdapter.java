package com.e.maintabactivity.organizer.adapters;


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

public class OrganizerProfileTripsAdapter extends RecyclerView.Adapter<OrganizerProfileTripsAdapter.OrganizerProfileTripsAdapterViewHolder> {
    private String[] organizers;
    private Context context;
    public OrganizerProfileTripsAdapter(Context context, String [] organizers){
        this.context = context;
        this.organizers = organizers;
    }
    @NonNull
    @Override

    public OrganizerProfileTripsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_profile_trip_item, parent, false);
        return new OrganizerProfileTripsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizerProfileTripsAdapter.OrganizerProfileTripsAdapterViewHolder holder, int position) {
        String title = organizers[position];
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
        return organizers.length;
    }

    public class OrganizerProfileTripsAdapterViewHolder extends RecyclerView.ViewHolder{
        MaterialTextView tripTitle;
        MaterialTextView destination;
        ImageView tripImage;
        MaterialTextView description;
        MaterialCardView cardView;
        public OrganizerProfileTripsAdapterViewHolder(View itemView){

            super(itemView);
            cardView = itemView.findViewById(R.id.layout_profile_trip_item_card_view);
            tripTitle = (MaterialTextView)itemView.findViewById(R.id.layout_profile_trip_title);
            destination = itemView.findViewById(R.id.layout_profile_trip_destination);
            description = itemView.findViewById(R.id.layout_profile_trip_description);
            tripImage = itemView.findViewById(R.id.layout_profile_trip_item_trip_image);
            cardView = itemView.findViewById(R.id.layout_profile_trip_item_card_view);
        }
    }
}
