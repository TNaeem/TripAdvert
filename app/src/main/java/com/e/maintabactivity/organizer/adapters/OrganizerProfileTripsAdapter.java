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
import com.e.maintabactivity.models.EventModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrganizerProfileTripsAdapter extends RecyclerView.Adapter<OrganizerProfileTripsAdapter.OrganizerProfileTripsAdapterViewHolder> {
    private List<EventModel> eventModelList;
    private Context context;
    public OrganizerProfileTripsAdapter(Context context, List<EventModel> eventModelList){
        this.context = context;
        this.eventModelList = eventModelList;

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
        final EventModel eventModel = eventModelList.get(position);

        holder.tripTitle.setText(eventModel.getTitle());
        holder.destination.setText(eventModel.getDestination());
        holder.description.setText(eventModel.getDescription());

        Picasso.get().load(eventModel.getPic()).into(holder.tripImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TripDetailsActivity.class);
                Gson gson = new Gson();
                intent.putExtra("trip", gson.toJson(eventModel));
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return eventModelList.size();
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
            tripTitle = itemView.findViewById(R.id.layout_profile_trip_title);
            destination = itemView.findViewById(R.id.layout_profile_trip_destination);
            description = itemView.findViewById(R.id.layout_profile_trip_description);
            tripImage = itemView.findViewById(R.id.layout_profile_trip_item_trip_image);
            cardView = itemView.findViewById(R.id.layout_profile_trip_item_card_view);
        }
    }
}
