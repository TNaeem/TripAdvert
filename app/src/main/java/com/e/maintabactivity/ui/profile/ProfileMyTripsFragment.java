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
import com.e.maintabactivity.apiServises.UserApiInterface;
import com.e.maintabactivity.models.EventModel;
import com.e.maintabactivity.organizer.adapters.OrganizerProfileTripsAdapter;
import com.e.maintabactivity.utility.UserSharedPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileMyTripsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "ProfileMyTripsFragment";


    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private EventsApiInterface mEventsApiInterface;


    public ProfileMyTripsFragment() {
        // Required empty public constructor
    }


    public static ProfileMyTripsFragment newInstance(String param1, String param2) {
        ProfileMyTripsFragment fragment = new ProfileMyTripsFragment();
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
        // Inflate the layout for this fragment
        mEventsApiInterface = RetrofitInstance.getRetrofitInstance().create(EventsApiInterface.class);
        int userId = UserSharedPreference.getUser(getContext()).getId();
        getTripsByOrganizerId(userId);

        View view = inflater.inflate(R.layout.fragment_profile_my_trips, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        recyclerView = view.findViewById(R.id.fragment_profile_my_trips_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    private void getTripsByOrganizerId(int userId){
        mEventsApiInterface.getPortfolioEventsByUserId(userId).enqueue(new Callback<List<EventModel>>() {
            @Override
            public void onResponse(Call<List<EventModel>> call, Response<List<EventModel>> response) {
                if(response.body() != null){
                    List<EventModel> events = response.body();
                    recyclerView.setAdapter(new ProfileTripsAdapter(getContext(), events));
                }
                Log.d(TAG, "onResponse: " + response);
            }
            @Override
            public void onFailure(Call<List<EventModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
