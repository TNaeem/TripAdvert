package com.e.maintabactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alespero.expandablecardview.ExpandableCardView;
import com.e.maintabactivity.organizer.OrganizerProfileActivity;
import com.google.android.material.card.MaterialCardView;


public class TripDetailsActivity extends AppCompatActivity {

    private Context mContext;

    public TripDetailsActivity(){
        mContext = this;
    }


    private ExpandableCardView mDetailsCard;
    private ExpandableCardView mScheduleCard;
    private ExpandableCardView mCommentsCard;
    private ImageView          mBackBtn;
    private MaterialCardView mCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        bindViews();



        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrganizerProfileActivity.class);
                startActivity(intent);
            }
        });
        mDetailsCard.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
                Toast.makeText(getApplicationContext(), isExpanded ? "Expanded!" : "Collapsed!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void bindViews(){
        mDetailsCard  = findViewById(R.id.activity_trip_details);
        mScheduleCard = findViewById(R.id.activity_trip_schedule);
        mCommentsCard = findViewById(R.id.activity_trip_comments);
        mBackBtn      = findViewById(R.id.activity_trip_details_back_btn);
        mCardView     = findViewById(R.id.organizer_layout);
    }

}
