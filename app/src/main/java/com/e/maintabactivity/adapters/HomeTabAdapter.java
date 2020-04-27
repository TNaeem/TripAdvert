package com.e.maintabactivity.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.e.maintabactivity.TripDetailsActivity;
import com.e.maintabactivity.organizer.OrganizerProfileActivity;
import com.e.maintabactivity.R;

public class HomeTabAdapter extends RecyclerView.Adapter<HomeTabAdapter.HomeTabFragmentAdapterViewHolder> {

    private String[] trips;
    Context context;
    public HomeTabAdapter(Context context, String [] trips){
        this.context = context;
        this.trips = trips;
    }
    @NonNull
    @Override

    public HomeTabFragmentAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_trip, parent, false);
        return new HomeTabFragmentAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeTabFragmentAdapterViewHolder holder, int position) {
        String title = trips[position];
        holder.tripTitle.setText(title);
        holder.tripImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(v.getContext(), TripDetailsActivity.class));
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
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

    public class HomeTabFragmentAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView tripTitle;
        TextView tripPrice;
        ImageView tripImage;
        CardView cardView;
        public HomeTabFragmentAdapterViewHolder(View itemView){

            super(itemView);
            tripTitle = itemView.findViewById(R.id.trip_title);
            tripImage = itemView.findViewById(R.id.trip_image);
            cardView = itemView.findViewById(R.id.organizer_layout);
        }
    }

}
