package com.e.maintabactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.e.maintabactivity.apiServises.LoginApiInterface;
import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.e.maintabactivity.apiServises.UserApiInterface;
import com.e.maintabactivity.models.OrganizerModel;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.models.UserModel;
import com.e.maintabactivity.ui.HomeFragment;
import com.e.maintabactivity.utility.UserSharedPreference;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingDeque;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {

    private static final String TAG = "UpdateProfileActivity";
    private static final int PICK_IMAGE = 1;

    private TextInputEditText mFirstName;
    private TextInputEditText mLastName;
    private TextInputEditText mAddress;
    private TextInputEditText mContact;
    private MaterialButton mSubmitBtn;
    private de.hdodenhof.circleimageview.CircleImageView mImage;

    private UserApiInterface userApiInterface;
    private PersonModel personModel;
    private UserModel user;
    Uri imageUrl;
    private Context mContext;



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

                personModel.setFirst_name(mFirstName.getText().toString());
                personModel.setLast_name(mLastName.getText().toString());

                personModel.setPhone_no(mContact.getText().toString());
                personModel.setUser_type(1);
                personModel.getUser().setAddress(mAddress.getText().toString());
                BitmapDrawable drawable = (BitmapDrawable) mImage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                personModel.setImage(encodeBase64(bitmap));
                updateUser();

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
                String img = encodeBase64(bitmap);
                personModel.setImage(img);
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
        Picasso.get().load(p.getImage()).into(mImage);
        mFirstName.setText(p.getFirst_name());
        mLastName.setText(p.getLast_name());
        mContact.setText(p.getPhone_no());
        mAddress.setText(p.getUser().getAddress());
    }


    private void updateUser(){
        userApiInterface.updateUser(personModel.getId(), personModel).enqueue(new Callback<PersonModel>() {
            @Override
            public void onResponse(Call<PersonModel> call, Response<PersonModel> response) {
                if(response.body() != null){
                    UserSharedPreference.removeUser(mContext);
                    UserSharedPreference.saveUser(mContext, response.body());
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


}
