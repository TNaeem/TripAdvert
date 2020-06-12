package com.e.maintabactivity.apiServises;

import com.e.maintabactivity.models.EventModel;
import com.e.maintabactivity.models.EventModelResponse;
import com.e.maintabactivity.models.NewEventModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EventsApiInterface {

    @GET("event/")
    Call<List<NewEventModel>> getAllEvents();
    // Events get karty hue unky organizers kese any hen?


    @POST("portfolio/")
    Call<EventModel> addPrivateEvent(@Body EventModel eventModel);

    @GET("portfolio")
    Call<List<EventModel>> getPortfolioEventsByOrganizerId(@Query("organizer") int id);

    @GET("portfolio")
    Call<List<EventModel>> getPortfolioEventsByUserId(@Query("user") int id);

}
