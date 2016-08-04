package com.zemulla.android.app.api;

import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.model.country.CountryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public interface AccountAPI {


    @GET(AppConstant.GETCOUNTRYLISTAD)
    Call<CountryResponse> GetCountryListAD();


}
