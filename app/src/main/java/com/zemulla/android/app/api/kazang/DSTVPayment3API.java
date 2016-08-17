package com.zemulla.android.app.api.kazang;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.kazang.dstvpayment3.DSTVPayment3Request;
import com.zemulla.android.app.model.kazang.dstvpayment3.DSTVPayment3Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class DSTVPayment3API {

    private KazangAPI kazangAPI;
    private APIListener apiListener;
    Call<DSTVPayment3Response> dstvPayment3ResponseCall;

    public DSTVPayment3API() {

        kazangAPI = ZemullaApplication.getRetrofit().create(KazangAPI.class);
    }

    public void dstv3(final DSTVPayment3Request dstvPayment3Request, final APIListener apiListener) {
        dstvPayment3ResponseCall = kazangAPI.getDSTVPayment3(dstvPayment3Request);
        dstvPayment3ResponseCall.enqueue(new Callback<DSTVPayment3Response>() {
            @Override
            public void onResponse(Call<DSTVPayment3Response> call, Response<DSTVPayment3Response> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<DSTVPayment3Response> call, Throwable t) {
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
