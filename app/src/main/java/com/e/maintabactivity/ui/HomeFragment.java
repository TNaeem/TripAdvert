package com.e.maintabactivity.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.e.maintabactivity.models.PersonModel;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements MainActivity.SearchableFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int USER_TYPE = 2;
    private static final String TAG = "HomeFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView mRecyclerView;
    RecyclerView mFeaturedEventRecyclerView;
    String[] trips = {"Title1", "Going to trips with Family an friends"} ;
    HomeTabAdapter homeTabAdapter;
    boolean adapter = false;


    private EventsApiInterface mEventsApiInterface;
    private OrganizerApiInterface mOrganizerApiInterface;
    private List<PersonModel> organizersList;
    private EventModelResponse mEventModelResponse;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mOrganizerApiInterface = RetrofitInstance.getRetrofitInstance().create(OrganizerApiInterface.class);
        mEventsApiInterface = RetrofitInstance.getRetrofitInstance().create(EventsApiInterface.class);
        getAllOrganizers();

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView = view.findViewById(R.id.fragment_home_recyclerView);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        LinearLayoutManager featuredEventLinearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false);
        mFeaturedEventRecyclerView = view.findViewById(R.id.featured_events_recyclerView);
        mFeaturedEventRecyclerView.setLayoutManager(featuredEventLinearLayoutManager);
        mFeaturedEventRecyclerView.setAdapter(new FeaturedEventsAdapter(getContext(), trips));
        // Inflate the layout for this fragment
        homeTabAdapter= new HomeTabAdapter(getContext(), new ArrayList<EventModel>(), new ArrayList<PersonModel>());
        mRecyclerView.setAdapter(homeTabAdapter);
        return view;
    }

    private void getAllEvents(){
        mEventsApiInterface.getAllEvents().enqueue(new Callback<EventModelResponse>() {
            @Override
            public void onResponse(Call<EventModelResponse> call, Response<EventModelResponse> response) {
                if(response.body() != null){

                    mEventModelResponse = response.body();
                    homeTabAdapter= new HomeTabAdapter(getContext(), mEventModelResponse.getResults(), organizersList);
                    mRecyclerView.setAdapter(homeTabAdapter);
                    adapter = true;
                    Log.d(TAG, "onResponse: " + adapter);

                }
            }
            @Override
            public void onFailure(Call<EventModelResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: call " + t);
            }
        });
    }

    private void getAllOrganizers(){

        mOrganizerApiInterface.getAllOrganizers(USER_TYPE).enqueue(new Callback<List<PersonModel>>() {
            @Override
            public void onResponse(Call<List<PersonModel>> call, Response<List<PersonModel>> response) {
                if(response.body() != null){
                    organizersList = response.body();
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
        Log.d(TAG, "filter: " + homeTabAdapter);
            homeTabAdapter.getFilter().filter(search);
            Log.d(TAG, "filter: " + search);

    }
}
