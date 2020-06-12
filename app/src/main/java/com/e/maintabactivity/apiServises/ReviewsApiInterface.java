package com.e.maintabactivity.apiServises;

import com.e.maintabactivity.models.ReviewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewsApiInterface {

    @GET("reviews/")
    Call<List<ReviewModel>> getAllReviewsByOrganizerId(@Query("organizer") int organizerId);

    // Add Review

    @POST("reviews/")
    Call<ReviewModel> addReview(@Body ReviewModel reviewModel);

    // Update Review
    @POST("reviews/{reviewId}")
    Call<ReviewModel> updateReview(@Path("reviewId") int did, @Body ReviewModel reviewModel);

    // Delete Review

}
