package com.e.maintabactivity.apiServises;

import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.models.UserBookingModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApiInterface {

    //Update Profile
    @PUT("users/update/{personId}")
    Call<PersonModel> updateUser(@Path("personId") int id, @Body PersonModel personModel);

    // All bookings by a specific user
    @GET("user-bookings")
    Call<List<UserBookingModel>> getBookingsByUserIde(@Query("user") int id);
    // Single booking by a specific user

}
