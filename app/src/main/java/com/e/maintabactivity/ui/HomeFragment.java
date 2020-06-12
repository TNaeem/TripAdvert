package com.e.maintabactivity.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.JsonReader;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.e.maintabactivity.MainActivity;
import com.e.maintabactivity.R;
import com.e.maintabactivity.adapters.FeaturedEventsAdapter;
import com.e.maintabactivity.adapters.HomeTabAdapter;

import com.e.maintabactivity.apiServises.EventsApiInterface;
import com.e.maintabactivity.apiServises.OrganizerApiInterface;
import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.models.EventModel;
import com.e.maintabactivity.models.EventModelResponse;
import com.e.maintabactivity.models.NewEventModel;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.staticModels.StaticEventModel;
import com.e.maintabactivity.staticModels.StaticOrganizerModel;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;
import java.util.List;


import javax.sql.StatementEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment implements MainActivity.SearchableFragment {

    private static final int USER_TYPE = 2;
    private static final String TAG = "HomeFragment";

    private RecyclerView mRecyclerView;
    private HomeTabAdapter homeTabAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private EventsApiInterface mEventsApiInterface;
    private OrganizerApiInterface mOrganizerApiInterface;

    private Retrofit mRetrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mRetrofit = RetrofitInstance.getRetrofitInstance();

        mOrganizerApiInterface = mRetrofit.create(OrganizerApiInterface.class);
        mEventsApiInterface = mRetrofit.create(EventsApiInterface.class);

        getAllOrganizers();

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setRefreshing(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView = view.findViewById(R.id.fragment_home_recyclerView);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        // Inflate the layout for this fragment
        if (StaticEventModel.allEvents != null) {
            homeTabAdapter = new HomeTabAdapter(getContext(),
                    StaticEventModel.allEvents, StaticOrganizerModel.allOrganizers);
        } else {
            homeTabAdapter = new HomeTabAdapter(getContext(),
                    new ArrayList<NewEventModel>(), new ArrayList<PersonModel>());
        }
        mRecyclerView.setAdapter(homeTabAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                StaticEventModel.allEvents = null;
                StaticOrganizerModel.allOrganizers = null;

                getAllOrganizers();
            }
        });

        return view;
    }

    private void getAllEvents() {
        if (StaticEventModel.allEvents != null) {
            return;
        }
        mEventsApiInterface.getAllEvents().enqueue(new Callback<List<NewEventModel>>() {
            @Override
            public void onResponse(Call<List<NewEventModel>> call, Response<List<NewEventModel>> response) {
                if (response.body() != null) {
                    Log.d(TAG, "onResponse: calling events " + response);
                    StaticEventModel.allEvents = response.body();
                    homeTabAdapter = new HomeTabAdapter(
                            getContext(), StaticEventModel.allEvents,
                            StaticOrganizerModel.allOrganizers);

                    mRecyclerView.setAdapter(homeTabAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<NewEventModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    private void getAllOrganizers() {

        if (StaticOrganizerModel.allOrganizers != null) {
            getAllEvents();
            return;
        }
        mOrganizerApiInterface.getAllOrganizers(USER_TYPE).enqueue(new Callback<List<PersonModel>>() {
            @Override
            public void onResponse(Call<List<PersonModel>> call, Response<List<PersonModel>> response) {
                if (response.body() != null) {
                    Log.d(TAG, "onResponse: calling organizers " + response);
                    StaticOrganizerModel.allOrganizers = response.body();
                    getAllEvents();
                }
            }

            @Override
            public void onFailure(Call<List<PersonModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: organizer" + t.getMessage());
            }
        });
    }


    @Override
    public void filter(String search) {
        homeTabAdapter.getFilter().filter(search);
        Log.d(TAG, "filter: " + search);
    }
}
