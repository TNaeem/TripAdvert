package com.e.maintabactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.alespero.expandablecardview.ExpandableCardView;
import com.e.maintabactivity.adapters.TripDetailImagesAdapter;
import com.e.maintabactivity.apiServises.EventImagesApiInterface;
import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.apiServises.UserApiInterface;
import com.e.maintabactivity.models.BookingModel;
import com.e.maintabactivity.models.EventModel;
import com.e.maintabactivity.models.ImageModel;
import com.e.maintabactivity.models.NewEventModel;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.organizer.ImageSliderActivity;
import com.e.maintabactivity.organizer.OrganizerProfileActivity;
import com.e.maintabactivity.organizer.adapters.OrganizerProfileGalleryAdapter;
import com.e.maintabactivity.utility.UserSharedPreference;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TripDetailsActivity extends AppCompatActivity {

    private Context mContext;
    private static final String TAG = "TripDetailsActivity";

    public TripDetailsActivity(){
        mContext = this;
    }

    private MaterialCardView mCardView;
    private ImageView mTripImage;

    private MaterialTextView mTripTitle;
    private MaterialTextView mTripDetail;

    private ImageView mIsAccommodation;
    private MaterialTextView mAccommodationDes;
    private ImageView mAccommodation;
    private MaterialTextView mAccommodationText;

    private ImageView mIsSightSeeing;
    private MaterialTextView mSightSeeingDes;
    private ImageView mSightSeeing;
    private MaterialTextView mSightSeeingText;

    private ImageView mIsFood;
    private MaterialTextView mFoodDes;
    private ImageView mFood;
    private MaterialTextView mFoodText;

    private MaterialTextView mHomeLocation;
    private MaterialTextView mDestinationLocation;
    private MaterialTextView mDepartureDate;
    private MaterialTextView mHomeBackDate;
    private MaterialTextView mTotalSlots;
    private MaterialTextView mAvailableSlots;
    private MaterialButton mBookNowBtn;

    private MaterialTextView mOrganizerName;
    private RatingBar mRatingBar;
    private ImageView mIsVerified;
    private ImageView mOrganizerImage;

    private PersonModel organizer;
    private NewEventModel eventModel;
    private EditText mBookingCount;

    private MaterialTextView mScheduleDay;
    private MaterialTextView mScheduleDesc;
    private LinearLayout mLinearLayout;

    private EventImagesApiInterface mEventImagesApiInterface;
    private UserApiInterface mUserApiInterface;
    private List<ImageModel> imageModelList;
    private GridView mGridView;
    androidx.appcompat.widget.Toolbar toolbar;
    private Context context = this;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        bindViews();

        // Setting Toolbar
        toolbar = findViewById(R.id.trip_detail_toolbar);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        // Receiving Intent data
        Bundle bundle = getIntent().getExtras();
        Gson gson = new Gson();
        eventModel = gson.fromJson(bundle.getString("trip"), NewEventModel.class);
        organizer = gson.fromJson(bundle.getString("organizer"), PersonModel.class);

        // Setting api interfaces
        mEventImagesApiInterface = RetrofitInstance.getRetrofitInstance().create(EventImagesApiInterface.class);
        mUserApiInterface = RetrofitInstance.getRetrofitInstance().create(UserApiInterface.class);
        getImagesByEventId(eventModel.getId());

        mGridView = findViewById(R.id.activity_trip_details_images_gridView);


        // Loading data
        loadEventData(eventModel);
        loadOrganizerData(organizer);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
// add the inflated View to the layoutS
        for(int i=0; i<3;i++){


            View v = (View) findViewById(R.layout.layout_schedule);
            Log.d(TAG, "onCreate: " + v);
            mScheduleDay = v.findViewById(R.id.schedule_day);
            mScheduleDesc = v.findViewById(R.id.schedule_description);
            mScheduleDay.setText("Day " + i);
            mScheduleDesc.setText("Some Description");

            mLinearLayout.addView(v);
        }

        // OnCLick on Organizer cardView
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrganizerProfileActivity.class);
                Gson gson = new Gson();
                intent.putExtra("organizer", gson.toJson(organizer));
                startActivity(intent);
            }
        });

        // Booking event
        mBookNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmBookingDialog(context);
            }
        });

    }

    private void confirmBookingDialog(Context c) {
        mBookingCount = new EditText(c);
        mBookingCount.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Booking")
                .setMessage("Confirm Booking")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BookingModel bookingModel = new BookingModel();
                        bookingModel.setEvent(eventModel.getId());
                        bookingModel.setUser(UserSharedPreference.getUser(context).getId());
                        postBooking(bookingModel);

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
    private void loadOrganizerData(PersonModel organizer){
        mOrganizerName.setText(organizer.getFirst_name() + " " + organizer.getLast_name());
        Picasso.get().load(organizer.getImage()).into(mOrganizerImage);
        mRatingBar.setRating(organizer.getOrganizer().getRating());
        if(!organizer.getOrganizer().isIs_verified()){
            mIsVerified.setVisibility(View.GONE);
        }
    }
    private void loadEventData(NewEventModel eventModel){
        Picasso.get().load(eventModel.getPic()).into(mTripImage);
        //mTripTitle.setText(eventModel.getTitle());
        toolbar.setTitle(eventModel.getTitle());
        mTripDetail.setText(eventModel.getDescription());
        mSightSeeingDes.setText(eventModel.getSightseeingDescription());
        if(!eventModel.isSightseeing()){
            mSightSeeing.setVisibility(View.GONE);
            mSightSeeingText.setText("");
            mIsSightSeeing.setVisibility(View.GONE);
            mSightSeeingDes.setText("");
        }
        mAccommodationDes.setText(eventModel.getAccommodationDescription());
        if(!eventModel.isAccommodated()){
            mAccommodation.setVisibility(View.GONE);
            mAccommodationText.setText("");
            mIsAccommodation.setVisibility(View.GONE);
            mAccommodationDes.setText("");
        }
        mFoodDes.setText(eventModel.getFoodDescription());
        if(!eventModel.isFood()){
            mFood.setVisibility(View.GONE);
            mFoodText.setText("");
            mIsFood.setVisibility(View.GONE);
            mFoodDes.setText("");
        }
        mDepartureDate.setText(eventModel.getDateOfDeparture());
        mHomeBackDate.setText(eventModel.getDateOfArrival());
        mDestinationLocation.setText(eventModel.getDestination());
        mHomeLocation.setText(eventModel.getHome());
        mTotalSlots.setText(String.valueOf(eventModel.getSlots()));
        mAvailableSlots.setText(String.valueOf(eventModel.getFreeSLots()));

    }
    public void bindViews(){

        mCardView     = findViewById(R.id.organizer_layout);
        mBookNowBtn = findViewById(R.id.activity_trip_detail_book_now_btn);

        //Trip Detail Views
        mTripImage    = findViewById(R.id.activity_trip_detail_image);
        mTripTitle = findViewById(R.id.activity_trip_detail_trip_title);
        mTripDetail = findViewById(R.id.activity_trip_detail_trip_detail);

        mIsSightSeeing = findViewById(R.id.sight_seeing_check);
        mSightSeeing = findViewById(R.id.trip_sightseeing_image);
        mSightSeeingDes = findViewById(R.id.sight_seeing_description);
        mSightSeeingText = findViewById(R.id.trip_sightseeing);

        mIsAccommodation = findViewById(R.id.accommodation_check);
        mAccommodation = findViewById(R.id.trip_accommodation_image);
        mAccommodationText = findViewById(R.id.trip_accommodation);
        mAccommodationDes = findViewById(R.id.accommodation_description);

        mIsFood = findViewById(R.id.food_check);
        mFood = findViewById(R.id.trip_food_image);
        mFoodText = findViewById(R.id.trip_food);
        mFoodDes = findViewById(R.id.food_description);

        mDepartureDate = findViewById(R.id.departure_date);
        mDestinationLocation = findViewById(R.id.destination_location);
        mHomeBackDate = findViewById(R.id.homeBack_date);
        mHomeLocation = findViewById(R.id.home_location);
        mTotalSlots = findViewById(R.id.activity_trip_detail_trip_total_slots);
        mAvailableSlots = findViewById(R.id.available_slots);

        mLinearLayout = findViewById(R.id.schedule_detail);
        //Organizer Layout Views
        mOrganizerName = findViewById(R.id.single_trip_organizer_name);
        mOrganizerImage = findViewById(R.id.trip_organizer_image);
        mRatingBar = findViewById(R.id.single_trip_organizer_ratting);
        mIsVerified = findViewById(R.id.single_trip_organizer_verify);
    }
    private void getImagesByEventId(int eventId){
        mEventImagesApiInterface.getImagesByEventId(eventId).enqueue(new Callback<List<ImageModel>>() {
            @Override
            public void onResponse(Call<List<ImageModel>> call, Response<List<ImageModel>> response) {
                if(response.body() != null && response.body().size() > 0){
                    imageModelList = response.body();
                    final List<String> images = extractImages(imageModelList);
                    mGridView.setAdapter(new OrganizerProfileGalleryAdapter(images, mContext));
                    mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(mContext, ImageSliderActivity.class);
                            intent.putStringArrayListExtra("images", (ArrayList<String>) images);
                            intent.putExtra("current", position);
                            startActivity(intent);
                        }
                    });
                }
                Log.d(TAG, "onResponse: " + response.body().size());
            }

            @Override
            public void onFailure(Call<List<ImageModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
    public List<String> extractImages( List<ImageModel> imageModels){
        List<String> images = new ArrayList<String>();
        for( ImageModel i : imageModels){
            images.add(i.getImage());
        }
        return images;
    }
    public void postBooking(BookingModel bookingModel){
        mUserApiInterface.addBooking(bookingModel).enqueue(new Callback<BookingModel>() {
            @Override
            public void onResponse(Call<BookingModel> call, Response<BookingModel> response) {
                if(response.body() != null ){
                    Toast.makeText(mContext, "Booking request is sent.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookingModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
