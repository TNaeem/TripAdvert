package com.e.maintabactivity.organizer.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.maintabactivity.R;
import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.apiServises.ReviewsApiInterface;
import com.e.maintabactivity.models.ReviewModel;
import com.e.maintabactivity.organizer.adapters.OrganizerReviewsAdapter;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "ReviewsFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int mPersonId;
    private ReviewsApiInterface mReviewsApiInterface;
    private RecyclerView mRecyclerView;
    private OrganizerReviewsAdapter mOrganizerReviewsAdapter;
    private List<ReviewModel> allReviews;
    private MaterialTextView noReviews;

    public ReviewsFragment(int mPersonId) {
       this.mPersonId = mPersonId;
    }

    public static ReviewsFragment newInstance(String param1, String param2, int mPersonId) {
        ReviewsFragment fragment = new ReviewsFragment(mPersonId);
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
        mReviewsApiInterface = RetrofitInstance.getRetrofitInstance().create(ReviewsApiInterface.class);
        getALlReviews(mPersonId);

        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        noReviews  = view.findViewById(R.id.no_reviews_message);

        mRecyclerView = view.findViewById(R.id.fragment_review_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mOrganizerReviewsAdapter = new OrganizerReviewsAdapter(getContext(), new ArrayList<ReviewModel>(), mPersonId);
        mRecyclerView.setAdapter(mOrganizerReviewsAdapter);

        return view;
    }

    public void getALlReviews(final int mPersonId){
        mReviewsApiInterface.getAllReviewsByOrganizerId(mPersonId).enqueue(new Callback<List<ReviewModel>>() {
            @Override
            public void onResponse(Call<List<ReviewModel>> call, Response<List<ReviewModel>> response) {
                Log.d(TAG, "onResponse: " + response);
                if(response.body() != null && response.body().size() > 0){
                    noReviews.setVisibility(View.GONE);
                    allReviews = response.body();
                    mOrganizerReviewsAdapter = new OrganizerReviewsAdapter(getContext(), allReviews, mPersonId);
                    mRecyclerView.setAdapter(mOrganizerReviewsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<ReviewModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


}
