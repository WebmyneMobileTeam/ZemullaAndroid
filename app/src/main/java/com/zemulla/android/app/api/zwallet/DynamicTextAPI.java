package com.zemulla.android.app.api.zwallet;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.zwallet.DynamicTextResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 08-08-2016.
 */
public class DynamicTextAPI {

    private ZWalletAPI zWalletAPI;
    private APIListener apiListener;

    public DynamicTextAPI() {

        zWalletAPI = ZemullaApplication.getRetrofit().create(ZWalletAPI.class);
    }

    public void getDynamicText(final int apiMasterId, final APIListener apiListener) {
        Call<DynamicTextResponse> loginResponseCall = zWalletAPI.getDynamicText(apiMasterId);
        loginResponseCall.enqueue(new Callback<DynamicTextResponse>() {
            @Override
            public void onResponse(Call<DynamicTextResponse> call, Response<DynamicTextResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<DynamicTextResponse> call, Throwable t) {
                apiListener.onFailure(call, t);
            }
        });

    }

    public void onDestory() {
        if (zWalletAPI != null) {
            zWalletAPI = null;
        }
        if (apiListener != null) {
            apiListener = null;
        }
    }

}
