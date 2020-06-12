package com.e.maintabactivity.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.e.maintabactivity.ChatActivity;
import com.e.maintabactivity.R;
import com.e.maintabactivity.adapters.ChatFragmentAdapter;
import com.e.maintabactivity.models.ContactModel;
import com.e.maintabactivity.models.MessageModel;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.utility.UserSharedPreference;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ChatFragment extends Fragment {
    private static final String TAG = "ChatFragment";

    // TODO: Rename and change types of parameters
    private DatabaseReference myRef;

    private int myId;
    private RecyclerView mRecyclerView;
    private ChatFragmentAdapter chatFragmentAdapter;
    private MaterialTextView noChats;

    private List<ContactModel> mContacts = new ArrayList<ContactModel>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myId = UserSharedPreference.getUser(getContext()).getId();


        // Firebase
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("contacts");

        // Read from the database
        myRef.child(String.valueOf(myId)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<ContactModel> contactModelList = new ArrayList<ContactModel>();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    contactModelList.add(childDataSnapshot.getValue(ContactModel.class));
                }
                Log.d(TAG, "onDataChange: " + contactModelList.size());
                noChats.setVisibility(View.GONE);
                chatFragmentAdapter.setContacts(contactModelList);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        noChats = view.findViewById(R.id.no_contacts_message);
        mRecyclerView = view.findViewById(R.id.fragment_chat_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        chatFragmentAdapter = new ChatFragmentAdapter(this.getContext(), new ArrayList<ContactModel>());

        mRecyclerView.setAdapter(chatFragmentAdapter);

        return view;
    }
}
