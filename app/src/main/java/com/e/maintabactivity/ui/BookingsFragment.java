package com.e.maintabactivity.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.maintabactivity.R;
import com.e.maintabactivity.adapters.UserBookingsAdapter;
import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.apiServises.UserApiInterface;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.models.BookingModel;
import com.e.maintabactivity.staticModels.StaticUserBookingModel;
import com.e.maintabactivity.utility.UserSharedPreference;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "BookingsFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private UserApiInterface userApiInterface;
    private MaterialTextView mNoBookings;

    public BookingsFragment() {
        // Required empty public constructor
    }

    public static BookingsFragment newInstance(String param1, String param2) {
        BookingsFragment fragment = new BookingsFragment();
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
        userApiInterface = RetrofitInstance.getRetrofitInstance().create(UserApiInterface.class);
        PersonModel loggedInUser = UserSharedPreference.getUser(getContext());
        Log.d(TAG, "onCreateView: " + loggedInUser);
        int userId = loggedInUser.getId();

        View view = inflater.inflate(R.layout.fragment_bookings, container, false);
        mNoBookings = view.findViewById(R.id.no_booking_message);

        mRecyclerView = view.findViewById(R.id.fragment_bookings_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        if(StaticUserBookingModel.allBookings == null) {
            getAllBookingsByUserId(userId);
        }else{
            mRecyclerView.setAdapter(new UserBookingsAdapter(getContext(), StaticUserBookingModel.allBookings));
        }
        return view;
    }

    private void getAllBookingsByUserId(final int id){
        userApiInterface.getBookingsByUserIde(id).enqueue(new Callback<List<BookingModel>>() {
            @Override
            public void onResponse(Call<List<BookingModel>> call, Response<List<BookingModel>> response) {
                if(response.body() != null && response.body().size() > 0){
                    mNoBookings.setVisibility(View.GONE);
                    StaticUserBookingModel.allBookings = response.body();
                    mRecyclerView.setAdapter(new UserBookingsAdapter(getContext(), StaticUserBookingModel.allBookings));
                }
            }

            @Override
            public void onFailure(Call<List<BookingModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }
}
