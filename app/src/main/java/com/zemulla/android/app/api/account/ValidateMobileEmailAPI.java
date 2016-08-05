package com.zemulla.android.app.api.account;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.validatemobileemail.ValidateMobileEmailRequest;
import com.zemulla.android.app.model.validatemobileemail.ValidateMobileEmailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class ValidateMobileEmailAPI {
    private AccountAPI accountAPI;
    private APIListener apiListener;

    public ValidateMobileEmailAPI() {

        accountAPI = ZemullaApplication.getRetrofit().create(AccountAPI.class);
    }

    public void validateMobileEmail(final ValidateMobileEmailRequest validateMobileEmailRequest, final APIListener apiListener) {
        Call<ValidateMobileEmailResponse> validateMobileEmailResponseCall = accountAPI.ValidateMoileEmail(validateMobileEmailRequest);
        validateMobileEmailResponseCall.enqueue(new Callback<ValidateMobileEmailResponse>() {
            @Override
            public void onResponse(Call<ValidateMobileEmailResponse> call, Response<ValidateMobileEmailResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<ValidateMobileEmailResponse> call, Throwable t) {
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
