package com.e.maintabactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText mEmail;
    private TextInputEditText mPassword;
    private Button mLoginBtn;
    private de.hdodenhof.circleimageview.CircleImageView mFbBtn;
    private de.hdodenhof.circleimageview.CircleImageView mGoogleBtn;
    private MaterialTextView mSinUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindView();
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mSinUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void bindView(){
        mEmail    = findViewById(R.id.activity_login_email);
        mPassword = findViewById(R.id.activity_login_Password);
        mLoginBtn = findViewById(R.id.activity_login_btn_Sign_in);
        mFbBtn    = findViewById(R.id.activity_login_fb_btn);
        mGoogleBtn= findViewById(R.id.activity_login_google_btn);
        mSinUpBtn = findViewById(R.id.activity_login_sign_up_btn);
    }



}
