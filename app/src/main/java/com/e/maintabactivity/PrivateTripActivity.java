package com.e.maintabactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.DateInterval;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PrivateTripActivity extends AppCompatActivity {

    private static final String TAG = "PrivateTripActivity";
    private static int ERROR_DIALOG_REQUEST = 9001;


    private MaterialButton    mDepartureDateBtn;
    private MaterialButton    mArrivalDateBtn;
    private MaterialTextView  mDepartureDateTextView;
    private MaterialTextView  mArrivalDateTextView;
    private TextInputEditText    mDepartureLocation;
    private TextInputEditText    mDestinationLocation;
    private TextInputEditText mTripTitle;
    private TextInputEditText mPassengersCount;
    private TextInputEditText mBudget;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_trip);

        bindView();

        mDepartureDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                        String mDateStr = sdf.format(myCalendar.getTime());
                        long d = new Date().getTime();
                        long d1 = myCalendar.getTime().getTime();
                        long diff = d1 - d;

                        Log.d(TAG, "Difference " + diff);
                        mDepartureDateTextView.setText(mDateStr);
                    }

                };

                new DatePickerDialog(PrivateTripActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        mArrivalDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                        String mDateStr = sdf.format(myCalendar.getTime());
                        Toast.makeText(PrivateTripActivity.this, "Arrival Time " + myCalendar.getTime(), Toast.LENGTH_SHORT).show();
                        mArrivalDateTextView.setText(mDateStr);
                    }

                };

                new DatePickerDialog(PrivateTripActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

    }
    public void bindView(){
        mTripTitle = findViewById(R.id.activity_private_trip_title);
        mPassengersCount = findViewById(R.id.activity_private_trip_passengers_count);
        mBudget = findViewById(R.id.activity_private_trip_budget);
        mDepartureDateBtn = findViewById(R.id.activity_private_trip_departure_date_btn);
        mArrivalDateBtn = findViewById(R.id.activity_private_trip_arrival_date_btn);
        mDepartureDateTextView = findViewById(R.id.activity_private_trip_departure_date_text);
        mArrivalDateTextView = findViewById(R.id.activity_private_trip_arrival_date_text);
        mDepartureLocation = findViewById(R.id.private_trip_activity_departure_location);
        mDestinationLocation = findViewById(R.id.private_trip_activity_destination);


    }

}
