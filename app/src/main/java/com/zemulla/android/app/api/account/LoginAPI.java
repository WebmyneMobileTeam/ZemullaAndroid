package com.zemulla.android.app.api.account;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.account.AccountAPI;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.login.LoginRequest;
import com.zemulla.android.app.model.login.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class LoginAPI {
    private AccountAPI accountAPI;
    private APIListener apiListener;

    public LoginAPI() {

        accountAPI = ZemullaApplication.getRetrofit().create(AccountAPI.class);
    }

    public void login(final LoginRequest loginRequest, final APIListener apiListener) {
        Call<LoginResponse> loginResponseCall = accountAPI.Login(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
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
