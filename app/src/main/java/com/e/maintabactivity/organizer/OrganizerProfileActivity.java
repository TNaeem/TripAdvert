package com.e.maintabactivity.organizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.RatingBar;

import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.organizer.adapters.OrganizerProfileViewPagerAdapter;
import com.e.maintabactivity.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrganizerProfileActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private OrganizerProfileViewPagerAdapter viewPagerAdapter;
    private CircleImageView mOrganizerImage;
    private RatingBar mOrganizerRating;
    private MaterialTextView mOrganizerName;
    private Bundle mBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_profile);

        mBundle = getIntent().getExtras();
        Gson gson = new Gson();
        PersonModel personModel = gson.fromJson(mBundle.getString("organizer"), PersonModel.class);

        viewPagerAdapter = new OrganizerProfileViewPagerAdapter(this, getSupportFragmentManager(), personModel);


        tabLayout = findViewById(R.id.activity_organizer_profile_tab_layout);
        viewPager = findViewById(R.id.activity_organizer_profile_view_pager);

        mOrganizerImage = findViewById(R.id.activity_organizer_profile_image);
        mOrganizerName = findViewById(R.id.activity_organizer_profile_name);
        mOrganizerRating = findViewById(R.id.activity_organizer_profile_rating);

        Picasso.get().load(personModel.getImage()).into(mOrganizerImage);
        mOrganizerRating.setRating(personModel.getOrganizer().getRating());
        mOrganizerName.setText(personModel.getFirst_name() + " " + personModel.getLast_name());

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);




        tabLayout.getTabAt(0).setIcon(R.drawable.icon_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_bookings);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_chat);
    }
}
