package com.zemulla.android.app.api.kazang;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.kazang.kazangtestelectricity.KazangTestElectricityResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class KazangTestElectricityAPI {

    private KazangAPI kazangAPI;
    private APIListener apiListener;
    Call<KazangTestElectricityResponse> getKazangProductPlanResponseCall;

    public KazangTestElectricityAPI() {

        kazangAPI = ZemullaApplication.getRetrofit().create(KazangAPI.class);
    }

    public void kazangDirectRechargeAPI(final String s, final APIListener apiListener) {
        getKazangProductPlanResponseCall = kazangAPI.getKazangTestElectricity(s);
        getKazangProductPlanResponseCall.enqueue(new Callback<KazangTestElectricityResponse>() {
            @Override
            public void onResponse(Call<KazangTestElectricityResponse> call, Response<KazangTestElectricityResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<KazangTestElectricityResponse> call, Throwable t) {
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
