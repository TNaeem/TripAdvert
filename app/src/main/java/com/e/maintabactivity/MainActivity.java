package com.e.maintabactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.e.maintabactivity.adapters.MainActivityViewPager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private com.miguelcatalan.materialsearchview.MaterialSearchView searchView ;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainActivityViewPager viewPagerAdapter;
    private String[] trips = {"Trip 1", "Trip 2", "Trip 3", "Trip 4", "Trip 5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TabLayout
        viewPagerAdapter = new MainActivityViewPager(this, getSupportFragmentManager());
        tabLayout = findViewById(R.id.activity_main_tab_layout);
        Log.d("MainActivity", "onCreate: " + tabLayout);
        viewPager = findViewById(R.id.activity_main_view_pager);
        searchView = findViewById(R.id.search_view);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //TabLayout Icons
        tabLayout.getTabAt(0).setIcon(R.drawable.icon_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_organizers);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_bookings);
        tabLayout.getTabAt(3).setIcon(R.drawable.icon_chat);
        tabLayout.getTabAt(4).setIcon(R.drawable.icon_profile);
        tabLayout.getTabAt(5).setIcon(R.drawable.icon_notification);

        // Material SearchView
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        //Discounted Event Recycler View
        recyclerView = findViewById(R.id.activity_main_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new FeaturedEventsAdapter(this, trips));
        */
    }

 @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     getMenuInflater().inflate(R.menu.action_search, menu);

     MenuItem item = menu.findItem(R.id.search);
     searchView.setMenuItem(item);
     searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
         @Override
         public boolean onQueryTextSubmit(String query) {
             //Do some magic
             return false;
         }

         @Override
         public boolean onQueryTextChange(String newText) {
             //Do some magic
             return false;
         }
     });

     return true;
    }
}
