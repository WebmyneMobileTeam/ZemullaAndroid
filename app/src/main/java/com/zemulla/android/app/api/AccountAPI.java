package com.zemulla.android.app.api;

import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.model.country.CountryResponse;
import com.zemulla.android.app.model.login.LoginRequest;
import com.zemulla.android.app.model.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public interface AccountAPI {


    @GET(AppConstant.GETCOUNTRYLISTAD)
    Call<CountryResponse> GetCountryListAD();


    @POST(AppConstant.Login)
    Call<LoginResponse> Login(@Body LoginRequest loginRequest);


}
