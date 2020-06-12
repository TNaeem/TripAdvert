package com.e.maintabactivity.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.e.maintabactivity.TripDetailsActivity;
import com.e.maintabactivity.apiServises.EventImagesApiInterface;
import com.e.maintabactivity.models.EventModel;
import com.e.maintabactivity.models.NewEventModel;
import com.e.maintabactivity.models.OrganizerModel;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.organizer.OrganizerProfileActivity;
import com.e.maintabactivity.R;
import com.e.maintabactivity.staticModels.StaticUserModel;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Preconditions;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HomeTabAdapter extends RecyclerView.Adapter<HomeTabAdapter.HomeTabFragmentAdapterViewHolder> implements Filterable {

    private List<NewEventModel> mTrips;
    private List<NewEventModel> mTripsAll;
    List<PersonModel> mOrganizersList;
    Context context;
    private static final String TAG = "HomeTabAdapter";
    
    public HomeTabAdapter(Context context, List<NewEventModel> trips, List<PersonModel> mOrganizersList){
        this.context = context;
        this.mTrips = trips;
        this.mOrganizersList = mOrganizersList;
        this.mTripsAll = new ArrayList<>(mTrips);
    }

    private PersonModel getOrganizer(int id){

        for (PersonModel organizer :  mOrganizersList) {
            if(id == organizer.getOrganizer().getId()){
                return organizer;
            }
        }
        return null;
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
        final NewEventModel eventModel = mTrips.get(position);
        holder.tripTitle.setText(eventModel.getTitle());
        Picasso.get().load(eventModel.getPic()).into(holder.tripImage);
        holder.tripPrice.setText(eventModel.getPrice() + " Rs");
        holder.destination.setText(eventModel.getDestination());
        holder.home.setText(eventModel.getHome());
        holder.date.setText(eventModel.getDateOfDeparture());

        final PersonModel organizer = getOrganizer(eventModel.getOrganizer().getId());
        holder.organizerName.setText(organizer.getFirst_name() + " " + organizer.getLast_name());
        holder.ratingBar.setRating(organizer.getOrganizer().getRating());
        Picasso.get().load(organizer.getImage()).into(holder.organizerImage);
        if(!organizer.getOrganizer().isIs_verified()){
            holder.isVerified.setVisibility(View.GONE);
        }

        holder.tripImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(v.getContext(), TripDetailsActivity.class);
                Gson gson = new Gson();
                intent.putExtra("trip", gson.toJson(eventModel));
                intent.putExtra("organizer", gson.toJson(organizer));
                context.startActivity(intent);
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OrganizerProfileActivity.class);
                Gson gson = new Gson();
                intent.putExtra("organizer", gson.toJson(organizer));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrips.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<NewEventModel> filteredEvents = new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filteredEvents.addAll(mTripsAll);
                Log.d(TAG, "performFiltering: Empty" );
            }else{
                Log.d(TAG, "performFiltering: " + constraint);
                for( NewEventModel e : mTripsAll){

                    if(e.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())
                    || e.getDestination().toLowerCase().contains(constraint.toString().toLowerCase())
                    || e.getHome().toLowerCase().contains(constraint.toString().toLowerCase())){

                        filteredEvents.add(e);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredEvents;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mTrips.clear();
            mTrips.addAll((Collection<? extends NewEventModel>) results.values);
            Log.d(TAG, "publishResults: " + mTrips.size());
            notifyDataSetChanged();
        }
    };

    public class HomeTabFragmentAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView tripTitle;
        TextView tripPrice;
        ImageView tripImage;
        CardView cardView;
        TextView home;
        TextView destination;
        TextView date;

        ImageView organizerImage;
        TextView organizerName;
        ImageView isVerified;
        RatingBar ratingBar;
        public HomeTabFragmentAdapterViewHolder(View itemView){

            super(itemView);
            tripTitle = itemView.findViewById(R.id.trip_title);
            tripImage = itemView.findViewById(R.id.trip_image);
            cardView = itemView.findViewById(R.id.organizer_layout);
            tripPrice = itemView.findViewById(R.id.single_trip_price);
            home = itemView.findViewById(R.id.single_trip_destination);
            destination = itemView.findViewById(R.id.single_trip_start_point);
            organizerImage = itemView.findViewById(R.id.trip_organizer_image);
            organizerName = itemView.findViewById(R.id.single_trip_organizer_name);
            isVerified = itemView.findViewById(R.id.single_trip_organizer_verify);
            ratingBar = itemView.findViewById(R.id.single_trip_organizer_ratting);
            date = itemView.findViewById(R.id.single_trip_date);
        }
    }

}
