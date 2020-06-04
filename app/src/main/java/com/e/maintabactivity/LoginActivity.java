package com.e.maintabactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.e.maintabactivity.apiServises.LoginApiInterface;
import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.models.LoginModel;
import com.e.maintabactivity.models.OrganizerModel;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.models.UserModel;
import com.e.maintabactivity.utility.UserSharedPreference;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private TextInputEditText mEmail;
    private TextInputEditText mPassword;
    private Button mLoginBtn;
    private Button mFbBtn;
    private SignInButton mGoogleBtn;
    private MaterialTextView mSinUpBtn;



    private LoginManager loginManager;
    private CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int SIGN_IN = 1;

    private String first_name = "";
    private String last_name = "";
    private String email = "";
    private String imageURL = null;
    private String userId = "";

    LoginApiInterface loginApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindView();
        if(isUserLoggedIn()){
            Log.d(TAG, "User Logged In: ");
            moveTo(MainActivity.class);
        }

        loginApiInterface = RetrofitInstance.getRetrofitInstance().create(LoginApiInterface.class);
        mFbBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                faceBookSignIn();
            }
        });

        // GOOGLE SIGN_IN
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        mGoogleBtn.setSize(SignInButton.SIZE_STANDARD);
        mGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        googleSignIn();
            }
        });


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = mEmail.getText().toString();
                String p = mPassword.getText().toString();
                LoginModel loginModel = new LoginModel(e, p);
                Toast.makeText(LoginActivity.this, e + p, Toast.LENGTH_SHORT).show();
                auth(loginModel);
                if(UserSharedPreference.getUser(LoginActivity.this) != null){
                    Log.d(TAG, "onClick: Moving to main activity");
                    moveTo(MainActivity.class);
                }
            }
        });

        mSinUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTo(SignUpActivity.class);
            }
        });
    }

    private boolean isUserLoggedIn(){
        Log.d(TAG, "isUserLoggedIn: " + UserSharedPreference.getUser(LoginActivity.this));
        return UserSharedPreference.getUser(LoginActivity.this) != null;
    }

    private void auth(final LoginModel loginModel){

        loginApiInterface.auth(loginModel).enqueue(new Callback<PersonModel>() {
            @Override
            public void onResponse(Call<PersonModel> call, Response<PersonModel> response) {
                PersonModel personModel = response.body();
                Log.d(TAG, "Login: "+ loginModel + response.body());
                if(personModel != null ){
                    UserSharedPreference.saveUser(LoginActivity.this,personModel);
                    Log.d(TAG, "User Logged in: ");
                }
            }

            @Override
            public void onFailure(Call<PersonModel> call, Throwable t) {
                Log.d(TAG, "onFailure: Login " + t.getMessage());
            }
        });
    }

    private void doesEmailExist(final String email){
        loginApiInterface.doesEmailExist(email).enqueue(new Callback<List<PersonModel>>() {
            @Override
            public void onResponse(Call<List<PersonModel>> call, Response<List<PersonModel>> response) {
                List<PersonModel> personModel = response.body();
                if(response.body() != null){
                    Log.d(TAG, "onResponse: email exists" + personModel.get(0));
                    UserSharedPreference.saveUser(LoginActivity.this, personModel.get(0));
                    Log.d(TAG, "onResponse: " + UserSharedPreference.getUser(LoginActivity.this));
                }else{
                    postUser(imageURL);
                }
            }

            @Override
            public void onFailure(Call<List<PersonModel>> call, Throwable t) {
                Log.d(TAG, " doesEmailExist onFailure: " + t.getMessage());

            }
        });
    }

    private void postUser(String image){
        PersonModel p = new PersonModel();
        p.setFirst_name(first_name);
        p.setLast_name(last_name);
        p.setEmail(email);
        p.setPassword("123");
        p.setPhone_no("03123456789");
        p.setImage(image);
        p.setUser_type(1);
        p.setUser(new UserModel());
        p.setOrganizer(new OrganizerModel());

        loginApiInterface.postUser(p).enqueue(new Callback<PersonModel>() {
            @Override
            public void onResponse(Call<PersonModel> call, Response<PersonModel> response) {
                PersonModel res = response.body();
                    // Shared preference
                UserSharedPreference.saveUser(LoginActivity.this, res);
                Log.d(TAG, "onResponse: User Posted" );
            }

            @Override
            public void onFailure(Call<PersonModel> call, Throwable t) {
                Log.d(TAG, "onFailure: PostUser " + t.getMessage());
            }
        });
    }

    private void getAllUsers(){
        Call<List<PersonModel>> users = loginApiInterface.getUsers();
        users.enqueue(new Callback<List<PersonModel>>() {
            @Override
            public void onResponse(Call<List<PersonModel>> call, Response<List<PersonModel>> response) {
                List<PersonModel> personModels = response.body();
                Log.d(TAG, "onResponse: " + personModels);

            }

            @Override
            public void onFailure(Call<List<PersonModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    // Login with Facebook
    public void faceBookSignIn(){
        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
        loginManager.logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                if (loginResult != null) {
                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            if (response.getError() != null) {
                                // handle error
                            } else {
                                try {
                                    userId = object.getString("id");
                                     email = object.getString("email");
                                     last_name = object.getString("last_name");
                                     first_name = object.getString("first_name");
                                    imageURL = "https://graph.facebook.com/" + userId + "/picture?type=large";
                                    Picasso.get().load(imageURL).into(target);
                                    Log.d(TAG, "ImageURL: " + imageURL);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });

                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,first_name,name,last_name,email");
                    request.setParameters(parameters);
                    request.executeAsync();
                    moveTo(MainActivity.class);
                }
            }

            @Override
            public void onCancel() {
                Log.e("bug", "facebook sign in connection cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("bug", "facebook sign in error" + error.toString());
            }
        });
    }

    // Login with Google
    public void googleSignIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_IN);

    }

    public void getGoogleUserData(){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
             first_name = acct.getDisplayName();
             email = acct.getEmail();

            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            if(personPhoto == null){
                Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                        R.drawable.icon_sample_profile);
                 imageURL = encodeBase64(bitmap);
                doesEmailExist(email);
            }else{
                imageURL = personPhoto.toString();
                Picasso.get().load(imageURL).into(target);
            }

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Log.d(TAG, "handleSignInResult: " + account);
            getGoogleUserData();
            moveTo(MainActivity.class);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    public void bindView(){
        mEmail    = findViewById(R.id.activity_login_email);
        mPassword = findViewById(R.id.activity_login_Password);
        mLoginBtn = findViewById(R.id.activity_login_btn_Sign_in);
        mFbBtn    =  findViewById(R.id.activity_login_fb_btn);
        mGoogleBtn= findViewById(R.id.activity_login_google_btn);
        mSinUpBtn = findViewById(R.id.activity_login_sign_up_btn);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void moveTo(Class c){
        Intent intent = new Intent(getBaseContext(), c);
        startActivity(intent);
    }

    private String encodeBase64(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            imageURL = encodeBase64(bitmap);
            if(UserSharedPreference.getUser(LoginActivity.this) == null){
                Toast.makeText(LoginActivity.this, "Saving new User", Toast.LENGTH_SHORT).show();
                doesEmailExist(email);
            }
        }

        @Override
        public void onBitmapFailed(Exception exception,Drawable errorDrawable) {
            Log.d(TAG, "onBitmapFailed: " , exception);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            Log.d(TAG, "onPrepareLoad: ");
        }
    };

}
