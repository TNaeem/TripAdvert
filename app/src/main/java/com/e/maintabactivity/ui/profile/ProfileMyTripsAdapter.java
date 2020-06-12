package com.e.maintabactivity.ui.profile;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.e.maintabactivity.R;
import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.apiServises.ReviewsApiInterface;
import com.e.maintabactivity.models.EventModel;
import com.e.maintabactivity.models.ReviewModel;
import com.e.maintabactivity.models.UserPortfolioEventModel;
import com.e.maintabactivity.utility.UserSharedPreference;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileMyTripsAdapter extends RecyclerView.Adapter<ProfileMyTripsAdapter.ProfileMyTripsAdapterViewHolder>{
    private List<UserPortfolioEventModel> userPortfolioEventModelList;
    private Context context;
    private static final String TAG = "ProfileMyTripsAdapter";
    private RatingBar mRatingBar;
    private TextInputEditText mRating;
    private TextInputEditText mMessage;

    ReviewsApiInterface reviewsApiInterface = RetrofitInstance.getRetrofitInstance().create(ReviewsApiInterface.class);

    public ProfileMyTripsAdapter(Context context, List<UserPortfolioEventModel> userPortfolioEventModelList){
        this.context = context;
        this.userPortfolioEventModelList = userPortfolioEventModelList;
    }
    @NonNull
    @Override

    public ProfileMyTripsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_user_profile_trip_item, parent, false);
        return new ProfileMyTripsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfileMyTripsAdapter.ProfileMyTripsAdapterViewHolder holder, int position) {
        final UserPortfolioEventModel userPortfolioEventModel = userPortfolioEventModelList.get(position);
        holder.tripTitle.setText(userPortfolioEventModel.getEvent().getTitle());
        holder.description.setText(userPortfolioEventModel.getEvent().getDescription());
        Picasso.get().load(userPortfolioEventModel.getEvent().getPic()).into(holder.tripImage);

        holder.destination.setText(userPortfolioEventModel.getEvent().getDestination());
        if(userPortfolioEventModel.getRating() > 0 ){
            holder.ratingBar.setRating(userPortfolioEventModel.getRating());
        }else{
            mRating.setVisibility(View.GONE);
        }

        // add Review Button
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.trip_review_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.add_review:
                                addReviewDialog(context, userPortfolioEventModel.getEvent().getOrganizer(),
                                                    userPortfolioEventModel.getEvent().getId());
                                //handle menu1 click
                                break;
                        }
                        return false;

                    }
                });
                popup.show();
            }
        });

        //Log.d(TAG, "onBindViewHolder: " + eventModel.getPic());
        // Click Listener on cardView

    }

    @Override
    public int getItemCount() {
        return userPortfolioEventModelList.size();
    }

    public class ProfileMyTripsAdapterViewHolder extends RecyclerView.ViewHolder{
        MaterialTextView tripTitle;
        MaterialTextView destination;
        ImageView tripImage;
        MaterialTextView description;
        TextView buttonViewOption;
        RatingBar ratingBar;

        public ProfileMyTripsAdapterViewHolder(View itemView){

            super(itemView);
            tripTitle = itemView.findViewById(R.id.layout_user_profile_trip_title);
            destination = itemView.findViewById(R.id.layout_user_profile_trip_destination);
            description = itemView.findViewById(R.id.layout_user_profile_trip_description);
            tripImage = itemView.findViewById(R.id.layout_user_profile_trip_image);
            buttonViewOption = itemView.findViewById(R.id.review_options);
            ratingBar = itemView.findViewById(R.id.review);
            mMessage = itemView.findViewById(R.id.editText2);
            mRating = itemView.findViewById(R.id.editText1);

        }
    }

    public void addReview(ReviewModel reviewModel){
        reviewsApiInterface.addReview(reviewModel).enqueue(new Callback<ReviewModel>() {
            @Override
            public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
                if(response.body() != null){
                    Log.d(TAG, "onResponse:  Review Added ");
                }
            }
            @Override
            public void onFailure(Call<ReviewModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage() );
            }
        });

    }

    private void addReviewDialog(Context c, final int ogrID, final int eventId) {
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setIcon(R.drawable.logo)
                .setTitle("Event Rating")
                .setMessage("Rate this trip.")
                .setView(R.layout.layout_add_review_dialog)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(mRating.getText());
                        Integer rating = Integer.valueOf(task);
                        ReviewModel reviewModel = new ReviewModel(rating,
                                UserSharedPreference.getUser(context).getId(),
                                ogrID,
                                eventId,
                                mMessage.getText().toString());
                        addReview(reviewModel);

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
}
