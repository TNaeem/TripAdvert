package com.e.maintabactivity.organizer.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.e.maintabactivity.R;
import com.e.maintabactivity.TripDetailsActivity;
import com.e.maintabactivity.models.ReviewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrganizerReviewsAdapter extends RecyclerView.Adapter<OrganizerReviewsAdapter.OrganizerReviewsAdapterViewHolder> {
    private List<ReviewModel> reviews;
    private Context context;
    int organizerId;
    public OrganizerReviewsAdapter(Context context, List<ReviewModel> reviews){
        this.context = context;
        this.reviews = reviews;

    }
    @NonNull
    @Override

    public OrganizerReviewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_review, parent, false);
        return new OrganizerReviewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizerReviewsAdapterViewHolder holder, int position) {
        final ReviewModel reviewModel = reviews.get(position);

        //holder.name.setText(reviewModel.get);
        //holder.destination.setText(eventModel.getDestination());
        //holder.description.setText(eventModel.getDescription());

        //Picasso.get().load(eventModel.getPic()).into(holder.tripImage);

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class OrganizerReviewsAdapterViewHolder extends RecyclerView.ViewHolder{
        MaterialTextView name;
        MaterialTextView message;
        ImageView image;
        MaterialTextView date;
        RatingBar ratingBar;

        public OrganizerReviewsAdapterViewHolder(View itemView){

            super(itemView);
            name = itemView.findViewById(R.id.layout_review_name);
            message = itemView.findViewById(R.id.layout_review_message);
            image = itemView.findViewById(R.id.layout_review_image);
            date = itemView.findViewById(R.id.layout_review_date_time);
            ratingBar = itemView.findViewById(R.id.layout_review_ratting);

        }
    }
}
