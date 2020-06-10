package com.e.maintabactivity.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageModel {

    int senderUserId;
    int receiverUserId;
    String message;
    String date;

    public MessageModel(){

    }

    public MessageModel(int senderUserId, int receiverUserId, String message, Date date) {
        this.senderUserId = senderUserId;
        this.receiverUserId = receiverUserId;
        this.message = message;
        dateDate(date);
    }

    public int getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(int senderUserId) {
        this.senderUserId = senderUserId;
    }

    public int getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(int receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date dateDate() {
        return new Date(date);

    }

    public void dateDate(Date date) {
        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
        this.date = ISO_8601_FORMAT.format(date);

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
