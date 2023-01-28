package com.codecoy.prescriptionapp.utils;


import com.codecoy.prescriptionapp.Api.ApiInterface;
import com.codecoy.prescriptionapp.Api.RetrofitInstance;
import com.codecoy.prescriptionapp.models.Prescriber;
import com.codecoy.prescriptionapp.models.User;

import java.util.ArrayList;

import retrofit2.Retrofit;

public class Singleton {
    // Static variable reference of single_instance
    // of type Singleton
    private static Singleton single_instance = new Singleton();



    // return a api interface instance
    private Retrofit retrofit = RetrofitInstance.getClient();
    public ApiInterface apiInterface = retrofit.create(ApiInterface.class);



    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself

    private Singleton() {
    }


    // Static method
    // Static method to create instance of Singleton class
    public static synchronized Singleton getInstance() {
        if (single_instance == null)
            single_instance = new Singleton();

        return single_instance;
    }


    // current user id
    private String currentUserID;
    private boolean isFiltered = false;


    public void setFiltered(boolean filtered) {
        isFiltered = filtered;
    }

    public boolean isFiltered() {
        return isFiltered;
    }

    public String getCurrentUserID() {
        return currentUserID;
    }

    public void setCurrentUserID(String currentUserID) {
        this.currentUserID = currentUserID;
    }

    // list of Prescriber

    private ArrayList<User> listPriscriber =new ArrayList<>();


    public ArrayList<User> getListPriscriber() {
        return listPriscriber;
    }

    public void setListPriscriber(ArrayList<User> listPriscriber) {
        this.listPriscriber = listPriscriber;
    }

    private ArrayList<Prescriber> listPriscriberDetail =new ArrayList<>();

    public ArrayList<Prescriber> getListPriscriberDetail() {
        return listPriscriberDetail;
    }

    public void setListPriscriberDetail(ArrayList<Prescriber> listPriscriberDetail) {
        this.listPriscriberDetail = listPriscriberDetail;
    }
}

