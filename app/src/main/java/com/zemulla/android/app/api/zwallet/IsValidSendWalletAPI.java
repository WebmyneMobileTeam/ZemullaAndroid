package com.zemulla.android.app.api.zwallet;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.zwallet.isvalidsendwallet.IsValidSendWalletRequest;
import com.zemulla.android.app.model.zwallet.isvalidsendwallet.IsValidSendWalletResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class IsValidSendWalletAPI {

    private ZWalletAPI paymentAPI;
    private APIListener apiListener;
    Call<IsValidSendWalletResponse> isValidSendWalletResponseCall;

    public IsValidSendWalletAPI() {

        paymentAPI = ZemullaApplication.getRetrofit().create(ZWalletAPI.class);
    }

    public void IsValidSendWallet(final IsValidSendWalletRequest isValidSendWalletRequest, final APIListener apiListener) {
        this.apiListener = apiListener;
        isValidSendWalletResponseCall = paymentAPI.isValidSendWallet(isValidSendWalletRequest);
        isValidSendWalletResponseCall.enqueue(new Callback<IsValidSendWalletResponse>() {
            @Override
            public void onResponse(Call<IsValidSendWalletResponse> call, Response<IsValidSendWalletResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<IsValidSendWalletResponse> call, Throwable t) {
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
