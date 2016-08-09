package com.zemulla.android.app.api.payment;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationRequest;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class GetTopupTransactionApi {

    private PaymentAPI paymentAPI;
    private APIListener apiListener;

    public GetTopupTransactionApi() {

        paymentAPI = ZemullaApplication.getRetrofit().create(PaymentAPI.class);
    }

    public void getTopupCharge(final TopUpTransactionChargeCalculationRequest request, final APIListener apiListener) {
        Call<TopUpTransactionChargeCalculationResponse> loginResponseCall = paymentAPI.GetTopUpCharge(request);
        loginResponseCall.enqueue(new Callback<TopUpTransactionChargeCalculationResponse>() {
            @Override
            public void onResponse(Call<TopUpTransactionChargeCalculationResponse> call, Response<TopUpTransactionChargeCalculationResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<TopUpTransactionChargeCalculationResponse> call, Throwable t) {
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
