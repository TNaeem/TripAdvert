package com.e.maintabactivity.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.maintabactivity.R;
import com.e.maintabactivity.models.UserBookingModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class UserBookingsAdapter extends RecyclerView.Adapter<UserBookingsAdapter.UserBookingsAdapterViewHolder> {

    private List<UserBookingModel> bookingModelList;
    private Context context;
    public UserBookingsAdapter(Context context, List<UserBookingModel> bookingModelList){
        this.context = context;
        this.bookingModelList = bookingModelList;
    }
    @NonNull
    @Override

    public UserBookingsAdapter.UserBookingsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_user_booking, parent, false);
        return new UserBookingsAdapter.UserBookingsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserBookingsAdapter.UserBookingsAdapterViewHolder holder, int position) {
        UserBookingModel bookingModel = bookingModelList.get(position);
        holder.tripTitle.setText(bookingModel.getEventDetails().getTitle());
        holder.amount.setText(bookingModel.getEventDetails().getPrice() + " Rs");
        holder.date.setText(bookingModel.getEventDetails().getDateOfDeparture());
        if(bookingModel.isIs_verified()){
            holder.status.setText("Approved");
            holder.status.setTextColor(Color.GREEN);
        }

    }

    @Override
    public int getItemCount() {
        return bookingModelList.size();
    }

    public class UserBookingsAdapterViewHolder extends RecyclerView.ViewHolder{
        MaterialTextView tripTitle;
        MaterialTextView amount;
        MaterialTextView status;
        MaterialTextView date;
        MaterialCardView cardView;
        public UserBookingsAdapterViewHolder(View itemView){

            super(itemView);
            cardView = itemView.findViewById(R.id.layout_user_booking_cardView);
            tripTitle = itemView.findViewById(R.id.layout_user_booking_trip_title);
            amount = itemView.findViewById(R.id.layout_user_booking_trip_amount);
            date = itemView.findViewById(R.id.layout_user_booking_trip_date);
            status = itemView.findViewById(R.id.layout_user_booking_trip_status);
        }
    }
}
