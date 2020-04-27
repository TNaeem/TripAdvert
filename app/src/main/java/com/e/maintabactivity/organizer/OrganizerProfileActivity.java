package com.e.maintabactivity.organizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.e.maintabactivity.organizer.adapters.OrganizerProfileViewPagerAdapter;
import com.e.maintabactivity.R;
import com.google.android.material.tabs.TabLayout;

public class OrganizerProfileActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private OrganizerProfileViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_profile);

        viewPagerAdapter = new OrganizerProfileViewPagerAdapter(this, getSupportFragmentManager());
        tabLayout = findViewById(R.id.activity_organizer_profile_tab_layout);
        viewPager = findViewById(R.id.activity_organizer_profile_view_pager);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.getTabAt(0).setIcon(R.drawable.icon_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_bookings);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_chat);
    }
}
