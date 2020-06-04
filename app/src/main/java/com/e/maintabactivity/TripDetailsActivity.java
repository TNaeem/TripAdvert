package com.e.maintabactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.alespero.expandablecardview.ExpandableCardView;
import com.e.maintabactivity.adapters.TripDetailImagesAdapter;
import com.e.maintabactivity.apiServises.EventImagesApiInterface;
import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.models.EventModel;
import com.e.maintabactivity.models.ImageModel;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.organizer.ImageSliderActivity;
import com.e.maintabactivity.organizer.OrganizerProfileActivity;
import com.e.maintabactivity.organizer.adapters.OrganizerProfileGalleryAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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


    private TextView mAddReviewBtn;
    private TextView mViewAllReviewsBtn;
    private ImageView mBackBtn;
    private MaterialCardView mCardView;
    private ImageView mTripImage;

    private MaterialTextView mTripTitle;
    private MaterialTextView mTripDetail;
    private ImageView mIsAccommodation;
    private ImageView mIsSightSeeing;
    private ImageView mIsFood;
    private MaterialTextView mAccommodationDes;
    private MaterialTextView mSightSeeingDes;
    private MaterialTextView mFoodDes;
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
    private EventModel eventModel;
    private EditText mBookingCount;

    private EventImagesApiInterface mEventImagesApiInterface;
    private List<ImageModel> imageModelList;
    private GridView mGridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        bindViews();

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        Bundle bundle = getIntent().getExtras();
        Gson gson = new Gson();
        eventModel = gson.fromJson(bundle.getString("trip"), EventModel.class);
        organizer = gson.fromJson(bundle.getString("organizer"), PersonModel.class);
        Log.d(TAG, "onCreate: " + eventModel.getId());

        mEventImagesApiInterface = RetrofitInstance.getRetrofitInstance().create(EventImagesApiInterface.class);
        getImagesByEventId(eventModel.getId());
        //Receiving passed eventModel object

        mGridView = findViewById(R.id.activity_trip_details_images_gridView);


        loadEventData(eventModel);
        loadOrganizerData(organizer);

        // Setting data



        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrganizerProfileActivity.class);
                Gson gson = new Gson();
                intent.putExtra("organizer", gson.toJson(organizer));
                startActivity(intent);
            }
        });

        mBookNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog(TripDetailsActivity.this);
            }
        });

    }
    private void showAddItemDialog(Context c) {
        mBookingCount = new EditText(c);
        mBookingCount.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Booking")
                .setMessage("How many seats you want to book?")
                .setView(mBookingCount)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(mBookingCount.getText());
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
    private void loadEventData(EventModel eventModel){
        Picasso.get().load(eventModel.getPic()).into(mTripImage);
        mTripTitle.setText(eventModel.getTitle());
        mTripDetail.setText(eventModel.getDescription());
        mSightSeeingDes.setText(eventModel.getSightseeingDescription());
        if(!eventModel.isSightseeing()){
            mIsSightSeeing.setImageResource(R.drawable.icon_cancel);
            mIsSightSeeing.setColorFilter(R.color.redColor);
            mSightSeeingDes.setText("");
        }
        mAccommodationDes.setText(eventModel.getAccommodationDescription());
        if(!eventModel.isAccommodated()){
            mIsAccommodation.setImageResource(R.drawable.icon_cancel);
            mIsAccommodation.setColorFilter(R.color.redColor);
            mAccommodationDes.setText("");
        }
        mFoodDes.setText(eventModel.getFoodDescription());
        if(!eventModel.isFood()){
            mIsFood.setImageResource(R.drawable.icon_cancel);
            mIsFood.setColorFilter(R.color.redColor);
            mFoodDes.setText("");
        }
        mDepartureDate.setText(eventModel.getDateOfDeparture());
        mHomeBackDate.setText(eventModel.getDateOfArrival());
        mDestinationLocation.setText(eventModel.getDestination());
        mHomeLocation.setText(eventModel.getHome());
        //mTotalSlots.setText(eventModel.getSlots());

    }
    public void bindViews(){

        mCardView     = findViewById(R.id.organizer_layout);
        mBookNowBtn = findViewById(R.id.activity_trip_detail_book_now_btn);

        //Trip Detail Views
        mTripImage    = findViewById(R.id.activity_trip_detail_image);
        mTripTitle = findViewById(R.id.activity_trip_detail_trip_title);
        mTripDetail = findViewById(R.id.activity_trip_detail_trip_detail);
        mIsSightSeeing = findViewById(R.id.sight_seeing_check);
        mIsAccommodation = findViewById(R.id.accommodation_check);
        mIsFood = findViewById(R.id.food_check);
        mAccommodationDes = findViewById(R.id.accommodation_description);
        mSightSeeingDes = findViewById(R.id.sight_seeing_description);
        mFoodDes = findViewById(R.id.food_description);
        mDepartureDate = findViewById(R.id.departure_date);
        mDestinationLocation = findViewById(R.id.destination_location);
        mHomeBackDate = findViewById(R.id.homeBack_date);
        mHomeLocation = findViewById(R.id.home_location);
        mTotalSlots = findViewById(R.id.activity_trip_detail_trip_total_slots);
        mAvailableSlots = findViewById(R.id.activity_trip_detail_trip_available_slots);


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

}
