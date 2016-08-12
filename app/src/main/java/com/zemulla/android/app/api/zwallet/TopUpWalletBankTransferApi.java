package com.zemulla.android.app.api.zwallet;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.zwallet.topupwalletbanktransfer.TopUpWalletBankTransferRequest;
import com.zemulla.android.app.model.zwallet.topupwalletbanktransfer.TopUpWalletBankTransferResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class TopUpWalletBankTransferApi {

    private ZWalletAPI paymentAPI;
    private APIListener apiListener;

    public TopUpWalletBankTransferApi() {

        paymentAPI = ZemullaApplication.getRetrofit().create(ZWalletAPI.class);
    }

    public void getTopUpWalletBankTransferApi(final TopUpWalletBankTransferRequest topUpWalletBankTransferRequest, final APIListener apiListener) {
        this.apiListener=apiListener;
        Call<TopUpWalletBankTransferResponse> loginResponseCall = paymentAPI.getTopUpWalletBankTransfer(topUpWalletBankTransferRequest);
        loginResponseCall.enqueue(new Callback<TopUpWalletBankTransferResponse>() {
            @Override
            public void onResponse(Call<TopUpWalletBankTransferResponse> call, Response<TopUpWalletBankTransferResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<TopUpWalletBankTransferResponse> call, Throwable t) {
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
