package com.e.maintabactivity.apiServises;

import com.e.maintabactivity.models.ReviewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReviewsApiInterface {

    @GET("reviews/{eventId}")
    Call<List<ReviewModel>> getAllReviewsByEventId(@Path("eventId") int id) ;

    @POST("reviews/{eventId}/add")
    Call<ReviewModel> addReview(@Path("eventId") int id, @Body ReviewModel reviewModel);

    // Update Review

    // Delete Review

}
