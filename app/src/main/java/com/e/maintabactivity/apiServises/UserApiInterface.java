package com.e.maintabactivity.apiServises;

import com.e.maintabactivity.models.EventModel;
import com.e.maintabactivity.models.NotificationModel;
import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.models.BookingModel;
import com.e.maintabactivity.models.UserPortfolioEventModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApiInterface {

    //Update Profile
    @PUT("users/update/{personId}")
    Call<PersonModel> updateUser(@Path("personId") int id, @Body PersonModel personModel);

    // All bookings by a specific user
    @GET("user-bookings")
    Call<List<BookingModel>> getBookingsByUserIde(@Query("user") int id);

    // Add booking
    @POST("")
    Call<Void> addBooking(@Body BookingModel bookingModel);


    //@GET("reviews/{userId}")
    @GET("events/pending/{userId}")
    Call<List<UserPortfolioEventModel>> getPendingReviewedEvents(@Path("userId") int id) ;

    @GET("events/reviewed/{userId}")
    Call<List<UserPortfolioEventModel>> getAllReviewedEvents(@Path("userId") int id);

    @GET("notifications")
    Call<List<NotificationModel>> getAllNotifications(@Query("sentFor") int id);

    @POST("persons/set_firebase_token/{userId}")
    Call<PersonModel> postFirebaseInstanceId(@Path("userId") int userId, @Body String firebase_token);




}
