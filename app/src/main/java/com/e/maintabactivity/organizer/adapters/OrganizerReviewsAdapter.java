package com.e.maintabactivity.organizer.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.models.ReviewModel;
import com.e.maintabactivity.services.UserServices;
import com.e.maintabactivity.staticModels.StaticUserModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrganizerReviewsAdapter extends RecyclerView.Adapter<OrganizerReviewsAdapter.OrganizerReviewsAdapterViewHolder> {

    private static final String TAG = "OrganizerReviewsAdapter";
    private List<ReviewModel> reviews;
    
    private Context context;
    int organizerId;
    public OrganizerReviewsAdapter(Context context, List<ReviewModel> reviews, int organizerId){
        this.context = context;
        this.reviews = reviews;
        this.organizerId = organizerId;

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

        holder.ratingBar.setRating(reviewModel.getRating());
        holder.date.setText(reviewModel.getDate());
        holder.message.setText(reviewModel.getMessage());
        holder.date.setText(reviewModel.getDate());
        PersonModel user = StaticUserModel.getUser(reviewModel.getUser());
        Log.d(TAG, "onBindViewHolder: " + user + " " + reviewModel.getUser());

        if(user.getImage()!= null){
            boolean isImageOK = UserServices.verifyImage(user.getImage());
            if(isImageOK){
                Picasso.get().load(user.getImage()).into(holder.image);
            }else{
                Picasso.get().load(RetrofitInstance.BASE_URL+user.getImage()).into(holder.image);
            }
        }else{
            Picasso.get().load(user.getImage()).into(holder.image);
        }
        holder.name.setText(user.getFirst_name() + " " + user.getLast_name());


        // First You have to get User
        //holder.name.setText(reviewModel.getUser());
        //Picasso.get().load(eventModel.getPic()).into(holder.tripImage);

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class OrganizerReviewsAdapterViewHolder extends RecyclerView.ViewHolder{
        MaterialTextView name;
        ImageView image;
        MaterialTextView date;
        RatingBar ratingBar;
        MaterialTextView message;

        public OrganizerReviewsAdapterViewHolder(View itemView){

            super(itemView);
            name = itemView.findViewById(R.id.layout_review_name);
            image = itemView.findViewById(R.id.layout_review_image);
            date = itemView.findViewById(R.id.layout_review_date_time);
            ratingBar = itemView.findViewById(R.id.layout_review_ratting);
            message = itemView.findViewById(R.id.layout_review_message);

        }
    }
}
