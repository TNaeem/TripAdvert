package com.e.maintabactivity.services;

import android.util.Log;

import com.e.maintabactivity.apiServises.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;

public class UserServices {

    public static boolean verifyImage(String imagePath){
        URI u = null;
        try {
            u = new URI(imagePath);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if(u.isAbsolute()) {
            return true;
        }else{
            return false;
        }
    }
}
