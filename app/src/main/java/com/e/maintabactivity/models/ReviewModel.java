package com.e.maintabactivity.models;

import com.google.gson.annotations.SerializedName;

public class ReviewModel {

    @SerializedName("id")
    int id;

    @SerializedName("rating")
    int rating;

    @SerializedName("date")
    String date;

    @SerializedName("user")
    int user;

    @SerializedName("organizer")
    int organizer;

    @SerializedName("event")
    int event;

    @SerializedName("comment")
    String message;

    public ReviewModel(int rating, int user, int organizer, int event, String message) {
        this.rating = rating;
        this.user = user;
        this.organizer = organizer;
        this.event = event;
    }

    public int getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }

    public int getUser() {
        return user;
    }

    public int getOrganizer() {
        return organizer;
    }

    public int getEvent() {
        return event;
    }

    public String getMessage() {
        return message;
    }
}
