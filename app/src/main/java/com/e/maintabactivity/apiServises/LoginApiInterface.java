package com.e.maintabactivity.apiServises;

import com.e.maintabactivity.models.LoginModel;
import com.e.maintabactivity.models.PersonModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginApiInterface {

    // Get All users
    @GET("persons")
    Call<List<PersonModel>> getUsers();

    //Register (SignUp)
    @POST("persons/")
    Call<PersonModel> postUser(@Body PersonModel personModel);

    // Authenticating email
    @GET("persons/")
    Call<List<PersonModel>> doesEmailExist(@Query("email") String email);

    //Authenticating login
    @POST("persons/login/")
    Call<PersonModel> auth(@Body LoginModel loginModel);




}
