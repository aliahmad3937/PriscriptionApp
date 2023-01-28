package com.codecoy.prescriptionapp.Api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    public static retrofit2.Retrofit RETROFIT = null;

     public static String BASE_URL = "https://wh717090.ispot.cc/prescription_app/webservice/";


    public static synchronized Retrofit getClient() {
        if (RETROFIT == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            //    AndroidNetworking.initialize(getApplicationContext());

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            RETROFIT = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL )
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
            return RETROFIT;
        }
        return RETROFIT;
    }




}
