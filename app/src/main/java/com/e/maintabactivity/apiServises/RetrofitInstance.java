package com.e.maintabactivity.apiServises;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit mRetrofit;
    public static final String BASE_URL = "https://b790db6d2399.ngrok.io";
    private static final String URL = BASE_URL+"/api/";

    public static Retrofit getRetrofitInstance(){
        if(mRetrofit == null){
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .setLenient()
                    .create();

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(2, TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .build();

            mRetrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }

}
