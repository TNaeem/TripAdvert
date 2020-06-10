package com.e.maintabactivity.models;

import com.google.gson.annotations.SerializedName;

public class OrganizerModel {

    @SerializedName("id")
    int id;

    @SerializedName("cnic")
    String cnic = null;

    @SerializedName("address")
    String address = null;

    @SerializedName("organization")
    String organization = null;

    @SerializedName("experience")
    String experience = null;

    @SerializedName("is_verified")
    boolean is_verified;

    @SerializedName("rating")
    float rating;

    public int getId() {
        return id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }
}