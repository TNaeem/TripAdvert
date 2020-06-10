package com.e.maintabactivity.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class AnnouncementModel implements Serializable {


    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;
    @SerializedName("topic")
    private String topic;
    @SerializedName("created_at")
    private Date date;

    public AnnouncementModel() {
    }
    public AnnouncementModel(String title, String body, String topic, Date date) {
        this.title = title;
        this.body = body;
        this.topic = topic;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
