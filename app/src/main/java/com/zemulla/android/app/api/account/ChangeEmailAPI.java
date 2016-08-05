package com.zemulla.android.app.api.account;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.account.AccountAPI;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.registration.RegistrationRequest;
import com.zemulla.android.app.model.registration.RegistrationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class ChangeEmailAPI {
    private AccountAPI accountAPI;
    private APIListener apiListener;

    public ChangeEmailAPI() {

        accountAPI = ZemullaApplication.getRetrofit().create(AccountAPI.class);
    }

    public void signUp(final RegistrationRequest registrationRequest, final APIListener apiListener) {
        Call<RegistrationResponse> loginResponseCall = accountAPI.Registration(registrationRequest);
        loginResponseCall.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
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
