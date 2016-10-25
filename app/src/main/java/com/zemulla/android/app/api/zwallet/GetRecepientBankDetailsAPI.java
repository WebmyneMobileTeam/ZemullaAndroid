package com.zemulla.android.app.api.zwallet;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.payment.getsupportedbankdetails.GetSupportedBankDetailsResponse;
import com.zemulla.android.app.model.zwallet.RecepientBankListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public class GetRecepientBankDetailsAPI {

    private ZWalletAPI zWalletAPI;
    private APIListener countryListener;

    public GetRecepientBankDetailsAPI() {
        zWalletAPI = ZemullaApplication.getRetrofit().create(ZWalletAPI.class);
    }

    public void getSupportedBankDetailsAPI(final APIListener apiListener) {
        countryListener = apiListener;
        Call<RecepientBankListResponse> countryResponseCall = zWalletAPI.getBanklist();
        countryResponseCall.enqueue(new Callback<RecepientBankListResponse>() {
            @Override
            public void onResponse(Call<RecepientBankListResponse> call, Response<RecepientBankListResponse> response) {

                countryListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<RecepientBankListResponse> call, Throwable t) {
                countryListener.onFailure(call, t);
            }
        });
    }

    public void setAPIListener(APIListener countryListener) {
        this.countryListener = countryListener;
    }

    public void onDestory() {
        if (zWalletAPI != null) {
            zWalletAPI = null;
        }
        if (countryListener != null) {
            countryListener = null;
        }
    }


}
