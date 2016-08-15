package com.zemulla.android.app.api.kazang;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.kazang.kazangairtime.KazangAirtimeRequest;
import com.zemulla.android.app.model.kazang.kazangairtime.KazangAirtimeResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class KazangAirTimeAPI {

    private KazangAPI kazangAPI;
    private APIListener apiListener;
    Call<KazangAirtimeResponse> kazangAirtimeResponseCall;

    public KazangAirTimeAPI() {

        kazangAPI = ZemullaApplication.getRetrofit().create(KazangAPI.class);
    }

    public void kazangAirTime(final KazangAirtimeRequest kazangAirtimeRequest,final APIListener apiListener) {
        kazangAirtimeResponseCall = kazangAPI.getKazangAirtime(kazangAirtimeRequest);
        kazangAirtimeResponseCall.enqueue(new Callback<KazangAirtimeResponse>() {
            @Override
            public void onResponse(Call<KazangAirtimeResponse> call, Response<KazangAirtimeResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<KazangAirtimeResponse> call, Throwable t) {
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
