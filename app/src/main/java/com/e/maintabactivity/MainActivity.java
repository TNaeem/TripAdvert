package com.e.maintabactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.e.maintabactivity.adapters.MainActivityViewPager;
import com.e.maintabactivity.ui.HomeFragment;
import com.e.maintabactivity.utility.UserSharedPreference;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainActivityViewPager viewPagerAdapter;
    private String[] trips = {"Trip 1", "Trip 2", "Trip 3", "Trip 4", "Trip 5"};
    SearchableFragment searchableFragment;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TabLayout
        viewPagerAdapter = new MainActivityViewPager(this, getSupportFragmentManager());
        tabLayout = findViewById(R.id.activity_main_tab_layout);
        viewPager = findViewById(R.id.activity_main_view_pager);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //TabLayout Icons
        tabLayout.getTabAt(0).setIcon(R.drawable.icon_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_organizers);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_bookings);
        tabLayout.getTabAt(3).setIcon(R.drawable.icon_chat);
        tabLayout.getTabAt(4).setIcon(R.drawable.icon_profile);
        tabLayout.getTabAt(5).setIcon(R.drawable.icon_notification);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //menu.getItem(1).setVisible(false);

                int index = tabLayout.getSelectedTabPosition();
                Fragment fragment = viewPagerAdapter.getItem(index);

                if(fragment instanceof SearchableFragment){
                    menu.getItem(1).setVisible(true);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // Material SearchView
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        MenuItem logout = menu.findItem(R.id.menu_logout);
        getMenuInflater().inflate(R.menu.action_search, menu);
        MenuItem item = menu.findItem(R.id.search);
        MaterialSearchView searchView = findViewById(R.id.search_view);
        searchView.setMenuItem(item);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                int index = tabLayout.getSelectedTabPosition();
                searchableFragment = (SearchableFragment) viewPagerAdapter.getItem(index);

                if(searchableFragment != null){
                    searchableFragment.filter(newText);
                }
                return false;
            }
        });

     return true;
    }

public interface SearchableFragment{
        public void filter(String search);
}
}
