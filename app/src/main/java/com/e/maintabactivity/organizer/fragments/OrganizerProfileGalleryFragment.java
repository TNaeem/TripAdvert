package com.e.maintabactivity.organizer.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.maintabactivity.organizer.adapters.OrganizerProfileGalleryAdapter;
import com.e.maintabactivity.R;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    int[] trips = {R.drawable.trip_location, R.drawable.person_image, R.drawable.person_image,
                    R.drawable.trip_location, R.drawable.person_image,R.drawable.trip_location, R.drawable.person_image, R.drawable.person_image,
                    R.drawable.trip_location, R.drawable.person_image,R.drawable.trip_location, R.drawable.person_image, R.drawable.person_image,
                    R.drawable.trip_location, R.drawable.person_image};


    public OrganizerProfileGalleryFragment() {
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
        OrganizerProfileGalleryFragment fragment = new OrganizerProfileGalleryFragment();
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
        View view = inflater.inflate(R.layout.fragment_organizer_profile_gallery, container, false);

        recyclerView = view.findViewById(R.id.fragment_organizer_profile_gallery_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager( getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new OrganizerProfileGalleryAdapter(getContext(), trips));
        return view;
    }
}
