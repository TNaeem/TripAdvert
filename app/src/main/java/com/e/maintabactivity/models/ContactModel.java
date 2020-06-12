package com.e.maintabactivity.models;

public class ContactModel {

    String name;
    int id;
    MessageModel message;

    public ContactModel(int id, String name) {
        this.id = id;
        this.name = name;
        //this.lastMessage = lastMessage;
}
    public ContactModel() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(MessageModel message) {
        this.message = message;
    }



    public String getName() {
        return name;
    }

    public MessageModel getMessage() {
        return message;
    }

    public int getId() {return id;}

}
