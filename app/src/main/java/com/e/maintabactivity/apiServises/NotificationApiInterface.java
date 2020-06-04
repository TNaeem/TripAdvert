package com.e.maintabactivity.apiServises;

import com.e.maintabactivity.models.NotificationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NotificationApiInterface {

    @GET("notifications/")
    Call<List<NotificationModel>> getAllNotifications();
}
