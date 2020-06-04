package com.e.maintabactivity.organizer.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.maintabactivity.apiServises.EventsApiInterface;
import com.e.maintabactivity.apiServises.OrganizerApiInterface;
import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.models.EventModel;
import com.e.maintabactivity.organizer.adapters.OrganizerProfileTripsAdapter;
import com.e.maintabactivity.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrganizerProfileTripsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrganizerProfileTripsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int organizerId;
    private static final String TAG = "OrganizerProfileTripsFr";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    String[] trips = {"Title 1", "Title 2"} ;
    private EventsApiInterface mEventsApiInterface;

    public OrganizerProfileTripsFragment(int organizerId) {
        this.organizerId = organizerId;
        // Required empty public constructor
    }


    public static OrganizerProfileTripsFragment newInstance(String param1, String param2) {

        OrganizerProfileTripsFragment fragment = new OrganizerProfileTripsFragment(organizerId);
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
        View view = inflater.inflate(R.layout.fragment_organizer_profile_trips, container, false);

        mEventsApiInterface = RetrofitInstance.getRetrofitInstance().create(EventsApiInterface.class);
        getTripsByOrganizerId();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        recyclerView = view.findViewById(R.id.fragment_organizer_profile_trips_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    private void getTripsByOrganizerId(){
        mEventsApiInterface.getPortfolioEventsByOrganizerId(organizerId).enqueue(new Callback<List<EventModel>>() {
            @Override
            public void onResponse(Call<List<EventModel>> call, Response<List<EventModel>> response) {
                if(response.body() != null){
                    List<EventModel> events = response.body();
                    recyclerView.setAdapter(new OrganizerProfileTripsAdapter(getContext(), events));
                }
                Log.d(TAG, "onResponse: " + response);

            }

            @Override
            public void onFailure(Call<List<EventModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage() + " Org Id " + organizerId);
            }
        });


    }
}
