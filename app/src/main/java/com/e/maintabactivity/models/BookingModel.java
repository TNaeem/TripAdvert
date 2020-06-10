package com.e.maintabactivity.models;

import com.google.gson.annotations.SerializedName;

public class BookingModel {

    @SerializedName("is_verified")
    boolean is_verified;

    @SerializedName("event")
    int event;

    @SerializedName("user")
    int user;

    @SerializedName("event_details")
    EventModel eventDetails;

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public EventModel getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(EventModel eventDetails) {
        this.eventDetails = eventDetails;
    }
}


