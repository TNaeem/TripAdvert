package com.e.maintabactivity.models;

import com.google.gson.annotations.SerializedName;

public class UserPortfolioEventModel {

    @SerializedName("event")
    EventModel event;

    @SerializedName("rating")
    int rating;

    public EventModel getEvent() {
        return event;
    }

    public int getRating() {
        return rating;
    }
}
