package com.zemulla.android.app.api;

import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.country.CountryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public class CountryAPI {

    private AccountAPI accountAPI;
    private CountryListener countryListener;

    public CountryAPI() {
        accountAPI = ZemullaApplication.getRetrofit().create(AccountAPI.class);
    }

    public void getCountryAPI(final CountryListener countryListener) {
        Call<CountryResponse> countryResponseCall = accountAPI.GetCountryListAD();
        countryResponseCall.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {

                countryListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                countryListener.onFailure(call, t);
            }
        });
    }

    public void setCountryListener(CountryListener countryListener) {
        this.countryListener = countryListener;
    }

    public void onDestory() {
        if (accountAPI != null) {
            accountAPI = null;
        }
        if (countryListener != null) {
            countryListener = null;
        }
    }


    public interface CountryListener {
        void onResponse(Response<CountryResponse> response);

        void onFailure(Call<CountryResponse> call, Throwable t);
    }
}
