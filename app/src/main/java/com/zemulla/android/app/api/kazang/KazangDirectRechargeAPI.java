package com.zemulla.android.app.api.kazang;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.kazang.kazangdirectrecharge.KazangDirectRechargeRequest;
import com.zemulla.android.app.model.kazang.kazangdirectrecharge.KazangDirectRechargeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class KazangDirectRechargeAPI {

    private KazangAPI kazangAPI;
    private APIListener apiListener;
    Call<KazangDirectRechargeResponse> getKazangProductPlanResponseCall;

    public KazangDirectRechargeAPI() {

        kazangAPI = ZemullaApplication.getRetrofit().create(KazangAPI.class);
    }

    public void kazangDirectRechargeAPI(final KazangDirectRechargeRequest kazangDirectRechargeRequest, final APIListener apiListener) {
        getKazangProductPlanResponseCall = kazangAPI.getKazangDirectRecharge(kazangDirectRechargeRequest);
        getKazangProductPlanResponseCall.enqueue(new Callback<KazangDirectRechargeResponse>() {
            @Override
            public void onResponse(Call<KazangDirectRechargeResponse> call, Response<KazangDirectRechargeResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<KazangDirectRechargeResponse> call, Throwable t) {
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
