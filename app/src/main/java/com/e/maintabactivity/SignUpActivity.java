package com.e.maintabactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.e.maintabactivity.ui.HomeFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText mFirstName;
    private TextInputEditText mLastName;
    private TextInputEditText mEmail;
    private TextInputEditText mPassword;
    private TextInputEditText mAddress;
    private TextInputEditText mContact;
    private MaterialButton mSignUpBtn;
    private de.hdodenhof.circleimageview.CircleImageView mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        bindView();
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    void bindView(){
        mImage     = findViewById(R.id.activity_add_SignUp_imgPicture);
        mFirstName = findViewById(R.id.activity_signUp_first_name);
        mLastName  = findViewById(R.id.activity_signUp_last_name);
        mEmail     = findViewById(R.id.activity_signUp_email);
        mPassword  = findViewById(R.id.activity_signUp_password);
        mAddress   = findViewById(R.id.activity_signUp_password);
        mContact   = findViewById(R.id.activity_signUp_phone_number);
        mSignUpBtn = findViewById(R.id.activity_signUp_btn);
    }
}
