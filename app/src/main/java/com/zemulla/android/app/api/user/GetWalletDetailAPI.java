package com.zemulla.android.app.api.user;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class GetWalletDetailAPI {
    private UserAPI userAPI;
    private APIListener apiListener;

    public GetWalletDetailAPI() {

        userAPI = ZemullaApplication.getRetrofit().create(UserAPI.class);
    }

    public void getWalletDetail(final String UserID, final APIListener apiListener) {
        Call<GetWalletDetailResponse> loginResponseCall = userAPI.GetWalletDetail(UserID);
        loginResponseCall.enqueue(new Callback<GetWalletDetailResponse>() {
            @Override
            public void onResponse(Call<GetWalletDetailResponse> call, Response<GetWalletDetailResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<GetWalletDetailResponse> call, Throwable t) {
                apiListener.onFailure(call, t);
            }
        });

    }


    public void onDestory() {
        if (userAPI != null) {
            userAPI = null;
        }
        if (apiListener != null) {
            apiListener = null;
        }
    }
}
