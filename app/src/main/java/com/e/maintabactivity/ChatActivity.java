package com.e.maintabactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.e.maintabactivity.adapters.ChatMessagesAdapter;
import com.e.maintabactivity.models.ContactModel;
import com.e.maintabactivity.models.MessageModel;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.staticModels.StaticOrganizerModel;
import com.e.maintabactivity.utility.UserSharedPreference;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";
    private Context context = this;
    
    private de.hdodenhof.circleimageview.CircleImageView mImage;
    private TextView mName;
    private EditText mMessage;
    private AppCompatImageButton mSendBtn;
    private RecyclerView mRecyclerView;
    private ChatMessagesAdapter chatMessagesAdapter;
    private List<MessageModel> mChatMessages = new ArrayList<MessageModel>();
    private int minId;
    private int maxId;
    private int myId;
    private int receiverId;
    private DatabaseReference myRef;
    private String senderName;
    private String receiverName;

    private CircleImageView mUserImage;
    private MaterialTextView mUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        bindView();

        receiverId = getIntent().getIntExtra("userId", 0);
        Log.d(TAG, "onCreate: " + receiverId);
        receiverName = getIntent().getStringExtra("userName");
        myId = UserSharedPreference.getUser(context).getId();
        senderName = UserSharedPreference.getUser(context).getFirst_name();
        PersonModel organizer = StaticOrganizerModel.getOrganizer(receiverId);
        Log.d(TAG, "onCreate: " + receiverId + " " + organizer);

        minId = Math.min(receiverId, myId);
        maxId = Math.max(receiverId, myId);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("messages");

        // Read from the database
        myRef.child((minId +  ":" + maxId)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<MessageModel> list = new ArrayList<>();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    list.add(childDataSnapshot.getValue(MessageModel.class));
                }
                chatMessagesAdapter.setmMessages(list);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        Picasso.get().load(organizer.getImage()).into(mImage);
        mName.setText(receiverName);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        chatMessagesAdapter = new ChatMessagesAdapter(this, mChatMessages);
        mRecyclerView.setAdapter(chatMessagesAdapter);

        // sending message
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = mMessage.getText().toString();
                if(msg.trim() != null){
                    MessageModel messageModel = new MessageModel(myId, receiverId, msg, new Date(), senderName, receiverName);
                    myRef.child(minId +  ":" + maxId).push().setValue(messageModel, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            //
                        }
                    });
                    mMessage.setText("");
                }

            }
        });
    }

    public void bindView(){
        mImage        = findViewById(R.id.activity_chat_image);
        mName         = findViewById(R.id.activity_chat_person_name);
        mRecyclerView = findViewById(R.id.activity_chat_recycler_view);
        mMessage      = findViewById(R.id.activity_chat_message_text);
        mSendBtn      = findViewById(R.id.activity_chat_send_btn);

    }
}
