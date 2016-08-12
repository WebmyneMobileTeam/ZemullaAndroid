package com.zemulla.android.app.api.zwallet;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.zwallet.sendmoneybanttransfer.SendMoneyBantTransferRequest;
import com.zemulla.android.app.model.zwallet.sendmoneybanttransfer.SendMoneyBantTransferResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class SendMoneyBantTransferAPI {

    private ZWalletAPI paymentAPI;
    private APIListener apiListener;
    Call<SendMoneyBantTransferResponse> sendMoneyBantTransferResponseCall;

    public SendMoneyBantTransferAPI() {

        paymentAPI = ZemullaApplication.getRetrofit().create(ZWalletAPI.class);
    }

    public void getTopUpWalletBankTransferApi(final SendMoneyBantTransferRequest sendMoneyBantTransferRequest, final APIListener apiListener) {
        this.apiListener = apiListener;
        sendMoneyBantTransferResponseCall = paymentAPI.sendMoneyBantTransfer(sendMoneyBantTransferRequest);
        sendMoneyBantTransferResponseCall.enqueue(new Callback<SendMoneyBantTransferResponse>() {
            @Override
            public void onResponse(Call<SendMoneyBantTransferResponse> call, Response<SendMoneyBantTransferResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<SendMoneyBantTransferResponse> call, Throwable t) {
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
