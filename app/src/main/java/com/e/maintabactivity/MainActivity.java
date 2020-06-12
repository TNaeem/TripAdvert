package com.e.maintabactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.e.maintabactivity.adapters.MainActivityViewPager;
import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.apiServises.UserApiInterface;
import com.e.maintabactivity.models.FirebaseInstanceModel;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.utility.UserSharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainActivityViewPager viewPagerAdapter;
    private String[] trips = {"Trip 1", "Trip 2", "Trip 3", "Trip 4", "Trip 5"};
    private SearchableFragment searchableFragment;

    private Menu menu;
    private Context context =this;
    private String firebase_token;
    private DatabaseReference myRef;
    private int myId;


    private UserApiInterface userApiInterface;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);

        // Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("tokens");

        // Setting Api Interface
        userApiInterface = RetrofitInstance.getRetrofitInstance().create(UserApiInterface.class);

        // Getting Firebase Token
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            //Checking app registration
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(!task .isSuccessful()){
                    Log.i(TAG, "Task Failed");
                    return;
                }

                firebase_token = task.getResult().getToken();

                postFirebaseInstanceToken();
                myRef.child(String.valueOf(myId)).setValue(firebase_token);
                Log.d(TAG, "onComplete: " + firebase_token);

            }
        });




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



    public void postFirebaseInstanceToken(){
        if(UserSharedPreference.getUser(context) == null )
            return;
         myId = UserSharedPreference.getUser(context).getId();

        FirebaseInstanceModel token = new FirebaseInstanceModel(firebase_token);
        Log.d(TAG, "postFirebaseInstanceToken: " + myId  + " " + firebase_token) ;
        userApiInterface.postFirebaseInstanceId(myId, token).enqueue(new Callback<PersonModel>() {
            @Override
            public void onResponse(Call<PersonModel> call, Response<PersonModel> response) {
                if(response.body()!= null ){
                    PersonModel p = response.body();
                    UserSharedPreference.removeUser(context);
                    UserSharedPreference.saveUser(context, p);
                    Log.d(TAG, "onResponse: Firebase " + p.getFirebaseInstanceId());
                }

            }

            @Override
            public void onFailure(Call<PersonModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
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

        getMenuInflater().inflate(R.menu.action_search, menu);
        MenuItem item = menu.findItem(R.id.search);
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        MenuItem logout = menu.findItem(R.id.menu_logout);
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
