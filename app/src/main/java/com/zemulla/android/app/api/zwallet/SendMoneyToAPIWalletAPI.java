package com.zemulla.android.app.api.zwallet;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.zwallet.sendmoneytoapiwallet.SendMoneyToAPIWalletRequest;
import com.zemulla.android.app.model.zwallet.sendmoneytoapiwallet.SendMoneyToAPIWalletResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class SendMoneyToAPIWalletAPI {

    private ZWalletAPI paymentAPI;
    private APIListener apiListener;
    Call<SendMoneyToAPIWalletResponse> loginResponseCall;

    public SendMoneyToAPIWalletAPI() {

        paymentAPI = ZemullaApplication.getRetrofit().create(ZWalletAPI.class);
    }

    public void getSendMoneyToAPIWallet(final SendMoneyToAPIWalletRequest request, final APIListener apiListener) {
        loginResponseCall = paymentAPI.getSendMoneyToAPIWalletRequest(request);
        loginResponseCall.enqueue(new Callback<SendMoneyToAPIWalletResponse>() {
            @Override
            public void onResponse(Call<SendMoneyToAPIWalletResponse> call, Response<SendMoneyToAPIWalletResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<SendMoneyToAPIWalletResponse> call, Throwable t) {
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
