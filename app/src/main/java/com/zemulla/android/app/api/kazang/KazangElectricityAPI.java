package com.zemulla.android.app.api.kazang;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.kazang.kazangtestelectricity.KazangElectricityRequest;
import com.zemulla.android.app.model.kazang.kazangtestelectricity.KazangElectricityResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class KazangElectricityAPI {

    private KazangAPI kazangAPI;
    private APIListener apiListener;
    Call<KazangElectricityResponse> getKazangProductPlanResponseCall;

    public KazangElectricityAPI() {

        kazangAPI = ZemullaApplication.getRetrofit().create(KazangAPI.class);
    }

    public void kazangDirectRechargeAPI(final KazangElectricityRequest kazangElectricityRequest, final APIListener apiListener) {
        getKazangProductPlanResponseCall = kazangAPI.getKazangElectricity(kazangElectricityRequest);
        getKazangProductPlanResponseCall.enqueue(new Callback<KazangElectricityResponse>() {
            @Override
            public void onResponse(Call<KazangElectricityResponse> call, Response<KazangElectricityResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<KazangElectricityResponse> call, Throwable t) {
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
