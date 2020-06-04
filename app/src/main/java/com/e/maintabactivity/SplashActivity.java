package com.e.maintabactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.e.maintabactivity.utility.UserSharedPreference;

public class SplashActivity extends AppCompatActivity {

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = SplashActivity.this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(UserSharedPreference.getUser(mContext) != null){
                     intent = new Intent(mContext, MainActivity.class);

                }else{
                    intent = new Intent(mContext, LoginActivity.class);
                }
                startActivity(intent);


                SplashActivity.this.finish();

            }
        }, 3000);
    }
}
