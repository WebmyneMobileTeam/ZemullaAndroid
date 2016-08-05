package com.zemulla.android.app.api.account;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.otpgenvaltemporary.OTPGenValTemporaryRequest;
import com.zemulla.android.app.model.otpgenvaltemporary.OTPGenValTemporaryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class OTPGenValTemporaryAPI {
    private AccountAPI accountAPI;
    private APIListener apiListener;

    public OTPGenValTemporaryAPI() {

        accountAPI = ZemullaApplication.getRetrofit().create(AccountAPI.class);
    }

    public void otpGenValTemporary(final OTPGenValTemporaryRequest otpGenValTemporaryRequest, final APIListener apiListener) {
        Call<OTPGenValTemporaryResponse> loginResponseCall = accountAPI.OTPGenValTemporary(otpGenValTemporaryRequest);
        loginResponseCall.enqueue(new Callback<OTPGenValTemporaryResponse>() {
            @Override
            public void onResponse(Call<OTPGenValTemporaryResponse> call, Response<OTPGenValTemporaryResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<OTPGenValTemporaryResponse> call, Throwable t) {
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
