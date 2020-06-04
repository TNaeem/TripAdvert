package com.e.maintabactivity.organizer.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.e.maintabactivity.organizer.ImageSliderActivity;
import com.e.maintabactivity.organizer.adapters.OrganizerProfileGalleryAdapter;
import com.e.maintabactivity.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrganizerProfileGalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrganizerProfileGalleryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int organizerId;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridView mGridView;
    private OrganizerProfileGalleryAdapter mOrganizerProfileGalleryAdapter;


    List<String> mTripImages = new ArrayList<>();


    public OrganizerProfileGalleryFragment( int organizerId) {
        this.organizerId = organizerId;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrganizerProfileGalleryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrganizerProfileGalleryFragment newInstance(String param1, String param2) {
        OrganizerProfileGalleryFragment fragment = new OrganizerProfileGalleryFragment(organizerId);
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organizer_profile_gallery, container, false);

        mGridView = view.findViewById(R.id.fragment_organizer_profile_gallery_gridView);

        mOrganizerProfileGalleryAdapter = new OrganizerProfileGalleryAdapter(mTripImages, getContext());
        mGridView.setAdapter(mOrganizerProfileGalleryAdapter);
        Log.d("HELLO", "onCreateView: " + mGridView);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ImageSliderActivity.class);
                intent.putStringArrayListExtra("images", (ArrayList<String>) mTripImages);
                intent.putExtra("current", position);
                startActivity(intent);

            }
        });
        return view;
    }
}
