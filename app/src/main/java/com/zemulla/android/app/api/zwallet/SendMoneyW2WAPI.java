package com.zemulla.android.app.api.zwallet;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.zwallet.sendmoneyw2wad.SendMoneyW2WRequest;
import com.zemulla.android.app.model.zwallet.sendmoneyw2wad.SendMoneyW2WResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class SendMoneyW2WAPI {

    private ZWalletAPI paymentAPI;
    private APIListener apiListener;
    Call<SendMoneyW2WResponse> sendMoneyW2WResponseCall;

    public SendMoneyW2WAPI() {

        paymentAPI = ZemullaApplication.getRetrofit().create(ZWalletAPI.class);
    }

    public void SendMoneyW2W(final SendMoneyW2WRequest sendMoneyW2WRequest, final APIListener apiListener) {
        this.apiListener = apiListener;
        sendMoneyW2WResponseCall = paymentAPI.sendMoneyW2W(sendMoneyW2WRequest);
        sendMoneyW2WResponseCall.enqueue(new Callback<SendMoneyW2WResponse>() {
            @Override
            public void onResponse(Call<SendMoneyW2WResponse> call, Response<SendMoneyW2WResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<SendMoneyW2WResponse> call, Throwable t) {
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
