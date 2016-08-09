package com.zemulla.android.app.api.zwallet;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.zwallet.topupwallet.TopUpRequest;
import com.zemulla.android.app.model.zwallet.topupwallet.TopUpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class TopupAPIWalletApi {

    private ZWalletAPI paymentAPI;
    private APIListener apiListener;

    public TopupAPIWalletApi() {

        paymentAPI = ZemullaApplication.getRetrofit().create(ZWalletAPI.class);
    }

    public void getTopupCharge(final TopUpRequest request, final APIListener apiListener) {
        Call<TopUpResponse> loginResponseCall = paymentAPI.GetTopUpCharge(request);
        loginResponseCall.enqueue(new Callback<TopUpResponse>() {
            @Override
            public void onResponse(Call<TopUpResponse> call, Response<TopUpResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<TopUpResponse> call, Throwable t) {
                apiListener.onFailure(call, t);
            }
        });

    }


    public void onDestory() {
        if (paymentAPI != null) {
            paymentAPI = null;
        }
        if (apiListener != null) {
            apiListener = null;
        }
    }
}
