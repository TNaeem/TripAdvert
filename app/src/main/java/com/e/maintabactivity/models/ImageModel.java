package com.e.maintabactivity.models;

import com.google.gson.annotations.SerializedName;

public class ImageModel {

    @SerializedName("image")
    String image;

    @SerializedName("event")
    int eventId;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getEvent() {
        return eventId;
    }

    public void setEvent(int event) {
        this.eventId = event;
    }


}
