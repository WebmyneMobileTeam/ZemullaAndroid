package com.zemulla.android.app.api.kazang;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.kazang.getproductapiprodmasteridwise.GetProductAPIProdMasterIDWiseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class GetProductAPIProdMasterIDWiseAPI {

    private KazangAPI kazangAPI;
    private APIListener apiListener;
    Call<GetProductAPIProdMasterIDWiseResponse> fundTransferTransactionChargeCalculationResponseCall;

    public GetProductAPIProdMasterIDWiseAPI() {

        kazangAPI = ZemullaApplication.getRetrofit().create(KazangAPI.class);
    }

    public void kazangProductProvider(final String providerName, final APIListener apiListener) {
        fundTransferTransactionChargeCalculationResponseCall = kazangAPI.getProductAPIProdMasterIDWise(providerName);
        fundTransferTransactionChargeCalculationResponseCall.enqueue(new Callback<GetProductAPIProdMasterIDWiseResponse>() {
            @Override
            public void onResponse(Call<GetProductAPIProdMasterIDWiseResponse> call, Response<GetProductAPIProdMasterIDWiseResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<GetProductAPIProdMasterIDWiseResponse> call, Throwable t) {
                apiListener.onFailure(call, t);
            }
        });

    }


    public void onDestory() {
        if (kazangAPI != null) {
            kazangAPI = null;
        }
        if (apiListener != null) {
            apiListener = null;
        }
    }

}
