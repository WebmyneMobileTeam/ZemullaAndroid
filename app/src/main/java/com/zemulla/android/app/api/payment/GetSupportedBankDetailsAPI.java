package com.zemulla.android.app.api.payment;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.payment.getsupportedbankdetails.GetSupportedBankDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public class GetSupportedBankDetailsAPI {

    private PaymentAPI paymentAPI;
    private APIListener countryListener;

    public GetSupportedBankDetailsAPI() {
        paymentAPI = ZemullaApplication.getRetrofit().create(PaymentAPI.class);
    }

    public void getSupportedBankDetailsAPI(final APIListener apiListener) {
        countryListener = apiListener;
        Call<GetSupportedBankDetailsResponse> countryResponseCall = paymentAPI.GetSupportedBankDetails();
        countryResponseCall.enqueue(new Callback<GetSupportedBankDetailsResponse>() {
            @Override
            public void onResponse(Call<GetSupportedBankDetailsResponse> call, Response<GetSupportedBankDetailsResponse> response) {

                countryListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<GetSupportedBankDetailsResponse> call, Throwable t) {
                countryListener.onFailure(call, t);
            }
        });
    }

    public void setAPIListener(APIListener countryListener) {
        this.countryListener = countryListener;
    }

    public void onDestory() {
        if (paymentAPI != null) {
            paymentAPI = null;
        }
        if (countryListener != null) {
            countryListener = null;
        }
    }


}
