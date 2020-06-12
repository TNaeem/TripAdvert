package com.e.maintabactivity.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewEventModel {
    @SerializedName("id")
    int  id;

    @SerializedName("pic")
    String pic;

    @SerializedName("title")
    String title;

    @SerializedName("home")
    String home;

    @SerializedName("destination")
    String destination;

    @SerializedName("description")
    String description;

    @SerializedName("is_completed")
    boolean isCompleted;

    @SerializedName("date_of_departure")
    String dateOfDeparture;

    @SerializedName("date_of_arrival")
    String dateOfArrival;

    @SerializedName("slots")
    int slots;

    @SerializedName("price")
    int price;

    @SerializedName("is_accomodation")
    boolean isAccommodated;

    @SerializedName("is_sightseeing")
    boolean isSightseeing;

    @SerializedName("is_food")
    boolean isFood;

    @SerializedName("accomodation_description")
    String accommodationDescription;

    @SerializedName("sightseeing_description")
    String sightseeingDescription;

    @SerializedName("food_description")
    String foodDescription;

    @SerializedName("organizer")
    OrganizerModel organizer;

    @SerializedName("schedule")
    List<EventScheduleModel> scheduleList;

    @SerializedName("free_slots")
    int freeSLots;

    public int getId() {
        return id;
    }

    public String getPic() {
        return pic;
    }

    public String getTitle() {
        return title;
    }

    public String getHome() {
        return home;
    }

    public String getDestination() {
        return destination;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getDateOfDeparture() {
        return dateOfDeparture;
    }

    public String getDateOfArrival() {
        return dateOfArrival;
    }

    public int getSlots() {
        return slots;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAccommodated() {
        return isAccommodated;
    }

    public boolean isSightseeing() {
        return isSightseeing;
    }

    public boolean isFood() {
        return isFood;
    }

    public String getAccommodationDescription() {
        return accommodationDescription;
    }

    public String getSightseeingDescription() {
        return sightseeingDescription;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public OrganizerModel getOrganizer() {
        return organizer;
    }

    public List<EventScheduleModel> getScheduleList() {
        return scheduleList;
    }

    public int getFreeSLots() {
        return freeSLots;
    }
}
