package com.e.maintabactivity.models;

import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("address")
    String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
