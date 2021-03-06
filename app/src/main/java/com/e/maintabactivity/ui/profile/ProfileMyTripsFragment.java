package com.e.maintabactivity.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.maintabactivity.R;
import com.e.maintabactivity.adapters.ProfileTripsAdapter;
import com.e.maintabactivity.apiServises.EventsApiInterface;
import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.apiServises.ReviewsApiInterface;
import com.e.maintabactivity.apiServises.UserApiInterface;
import com.e.maintabactivity.models.EventModel;
import com.e.maintabactivity.models.ReviewModel;
import com.e.maintabactivity.models.UserPortfolioEventModel;
import com.e.maintabactivity.organizer.adapters.OrganizerProfileTripsAdapter;
import com.e.maintabactivity.utility.UserSharedPreference;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileMyTripsFragment extends Fragment {

    private static final String TAG = "ProfileMyTripsFragment";


    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private EventsApiInterface mEventsApiInterface;

    private List<UserPortfolioEventModel> mMyEvents;
    private UserApiInterface mUserApiInterface;
    private ProfileMyTripsAdapter mProfileMyTripsAdapter;
    private MaterialTextView noTrips;

    private int mUserId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mEventsApiInterface = RetrofitInstance.getRetrofitInstance().create(EventsApiInterface.class);
        mUserApiInterface = RetrofitInstance.getRetrofitInstance().create(UserApiInterface.class);

        mUserId = UserSharedPreference.getUser(getContext()).getId();
        //getTripsByOrganizerId(mUserId);
        getAllReviewedEvents(mUserId);


        View view = inflater.inflate(R.layout.fragment_profile_my_trips, container, false);
        noTrips = view.findViewById(R.id.no_trips_message);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        recyclerView = view.findViewById(R.id.fragment_profile_my_trips_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        mProfileMyTripsAdapter = new ProfileMyTripsAdapter(getContext(), new ArrayList<UserPortfolioEventModel>());
        recyclerView.setAdapter(mProfileMyTripsAdapter);

        return view;
    }

    // ... anonymous ...
    private void getTripsByOrganizerId(int userId){
        mEventsApiInterface.getPortfolioEventsByUserId(userId).enqueue(new Callback<List<EventModel>>() {
            @Override
            public void onResponse(Call<List<EventModel>> call, Response<List<EventModel>> response) {
               /* if(response.body() != null){
                    List<UserPortfolioEventModel> events = response.body();
                    recyclerView.setAdapter(new ProfileMyTripsAdapter(getContext(), events));
                }
                */

                Log.d(TAG, "onResponse: " + response);
            }
            @Override
            public void onFailure(Call<List<EventModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void getAllPendingReviewEvents(int mUserId){
        mUserApiInterface.getPendingReviewedEvents(mUserId).enqueue(new Callback<List<UserPortfolioEventModel>>() {
            @Override
            public void onResponse(Call<List<UserPortfolioEventModel>> call, Response<List<UserPortfolioEventModel>> response) {
                if(response.body() != null && response.body().size() > 0 ){
                    mMyEvents = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<UserPortfolioEventModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: getAllPendingReviewEvents " + t.getMessage());
            }
        });
    }
    public void getAllReviewedEvents(final int mUserId){

        mUserApiInterface.getAllReviewedEvents(mUserId).enqueue(new Callback<List<UserPortfolioEventModel>>() {
            @Override
            public void onResponse(Call<List<UserPortfolioEventModel>> call, Response<List<UserPortfolioEventModel>> response) {
                getAllPendingReviewEvents(mUserId);
                if(response.body() != null && response.body().size() > 0){
                    if(mMyEvents != null)
                        mMyEvents.addAll(response.body());
                    else
                        mMyEvents = response.body();
                    noTrips.setVisibility(View.GONE);
                    mProfileMyTripsAdapter = new ProfileMyTripsAdapter(getContext(), mMyEvents);
                    recyclerView.setAdapter(mProfileMyTripsAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<UserPortfolioEventModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: getAllReviewedEvents " + t.getMessage());
            }
        });
    }

}
