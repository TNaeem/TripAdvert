package com.e.maintabactivity.staticModels;

import com.e.maintabactivity.models.PersonModel;

import java.util.List;

public class StaticUserModel {

    public static String firebaseInstanceId;
    public static List<PersonModel> allUsers;

    public static PersonModel getUser(int id){
        for (PersonModel p : allUsers){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }
}
