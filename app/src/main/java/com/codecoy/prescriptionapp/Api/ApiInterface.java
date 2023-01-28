package com.codecoy.prescriptionapp.Api;


import com.codecoy.prescriptionapp.models.DetailResponse;
import com.codecoy.prescriptionapp.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> checkUserStatus(
            @Field("email") String email ,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("prescriber_list")
    Call<LoginResponse> getPrescriberList(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("prescriber_list")
    Call<LoginResponse> getPrescriberList(
            @Field("user_id") String user_id,
            @Field("start_date") String start,
            @Field("end_date") String end
    );


    @FormUrlEncoded
    @POST("filter")
    Call<DetailResponse> filterUser(
            @Field("fullname") String name,
            @Field("start_date") String start,
            @Field("end_date") String end
    );


    @GET("detail_prescribe")
    Call<DetailResponse> getPrescriberDetail(
            @Query("fullname") String name
    );



}

