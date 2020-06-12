package com.e.maintabactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.Toast;

import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.apiServises.UserApiInterface;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.models.UserModel;
import com.e.maintabactivity.services.UserServices;
import com.e.maintabactivity.utility.UserSharedPreference;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity implements Validator.ValidationListener{

    private static final String TAG = "UpdateProfileActivity";
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
    @Length(sequence = 2, min = 3, max = 50, message = "Address must be from 3 to 30 characters in length")
    @Pattern(sequence = 3, regex = "^[A-Za-z0-9_\\-\\#]+$", message = "Address can only contain A-Z a-z 0-9 # - _")
    private TextInputEditText mAddress;

    @NotEmpty(sequence = 1, trim = true, message = "Required")
    @Length(sequence = 2, min = 11, max = 11, message = "Contact must be 11 characters in length")
    @Pattern(sequence = 3, regex = "^[0-9]+$", message = "Contact can only contain 0-9")
    private TextInputEditText mContact;

    private MaterialButton mSubmitBtn;
    private de.hdodenhof.circleimageview.CircleImageView mImage;

    private UserApiInterface userApiInterface;
    private PersonModel personModel;
    private UserModel user;
    Uri imageUrl;
    private Context mContext;
    private Validator mValidator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = UpdateProfileActivity.this;
        setContentView(R.layout.activity_update_profile);

        personModel = UserSharedPreference.getUser(mContext);
        bindView();
        setExistingDetails(personModel);

        userApiInterface = RetrofitInstance.getRetrofitInstance().create(UserApiInterface.class);

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Image"), PICK_IMAGE);
            }
        });


        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
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
                //String img = encodeBase64(bitmap);
                //personModel.setImage(img);
            }catch(IOException e){
                e.printStackTrace();
            }
        }


    }


    void bindView(){
        mImage     = findViewById(R.id.activity_update_profile_imgPicture);
        mFirstName = findViewById(R.id.activity_update_profile_first_name);
        mLastName  = findViewById(R.id.activity_update_profile_last_name);
        mAddress   = findViewById(R.id.activity_update_profile_address);
        mContact   = findViewById(R.id.activity_update_profile_phone_number);
        mSubmitBtn = findViewById(R.id.activity_update_profile_btn);
    }

    void setExistingDetails(PersonModel p){
        Log.d(TAG, "setExistingDetails: " + p.getImage());
        if(p.getImage() != null){
            boolean isImageOK = UserServices.verifyImage(personModel.getImage());
            if(isImageOK){
                Picasso.get().load(p.getImage()).into(mImage);
            }else{
                Picasso.get().load(RetrofitInstance.BASE_URL+personModel.getImage()).into(mImage);
            }
        }else{
            Picasso.get().load(R.drawable.person_image).into(mImage);
        }

        mFirstName.setText(p.getFirst_name());
        mLastName.setText(p.getLast_name());
        mContact.setText(p.getPhone_no());
        mAddress.setText(p.getUser().getAddress());
    }


    private void updateUser(Map<String, String> map){
        userApiInterface.updateUser(personModel.getId(), map).enqueue(new Callback<PersonModel>() {
            @Override
            public void onResponse(Call<PersonModel> call, Response<PersonModel> response) {
                if(response.body() != null){
                    UserSharedPreference.removeUser(mContext);
                     personModel = response.body();
                     UserSharedPreference.saveUser(mContext, personModel);


                    //UserSharedPreference.saveUser(mContext, response.body());
                    Log.d(TAG, "onResponse: User Updated "  + response.body());
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                }
                Log.d(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<PersonModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }




    private String encodeBase64(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }


    @Override
    public void onValidationSucceeded() {
        /*
        personModel.setFirst_name(mFirstName.getText().toString());
        personModel.setLast_name(mLastName.getText().toString());

        personModel.setPhone_no(mContact.getText().toString());
        personModel.setUser_type(1);
        personModel.getUser().setAddress(mAddress.getText().toString());

        personModel.setImage(encodeBase64(bitmap));
         */
        BitmapDrawable drawable = (BitmapDrawable) mImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();


        Map<String, String> map = new HashMap<String, String>();
        map.put("first_name", mFirstName.getText().toString());
        map.put("last_name", mLastName.getText().toString());
        map.put("image", encodeBase64(bitmap));
        map.put("user_type", "1");
        map.put("address", mAddress.getText().toString());
        map.put("phone_no", mContact.getText().toString());

        updateUser(map);

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
