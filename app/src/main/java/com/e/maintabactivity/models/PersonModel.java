package com.e.maintabactivity.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class PersonModel {
    @SerializedName("id")
    int id;

    @SerializedName("first_name")
    String first_name;

    @SerializedName("last_name")
    String last_name;

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    @SerializedName("phone_no")
    String phone_no;

    @SerializedName("is_blocked")
    boolean is_blocked;

    @SerializedName("image")
    String image;

    @SerializedName("date")
    Date date;

    @SerializedName("user_type")
    int user_type;

    @SerializedName("firebaseinstancetoken")
    String firebaseInstanceId;

    @SerializedName("user")
    UserModel user = null;

    @SerializedName("organizer")
    OrganizerModel organizer = null;

    public PersonModel(int id){
        this.id = id;
    }
    public PersonModel(){
    }
    public String getFirebaseInstanceId() {
        return firebaseInstanceId;
    }

    public void setFirebaseInstanceId(String firebaseInstanceId) {
        this.firebaseInstanceId = firebaseInstanceId;
    }
    public int getId() {
        return id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public OrganizerModel getOrganizer() {
        return organizer;
    }

    public void setOrganizer(OrganizerModel organizer) {
        this.organizer = organizer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public boolean isIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(boolean is_blocked) {
        this.is_blocked = is_blocked;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int isUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }
}
