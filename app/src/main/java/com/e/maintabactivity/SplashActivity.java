package com.e.maintabactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.e.maintabactivity.apiServises.OrganizerApiInterface;
import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.staticModels.StaticUserModel;
import com.e.maintabactivity.utility.NotificationUtils;
import com.e.maintabactivity.utility.UserSharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    private Context mContext = this;
    OrganizerApiInterface organizerApiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Setting Api Interface
        organizerApiInterface = RetrofitInstance.getRetrofitInstance().create(OrganizerApiInterface.class);
        getAllUsers();

        // Setting Firebase Notification Instance
        NotificationUtils.createNotificationChannel(this);

        FirebaseMessaging.getInstance().subscribeToTopic("topic");
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                Log.d(TAG, "onSuccess: " + instanceIdResult.getToken());
            }
        });

        // Moving to Main/Login Activity
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

    private void getAllUsers(){

        organizerApiInterface.getAllOrganizers(1).enqueue(new Callback<List<PersonModel>>() {
            @Override
            public void onResponse(Call<List<PersonModel>> call, Response<List<PersonModel>> response) {
                if(response.body() != null){
                    Log.d(TAG, "onResponse: calling organizers " + response.body().size());
                    StaticUserModel.allUsers = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<PersonModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: organizer" + t.getMessage());
            }
        });
    }
}
