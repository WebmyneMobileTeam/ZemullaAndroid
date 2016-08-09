package com.zemulla.android.app.api.account;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.account.optgenval.OTPGenValRequest;
import com.zemulla.android.app.model.account.optgenval.OTPGenValResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class OTPGenValAPI {
    private AccountAPI accountAPI;
    private APIListener apiListener;

    public OTPGenValAPI() {

        accountAPI = ZemullaApplication.getRetrofit().create(AccountAPI.class);
    }

    public void otpGenVal(final OTPGenValRequest otpGenValRequest, final APIListener apiListener) {
        Call<OTPGenValResponse> loginResponseCall = accountAPI.OTPGenVal(otpGenValRequest);
        loginResponseCall.enqueue(new Callback<OTPGenValResponse>() {
            @Override
            public void onResponse(Call<OTPGenValResponse> call, Response<OTPGenValResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<OTPGenValResponse> call, Throwable t) {
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
