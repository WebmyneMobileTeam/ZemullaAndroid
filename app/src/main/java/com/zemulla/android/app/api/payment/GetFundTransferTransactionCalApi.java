package com.zemulla.android.app.api.payment;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.FundTransferTransactionChargeCalculationResponse;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class GetFundTransferTransactionCalApi {

    private PaymentAPI paymentAPI;
    private APIListener apiListener;
    Call<FundTransferTransactionChargeCalculationResponse> fundTransferTransactionChargeCalculationResponseCall;
    public GetFundTransferTransactionCalApi() {

        paymentAPI = ZemullaApplication.getRetrofit().create(PaymentAPI.class);
    }

    public void getTopupCharge(final TopUpTransactionChargeCalculationRequest request, final APIListener apiListener) {
        fundTransferTransactionChargeCalculationResponseCall = paymentAPI.GetFundTransferCharge(request);
        fundTransferTransactionChargeCalculationResponseCall.enqueue(new Callback<FundTransferTransactionChargeCalculationResponse>() {
            @Override
            public void onResponse(Call<FundTransferTransactionChargeCalculationResponse> call, Response<FundTransferTransactionChargeCalculationResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<FundTransferTransactionChargeCalculationResponse> call, Throwable t) {
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
