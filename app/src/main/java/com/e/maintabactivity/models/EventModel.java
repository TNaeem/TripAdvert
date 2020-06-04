package com.e.maintabactivity.models;

import com.google.gson.annotations.SerializedName;


public class EventModel {

    @SerializedName("id")
    int  id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @SerializedName("pic")
    String pic;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("category")
    String category;

    @SerializedName("is_full")
    boolean isFull;

    @SerializedName("home")
    String home;

    @SerializedName("destination")
    String destination;

    @SerializedName("date_of_departure")
    String dateOfDeparture;

    @SerializedName("date_of_arrival")
    String dateOfArrival;

    @SerializedName("slots")
    int slots;

    @SerializedName("price")
    int price;

    @SerializedName("is_completed")
    boolean isCompleted;

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
    int organizer;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(String dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public String getDateOfArrival() {
        return dateOfArrival;
    }

    public void setDateOfArrival(String dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isAccommodated() {
        return isAccommodated;
    }

    public void setAccommodated(boolean accommodated) {
        isAccommodated = accommodated;
    }

    public boolean isSightseeing() {
        return isSightseeing;
    }

    public void setSightseeing(boolean sightseeing) {
        isSightseeing = sightseeing;
    }

    public boolean isFood() {
        return isFood;
    }

    public void setFood(boolean food) {
        isFood = food;
    }

    public String getAccommodationDescription() {
        return accommodationDescription;
    }

    public void setAccommodationDescription(String accommodationDescription) {
        this.accommodationDescription = accommodationDescription;
    }

    public String getSightseeingDescription() {
        return sightseeingDescription;
    }

    public void setSightseeingDescription(String sightseeingDescription) {
        this.sightseeingDescription = sightseeingDescription;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public int getOrganizer() {
        return organizer;
    }

    public void setOrganizer(int organizer) {
        this.organizer = organizer;
    }
}
