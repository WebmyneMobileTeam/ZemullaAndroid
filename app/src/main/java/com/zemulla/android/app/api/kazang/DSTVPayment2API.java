package com.zemulla.android.app.api.kazang;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.kazang.dstvpayment2.DSTVPayment2Request;
import com.zemulla.android.app.model.kazang.dstvpayment2.DSTVPayment2Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class DSTVPayment2API {

    private KazangAPI kazangAPI;
    private APIListener apiListener;
    Call<DSTVPayment2Response> dstvPayment3ResponseCall;

    public DSTVPayment2API() {

        kazangAPI = ZemullaApplication.getRetrofit().create(KazangAPI.class);
    }

    public void dstv2(final DSTVPayment2Request dstvPayment2Request, final APIListener apiListener) {
        dstvPayment3ResponseCall = kazangAPI.getDSTVPayment2(dstvPayment2Request);
        dstvPayment3ResponseCall.enqueue(new Callback<DSTVPayment2Response>() {
            @Override
            public void onResponse(Call<DSTVPayment2Response> call, Response<DSTVPayment2Response> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<DSTVPayment2Response> call, Throwable t) {
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
