package com.zemulla.android.app.api.payment;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.payment.PaypalPayment.PaypalPayment1Request;
import com.zemulla.android.app.model.payment.PaypalPayment.PaypalPayment1Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class GetPayPal1Api {

    private PaymentAPI paymentAPI;
    private APIListener apiListener;

    public GetPayPal1Api() {

        paymentAPI = ZemullaApplication.getRetrofit().create(PaymentAPI.class);
    }

    public void getPayPal1(final PaypalPayment1Request request, final APIListener apiListener) {
        Call<PaypalPayment1Response> loginResponseCall = paymentAPI.GetPayPalCharge(request);
        loginResponseCall.enqueue(new Callback<PaypalPayment1Response>() {
            @Override
            public void onResponse(Call<PaypalPayment1Response> call, Response<PaypalPayment1Response> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<PaypalPayment1Response> call, Throwable t) {
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
