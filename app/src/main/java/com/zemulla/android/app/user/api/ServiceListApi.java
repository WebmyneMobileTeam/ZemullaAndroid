package com.zemulla.android.app.user.api;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.account.AccountAPI;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.user.model.ServiceListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 08-08-2016.
 */
public class ServiceListApi {

    private AccountAPI accountAPI;
    private APIListener apiListener;

    public ServiceListApi() {

        accountAPI = ZemullaApplication.getRetrofit().create(AccountAPI.class);
    }

    public void getService(final APIListener<ServiceListResponse> apiListener) {
        Call<ServiceListResponse> loginResponseCall = accountAPI.getServiceList();
        loginResponseCall.enqueue(new Callback<ServiceListResponse>() {
            @Override
            public void onResponse(Call<ServiceListResponse> call, Response<ServiceListResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<ServiceListResponse> call, Throwable t) {
                apiListener.onFailure(call, t);
            }
        });

    }


    public void onDestory() {
        if (accountAPI != null) {
            accountAPI = null;
        }
        if (apiListener != null) {
            apiListener = null;
        }
    }
}
