
package com.e.maintabactivity.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.maintabactivity.R;

import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.services.UserServices;
import com.e.maintabactivity.ui.profile.ProfileViewPagerAdapter;
import com.e.maintabactivity.utility.UserSharedPreference;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "ProfileFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ProfileViewPagerAdapter mViewPagerAdapter;
    private Context mContext;
    private CircleImageView mProfileImage;
    private MaterialTextView mUsername;

    public ProfileFragment() {
        // Required empty public constructor
        mContext = getContext();
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Uploading Profile Image
        mProfileImage = view.findViewById(R.id.fragment_profile_image);
        mUsername = view.findViewById(R.id.fragment_profile_name);

        PersonModel personModel = UserSharedPreference.getUser(getContext());
        Log.d(TAG, "onCreateView: " + personModel.getUser() + " " + personModel.getImage());

        if(personModel.getImage() != null){
            boolean isImageOK = UserServices.verifyImage(personModel.getImage());
            if(isImageOK){
                Picasso.get().load(personModel.getImage()).into(mProfileImage);
            }else{
                Picasso.get().load(RetrofitInstance.BASE_URL+personModel.getImage()).into(mProfileImage);
            }
        }else{
            Picasso.get().load(R.drawable.icon_sample_profile).into(mProfileImage);
        }

        mUsername.setText(personModel.getFirst_name() + " " + personModel.getLast_name());

        // Setting tab layout
        mTabLayout        = view.findViewById(R.id.fragment_profile_tab_layout);
        mViewPager        = view.findViewById(R.id.fragment_profile_view_pager);
        FragmentManager fragmentManager = getChildFragmentManager();
        mViewPagerAdapter = new ProfileViewPagerAdapter(mContext, fragmentManager);

        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(0).setIcon(R.drawable.icon_profile);
        mTabLayout.getTabAt(1).setIcon(R.drawable.icon_bookings);

        return view;
    }



}
