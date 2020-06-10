package com.e.maintabactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.e.maintabactivity.utility.UserSharedPreference;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.card.MaterialCardView;

public class SettingsActivity extends AppCompatActivity {

    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        MaterialCardView logoutCardView = findViewById(R.id.logout);
        logoutCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSharedPreference.removeUser(context);
                Log.d("TAG", " User after logout " + UserSharedPreference.getUser(context));

                LoginManager.getInstance().logOut();
                Toast.makeText(context, "Logout", Toast.LENGTH_SHORT).show();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

                GoogleSignInClient googleSignInClient  = GoogleSignIn.getClient(context, gso);
                googleSignInClient.signOut();


                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}
