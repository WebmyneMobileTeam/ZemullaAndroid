package com.zemulla.android.app.api.kazang;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.kazang.dstvpayment1.DSTVPayment1Request;
import com.zemulla.android.app.model.kazang.dstvpayment1.DSTVPayment1Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class DSTVPayment1API {

    private KazangAPI kazangAPI;
    private APIListener apiListener;
    Call<DSTVPayment1Response> dstvPayment1ResponseCall;

    public DSTVPayment1API() {

        kazangAPI = ZemullaApplication.getRetrofit().create(KazangAPI.class);
    }

    public void callDSTVPayment1(final DSTVPayment1Request dstvPayment1Request, final APIListener apiListener) {
        dstvPayment1ResponseCall = kazangAPI.getDSTVPayment1(dstvPayment1Request);
        dstvPayment1ResponseCall.enqueue(new Callback<DSTVPayment1Response>() {
            @Override
            public void onResponse(Call<DSTVPayment1Response> call, Response<DSTVPayment1Response> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<DSTVPayment1Response> call, Throwable t) {
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
