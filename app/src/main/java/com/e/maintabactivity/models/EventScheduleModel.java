package com.e.maintabactivity.models;

import com.google.gson.annotations.SerializedName;

public class EventScheduleModel {

    @SerializedName("id")
    int id;

    @SerializedName("day")
    String day;

    @SerializedName("event_id")
    int eventId;

    @SerializedName("short_description")
    String description;

    public int getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public int getEventId() {
        return eventId;
    }

    public String getDescription() {
        return description;
    }
}
