package com.zemulla.android.app.home.model;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.account.AccountAPI;
import com.zemulla.android.app.base.ZemullaApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 05-08-2016.
 */
public class ProfileAPI {

    private AccountAPI accountAPI;
    private APIListener apiListener;

    public ProfileAPI() {
        accountAPI = ZemullaApplication.getRetrofit().create(AccountAPI.class);

    }

    public void getProfile(String userId, final APIListener apiListener) {
        Call<ProfileResponse> fullProfileCall = accountAPI.UserProfile(userId);
        fullProfileCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
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
