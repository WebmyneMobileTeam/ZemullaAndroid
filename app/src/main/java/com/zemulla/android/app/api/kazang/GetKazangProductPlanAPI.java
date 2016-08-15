package com.zemulla.android.app.api.kazang;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.kazang.getkazangproductplan.GetKazangProductPlanRequest;
import com.zemulla.android.app.model.kazang.getkazangproductplan.GetKazangProductPlanResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class GetKazangProductPlanAPI {

    private KazangAPI kazangAPI;
    private APIListener apiListener;
    Call<GetKazangProductPlanResponse> getKazangProductPlanResponseCall;

    public GetKazangProductPlanAPI() {

        kazangAPI = ZemullaApplication.getRetrofit().create(KazangAPI.class);
    }

    public void getKazangProductPlan(final GetKazangProductPlanRequest getKazangProductPlanRequest,final APIListener apiListener) {
        getKazangProductPlanResponseCall = kazangAPI.getKazangProductPlan(getKazangProductPlanRequest);
        getKazangProductPlanResponseCall.enqueue(new Callback<GetKazangProductPlanResponse>() {
            @Override
            public void onResponse(Call<GetKazangProductPlanResponse> call, Response<GetKazangProductPlanResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<GetKazangProductPlanResponse> call, Throwable t) {
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
