package com.zemulla.android.app.api;

import com.zemulla.android.app.model.country.CountryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public interface AccountAPI {


    @GET("Account.svc/json/GetCountryListAD")
    Call<CountryResponse> GetCountryListAD();


}
