package com.e.maintabactivity.organizer.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.e.maintabactivity.ChatActivity;
import com.e.maintabactivity.PrivateTripActivity;
import com.e.maintabactivity.R;
import com.e.maintabactivity.models.OrganizerModel;
import com.e.maintabactivity.models.PersonModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrganizerProfileAboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrganizerProfileAboutFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "OrganizerProfileAboutFr";
    private static PersonModel personModel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MaterialButton mPrivateEventBtn;
    private MaterialTextView mEmail;
    private MaterialTextView mCompanyName;
    private MaterialTextView mContact;
    private MaterialTextView mAddress;
    private ImageView mIsVerified;
    private MaterialButton mChatBtn;


    public OrganizerProfileAboutFragment(PersonModel personModel) {
         this.personModel = personModel;

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrganizerProfileAboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrganizerProfileAboutFragment newInstance(String param1, String param2) {

        OrganizerProfileAboutFragment fragment = new OrganizerProfileAboutFragment(personModel);
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
        View view = inflater.inflate(R.layout.fragment_organizer_profile_about, container, false);

        Log.d(TAG, "onCreateView: while inflating" + personModel.getId());
        mCompanyName = view.findViewById(R.id.fragment_organizer_profile_about_org);
        mCompanyName.setText(personModel.getOrganizer().getOrganization());
        mAddress = view.findViewById(R.id.fragment_organizer_profile_about_address);
        mAddress.setText(personModel.getOrganizer().getAddress());
        mContact = view.findViewById(R.id.fragment_organizer_profile_about_contact);
        mContact.setText(personModel.getPhone_no());
        mEmail = view.findViewById(R.id.fragment_organizer_profile_about_emial);
        mEmail.setText(personModel.getEmail());
        mIsVerified = view.findViewById(R.id.layout_organizer_card_isVerified);


        mChatBtn = view.findViewById(R.id.fragment_organizer_profile_about_btn_chat);
        mChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + personModel.getId() );
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("userId", personModel.getId());
                intent.putExtra("userName", personModel.getFirst_name() + " " + personModel.getLast_name());
                //add contact in db if not available
                startActivity(intent);
            }
        });
        mPrivateEventBtn = view.findViewById(R.id.fragment_organizer_profile_about_btn_private_event);
        mPrivateEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PrivateTripActivity.class);
                intent.putExtra("UserId", personModel.getId());
                startActivity(intent);
            }
        });

        return view;
    }
}
