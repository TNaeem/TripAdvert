package com.e.maintabactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PrivateTripActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private static final String TAG = "MainActivity";
    private static int ERROR_DIALOG_REQUEST = 9001;
    Button mStartPointBtn;
    Button mDestinationBtn;

    private Button mDepartureDateBtn;
    private Button mArrivalDateBtn;
    private MaterialTextView mDepartureDateTextView;
    private MaterialTextView mArrivalDateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_trip);

        //Date Picker
        /*MaterialDatePicker.Builder<Long> builder =
                MaterialDatePicker.Builder.datePicker();
        MaterialDatePicker<Long> picker = builder.build();
        picker.show(getSupportFragmentManager(), picker.toString());
        */

        mDepartureDateBtn = findViewById(R.id.activity_private_trip_departure_date_btn);
        mArrivalDateBtn = findViewById(R.id.activity_private_trip_arrival_date_btn);
        mDepartureDateTextView = findViewById(R.id.activity_private_trip_departure_date_text);
        mArrivalDateTextView = findViewById(R.id.activity_private_trip_arrival_date_text);



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
                        mArrivalDateTextView.setText(mDateStr);
                    }

                };

                new DatePickerDialog(PrivateTripActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

    }


    private void init(){
        mStartPointBtn = findViewById(R.id.private_trip_activity_start_point_btn);
        mStartPointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MapActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isServiceOK(){
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(PrivateTripActivity.this);

        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG, "isServiceOK: Google play service is working");
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServiceOK: an error occur but can be fixed");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(PrivateTripActivity.this,available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make request", Toast.LENGTH_LONG).show();
        }
        return false;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
