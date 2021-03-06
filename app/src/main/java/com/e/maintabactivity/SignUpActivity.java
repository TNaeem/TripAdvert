package com.e.maintabactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.Toast;

import com.e.maintabactivity.apiServises.LoginApiInterface;
import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.apiServises.UserApiInterface;
import com.e.maintabactivity.models.OrganizerModel;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.models.UserModel;
import com.e.maintabactivity.ui.HomeFragment;
import com.e.maintabactivity.utility.UserSharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements Validator.ValidationListener {

    private static final String TAG = "SignUpActivity";
    private static final int PICK_IMAGE = 1;

    @NotEmpty(sequence = 1, trim = true, message = "Required")
    @Length(sequence = 2, min = 3, max = 30, message = "First Name must be from 3 to 30 characters in length")
    @Pattern(sequence = 3, regex = "^[a-zA-Z ]*$", message = "First Name can only contain alphabets and spaces")
    private TextInputEditText mFirstName;

    @NotEmpty(sequence = 1, trim = true, message = "Required")
    @Length(sequence = 2, min = 3, max = 30, message = "Last Name must be from 3 to 30 characters in length")
    @Pattern(sequence = 3, regex = "^[a-zA-Z ]*$", message = "Last Name can only contain alphabets and spaces")
    private TextInputEditText mLastName;

    @NotEmpty(sequence = 1, trim = true, message = "Required")
    @Length(sequence = 2, min = 3, max = 30, message = "Email must be from 3 to 30 characters in length")
    @Pattern(sequence = 3, regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Enter valid email")
    private TextInputEditText mEmail;


    @NotEmpty(sequence = 1, trim = true, message = "Required")
    @Length(sequence = 2, min = 3, max = 30, message = "Password must be from 3 to 30 characters in length")
    @Pattern(sequence = 3, regex = "^[a-z0-9_\\-\\.]+$", message = "Password can only contain a-z 0-9 . - _")
    private TextInputEditText mPassword;

    @NotEmpty(sequence = 1, trim = true, message = "Required")
    @Length(sequence = 2, min = 3, max = 50, message = "Address must be from 3 to 30 characters in length")
    @Pattern(sequence = 3, regex = "^[A-Za-z0-9_\\-\\#]+$", message = "Address can only contain A-Z a-z 0-9 # - _")
    private TextInputEditText mAddress;

    @NotEmpty(sequence = 1, trim = true, message = "Required")
    @Length(sequence = 2, min = 11, max = 11, message = "Contact must be 11 characters in length")
    @Pattern(sequence = 3, regex = "^[0-9]+$", message = "Contact can only contain 0-9")
    private TextInputEditText mContact;

    private MaterialButton mSignUpBtn;
    private de.hdodenhof.circleimageview.CircleImageView mImage;

    LoginApiInterface loginApiInterface;
    UserApiInterface userApiInterface;
    String firebaseInstanceToken;
    private PersonModel personModel;
    private UserModel user;
    Uri imageUrl;
    private Context context = this;
    private Validator mValidator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        personModel = new PersonModel();
        user = new UserModel();

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            //Checking app registration
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(!task .isSuccessful()){
                    Log.i(TAG, "Task Failed");
                    return;
                }
                firebaseInstanceToken = task.getResult().getToken();

            }
        });

        loginApiInterface = RetrofitInstance.getRetrofitInstance().create(LoginApiInterface.class);
        userApiInterface = RetrofitInstance.getRetrofitInstance().create(UserApiInterface.class);
        bindView();



        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Image"), PICK_IMAGE);
            }
        });


        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mValidator.validate();

            }
        });


        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setViewValidatedAction(new Validator.ViewValidatedAction() {
            @Override
            public void onAllRulesPassed(View view) {
                ((TextInputLayout) view.getParent().getParent()).setError(null);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            Uri imageUrl = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUrl);
                mImage.setImageBitmap(bitmap);
                String img =  encodeBase64(bitmap);
                personModel.setImage(img);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    void bindView(){
        mImage     = findViewById(R.id.activity_add_SignUp_imgPicture);
        mFirstName = findViewById(R.id.activity_signUp_first_name);
        mLastName  = findViewById(R.id.activity_signUp_last_name);
        mEmail     = findViewById(R.id.activity_signUp_email);
        mPassword  = findViewById(R.id.activity_signUp_password);
        mAddress   = findViewById(R.id.activity_signUp_address);
        mContact   = findViewById(R.id.activity_signUp_phone_number);
        mSignUpBtn = findViewById(R.id.activity_signUp_btn);
    }


    private void doesEmailExist(final String email){
        loginApiInterface.doesEmailExist(email).enqueue(new Callback<List<PersonModel>>() {
            @Override
            public void onResponse(Call<List<PersonModel>> call, Response<List<PersonModel>> response) {
                List<PersonModel> p = response.body();
                if(response.body() != null && response.body().size() > 0){
                    Log.d(TAG, "onResponse: email exists" + response.body().size());
                    UserSharedPreference.saveUser(SignUpActivity.this, p.get(0));
                }else{
                    postUser(personModel);
                }
            }

            @Override
            public void onFailure(Call<List<PersonModel>> call, Throwable t) {
                Log.d(TAG, " doesEmailExist onFailure: " + t.getMessage());

            }
        });
    }

    private void postUser(PersonModel personModel){

        personModel.setUser_type(1);

        loginApiInterface.postUser(personModel).enqueue(new Callback<PersonModel>() {
            @Override
            public void onResponse(Call<PersonModel> call, Response<PersonModel> response) {
                PersonModel res = response.body();
                // Shared preference
                UserSharedPreference.saveUser(SignUpActivity.this, res);
                Log.d(TAG, "onResponse: user added " + res);
                startActivity(new Intent(context, MainActivity.class));

            }

            @Override
            public void onFailure(Call<PersonModel> call, Throwable t) {
                Log.d(TAG, "onFailure: PostUser " + t.getMessage());
            }
        });
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

            String imageString = encodeBase64(bitmap);
            personModel.setImage(imageString);

            if(UserSharedPreference.getUser(SignUpActivity.this) == null){
                Toast.makeText(SignUpActivity.this, "Saving new User", Toast.LENGTH_SHORT).show();
                doesEmailExist(personModel.getEmail());
            }
        }

        @Override
        public void onBitmapFailed(Exception exception, Drawable errorDrawable) {
            Log.d(TAG, "onBitmapFailed: " , exception);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            Log.d(TAG, "onPrepareLoad: ");
        }
    };

    @Override
    public void onValidationSucceeded() {
        personModel.setFirst_name(mFirstName.getText().toString());
        personModel.setLast_name(mLastName.getText().toString());
        personModel.setEmail(mEmail.getText().toString());
        personModel.setPassword(mPassword.getText().toString());
        personModel.setPhone_no(mContact.getText().toString());
        personModel.setUser_type(1);
        user.setAddress(mAddress.getText().toString());
        personModel.setUser(user);
        personModel.setOrganizer(new OrganizerModel());
        doesEmailExist(personModel.getEmail());
        if(UserSharedPreference.getUser(SignUpActivity.this) == null){
            postUser(personModel);
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            ViewParent view = error.getView().getParent().getParent();
            String message = error.getFailedRules().get(0).getMessage(this);

            if (view instanceof TextInputLayout) {
                ((TextInputLayout) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
