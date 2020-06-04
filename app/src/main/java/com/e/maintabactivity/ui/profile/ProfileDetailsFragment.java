package com.e.maintabactivity.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.maintabactivity.R;
import com.e.maintabactivity.SignUpActivity;
import com.e.maintabactivity.UpdateProfileActivity;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.utility.UserSharedPreference;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Context mContext;
    private String mParam1;
    private String mParam2;
    private MaterialButton mEditProfileBtn;
    private MaterialTextView mEmail;
    private MaterialTextView mName;
    private MaterialTextView mAddress;
    private MaterialTextView mContact;



    public ProfileDetailsFragment() {

        // Required empty public constructor
    }


    public static ProfileDetailsFragment newInstance(String param1, String param2) {
        ProfileDetailsFragment fragment = new ProfileDetailsFragment();
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

        View view = inflater.inflate(R.layout.fragment_profile_details, container, false);

        PersonModel personModel = UserSharedPreference.getUser(getContext());



        mEmail = view.findViewById(R.id.fragment_profile_details_email);
        mName = view.findViewById(R.id.fragment_profile_detail_name);
        mAddress = view.findViewById(R.id.fragment_profile_details_address);
        mContact = view.findViewById(R.id.fragment_profile_details_contact);
        mEditProfileBtn = view.findViewById(R.id.fragment_profile_details_edit_profile);
        mEditProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateProfileActivity.class);
                getActivity().startActivity(intent);
            }
        });

        if(personModel != null){
            mEmail.setText(personModel.getEmail());
            mName.setText(personModel.getFirst_name() + " " + personModel.getLast_name());
            mAddress.setText(personModel.getUser().getAddress());
            mContact.setText(personModel.getPhone_no());
        }

        return view;
    }


}
