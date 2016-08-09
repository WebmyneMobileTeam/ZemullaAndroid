package com.zemulla.android.app.api.account;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.account.resetpassword.ResetPasswordRequest;
import com.zemulla.android.app.model.account.resetpassword.ResetPasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class ResetPasswordAPI {
    private AccountAPI accountAPI;
    private APIListener apiListener;

    public ResetPasswordAPI() {

        accountAPI = ZemullaApplication.getRetrofit().create(AccountAPI.class);
    }

    public void resetPassword(final ResetPasswordRequest resetPasswordRequest, final APIListener apiListener) {
        Call<ResetPasswordResponse> loginResponseCall = accountAPI.ResetPassword(resetPasswordRequest);
        loginResponseCall.enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
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
