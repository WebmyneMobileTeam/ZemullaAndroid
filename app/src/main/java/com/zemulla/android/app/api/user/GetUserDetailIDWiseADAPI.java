package com.zemulla.android.app.api.user;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.user.getuserdetailidwise.GetUserDetailIDWiseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class GetUserDetailIDWiseADAPI {
    private UserAPI userAPI;
    private APIListener apiListener;
    Call<GetUserDetailIDWiseResponse> getUserDetailIDWiseResponseCall;

    public GetUserDetailIDWiseADAPI() {

        userAPI = ZemullaApplication.getRetrofit().create(UserAPI.class);
    }

    public void getUserDetailIDWiseAD(final String UserID, final APIListener apiListener) {
        getUserDetailIDWiseResponseCall = userAPI.GetUserDetailIDWise(UserID);
        getUserDetailIDWiseResponseCall.enqueue(new Callback<GetUserDetailIDWiseResponse>() {
            @Override
            public void onResponse(Call<GetUserDetailIDWiseResponse> call, Response<GetUserDetailIDWiseResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<GetUserDetailIDWiseResponse> call, Throwable t) {
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
