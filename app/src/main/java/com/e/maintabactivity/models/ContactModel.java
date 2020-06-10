package com.e.maintabactivity.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ContactModel {

    PersonModel person;
    String lastMessage;
    String lastMessageDate;

    public ContactModel(PersonModel person, String lastMessage, Date lastMessageDate) {
        this.person = person;
        this.lastMessage = lastMessage;
        setLastMessageDate(lastMessageDate);
    }
    public ContactModel() {

    }

    public PersonModel getPerson() {
        return person;
    }

    public void setPerson(PersonModel person) {
        this.person = person;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Date getLastMessageDate() {
        return new Date(lastMessageDate);
    }

    public void setLastMessageDate(Date lastMessageDate) {
        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
         this.lastMessageDate= ISO_8601_FORMAT.format(lastMessageDate);

    }
}
