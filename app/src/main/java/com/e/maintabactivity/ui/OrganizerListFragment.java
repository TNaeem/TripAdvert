package com.e.maintabactivity.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.maintabactivity.R;
import com.e.maintabactivity.adapters.OrganizerListAdapter;
import com.e.maintabactivity.apiServises.OrganizerApiInterface;
import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.staticModels.StaticOrganizerModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrganizerListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "OrganizerListFragment";
    private static final int USER_TYPE = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;

    OrganizerApiInterface mOrganizerApiInterface;

    public OrganizerListFragment() {
        // Required empty public constructor
    }


    public static OrganizerListFragment newInstance(String param1, String param2) {
        OrganizerListFragment fragment = new OrganizerListFragment();
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
        View view = inflater.inflate(R.layout.fragment_organizer_list, container, false);

        mOrganizerApiInterface = RetrofitInstance.getRetrofitInstance().create(OrganizerApiInterface.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView = view.findViewById(R.id.fragment_organizer_list_resyclerView);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //
        if(StaticOrganizerModel.allOrganizers == null){
            getAllOrganizers();
        }else {
            mRecyclerView.setAdapter(new OrganizerListAdapter(getContext(), StaticOrganizerModel.allOrganizers));
        }

        //((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        return view;
    }

    private void getAllOrganizers(){
        Log.d(TAG, "getAllOrganizers: INSIDE");
        mOrganizerApiInterface.getAllOrganizers(USER_TYPE).enqueue(new Callback<List<PersonModel>>() {
            @Override
            public void onResponse(Call<List<PersonModel>> call, Response<List<PersonModel>> response) {
                if(response.body() != null){
                    StaticOrganizerModel.allOrganizers = response.body();
                    mRecyclerView.setAdapter(new OrganizerListAdapter(getContext(), StaticOrganizerModel.allOrganizers));
                }

                Log.d(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<List<PersonModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
