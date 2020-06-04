package com.e.maintabactivity.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.e.maintabactivity.ChatActivity;
import com.e.maintabactivity.R;

public class ReviewsListAdapter extends RecyclerView.Adapter<ReviewsListAdapter.ReviewsListAdapterViewHolder> {
    private static final String TAG = "ReviewsListAdapter";

    private String[] mReviews;
    private Context mContext;
    public ReviewsListAdapter(Context context, String [] reviews){
        this.mContext = context ;
        this.mReviews = reviews;
    }
    @NonNull
    @Override

    public ReviewsListAdapter.ReviewsListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_review, parent, false);
        return new ReviewsListAdapter.ReviewsListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsListAdapter.ReviewsListAdapterViewHolder holder, int position) {
        //String message = mContacts[position];
        holder.message.setText("message");
        holder.ratingBar.setRating(3);
    }

    @Override
    public int getItemCount() {
        return mReviews.length;
    }

    public class ReviewsListAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView personName;
        TextView message;
        ImageView personImage;
        TextView date;
        RatingBar ratingBar;
        public ReviewsListAdapterViewHolder(View itemView){

            super(itemView);
            personImage = itemView.findViewById(R.id.layout_review_image);
            personName = itemView.findViewById(R.id.layout_review_name);
            message = itemView.findViewById(R.id.layout_review_message);
            date  = itemView.findViewById(R.id.layout_review_date_time);
            ratingBar = itemView.findViewById(R.id.layout_review_ratting);
        }

    }
}
