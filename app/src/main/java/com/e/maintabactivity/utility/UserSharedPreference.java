package com.e.maintabactivity.utility;

import android.content.Context;
import android.content.SharedPreferences;


import com.e.maintabactivity.models.PersonModel;
import com.e.maintabactivity.models.UserModel;
import com.google.gson.Gson;


public class UserSharedPreference {

    public static final String SHARED_PREF_NAME = "SCA_APP";
    public static final String USER_DETAILS_STRING = "USER_DETAIL";

    public static void saveUser(Context mContext, PersonModel user){
        SharedPreferences mPrefs = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString(USER_DETAILS_STRING, json);
        prefsEditor.commit();
    }

    public static boolean userExists(Context mContext) {
        SharedPreferences mPrefs = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return mPrefs.contains(USER_DETAILS_STRING);
    }

    public static PersonModel getUser(Context mContext){
        SharedPreferences mPrefs = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(USER_DETAILS_STRING, "");
        if(json.equalsIgnoreCase("")){
            return null;
        }
        PersonModel user = gson.fromJson(json, PersonModel.class);
        return user;
    }

    public static void removeUser(Context mContext){
        SharedPreferences mPrefs = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.remove(USER_DETAILS_STRING);
        prefsEditor.commit();
    }
}
