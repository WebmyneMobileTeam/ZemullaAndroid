package com.zemulla.android.app.api.account;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.account.changeemail.ChangeEmailRequest;
import com.zemulla.android.app.model.account.changeemail.ChangeEmailResponse;

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

    public void changeEmail(final ChangeEmailRequest changeEmailRequest, final APIListener apiListener) {
        Call<ChangeEmailResponse> changeEmailResponseCall = accountAPI.ChangeEmailAD(changeEmailRequest);
        changeEmailResponseCall.enqueue(new Callback<ChangeEmailResponse>() {
            @Override
            public void onResponse(Call<ChangeEmailResponse> call, Response<ChangeEmailResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<ChangeEmailResponse> call, Throwable t) {
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
