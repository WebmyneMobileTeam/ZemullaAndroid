package com.zemulla.android.app.api.payment;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.payment.PaypalPayment.PaypalPayment2Request;
import com.zemulla.android.app.model.payment.PaypalPayment.PaypalPayment2Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class GetPayPal2Api {

    private PaymentAPI paymentAPI;
    private APIListener apiListener;

    public GetPayPal2Api() {

        paymentAPI = ZemullaApplication.getRetrofit().create(PaymentAPI.class);
    }

    public void getPayPal1(final PaypalPayment2Request request, final APIListener apiListener) {
        Call<PaypalPayment2Response> loginResponseCall = paymentAPI.ProcessPaypal(request);
        loginResponseCall.enqueue(new Callback<PaypalPayment2Response>() {
            @Override
            public void onResponse(Call<PaypalPayment2Response> call, Response<PaypalPayment2Response> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<PaypalPayment2Response> call, Throwable t) {
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
