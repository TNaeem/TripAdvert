package com.e.maintabactivity.staticModels;

import com.e.maintabactivity.models.PersonModel;

import java.util.List;

public class StaticOrganizerModel {

    public static List<PersonModel> allOrganizers;

    public static PersonModel getOrganizer(int id) {
        for (PersonModel p : allOrganizers) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }



}
