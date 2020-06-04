package com.e.maintabactivity.apiServises;

import com.e.maintabactivity.models.ImageModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EventImagesApiInterface {

    @GET("images")
    Call<List<ImageModel>> getImagesByEventId(@Query("event") int id);
}
