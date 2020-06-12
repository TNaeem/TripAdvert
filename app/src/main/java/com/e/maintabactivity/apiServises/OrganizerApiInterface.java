package com.e.maintabactivity.apiServises;

import com.e.maintabactivity.models.PersonModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrganizerApiInterface {


    // All organizers
    @GET("persons/")
    Call<List<PersonModel>> getAllOrganizers(@Query("user_type") int user_type);


    // Single organizer by id
    @GET("persons/{personId}")
    Call<PersonModel> getPersonById(@Path("personId") int id);

    // Get all images of events of a specific organizer

}
