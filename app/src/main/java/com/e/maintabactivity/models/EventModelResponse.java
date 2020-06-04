package com.e.maintabactivity.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventModelResponse {
    @SerializedName("count")
    int count;

    @SerializedName("next")
    String next;

    @SerializedName("previous")
    String previous;

    @SerializedName("results")
    List<EventModel> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<EventModel> getResults() {
        return results;
    }

    public void setResults(List<EventModel> results) {
        this.results = results;
    }
}
