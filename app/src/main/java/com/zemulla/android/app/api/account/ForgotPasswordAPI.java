package com.zemulla.android.app.api.account;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.account.forgotpassword.ForgotPasswordRequest;
import com.zemulla.android.app.model.account.forgotpassword.ForgotPasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class ForgotPasswordAPI {
    private AccountAPI accountAPI;
    private APIListener apiListener;

    public ForgotPasswordAPI() {

        accountAPI = ZemullaApplication.getRetrofit().create(AccountAPI.class);
    }

    public void forgotPassword(final ForgotPasswordRequest forgotPasswordRequest, final APIListener apiListener) {
        Call<ForgotPasswordResponse> loginResponseCall = accountAPI.ForgotPassword(forgotPasswordRequest);
        loginResponseCall.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
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
