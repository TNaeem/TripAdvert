package com.e.maintabactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.e.maintabactivity.adapters.FeaturedEventsAdapter;
import com.e.maintabactivity.R;
import com.google.android.material.textfield.TextInputEditText;

public class ChatActivity extends AppCompatActivity {

    private de.hdodenhof.circleimageview.CircleImageView mImage;
    private TextView mName;
    private TextInputEditText mMessage;
    private AppCompatImageButton mSendBtn;
    private RecyclerView mRecyclerView;
    private String[] messages = {"Hello", "How are you", "sjefwefse fosfoisfns fsvnsvnsl vljvwnvwl vwlvwnlnw"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        bindView();

        LinearLayoutManager featuredEventLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(featuredEventLinearLayoutManager);
        mRecyclerView.setAdapter(new FeaturedEventsAdapter(this, messages));

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = mMessage.getText().toString();
                if(msg.trim() != null){
                    messages[messages.length + 1] = msg;
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
