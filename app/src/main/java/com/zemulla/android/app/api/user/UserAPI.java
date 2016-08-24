package com.zemulla.android.app.api.user;

import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.model.user.dashboard.GetDashboardData;
import com.zemulla.android.app.model.user.getuserdetailidwise.GetUserDetailIDWiseResponse;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by raghavthakkar on 08-08-2016.
 */
public interface UserAPI {

    @GET(AppConstant.GetWalletDetail)
    Call<GetWalletDetailResponse> GetWalletDetail(@Path("USERID") String USERID);

    @GET(AppConstant.GetUserDetailIDWise)
    Call<GetUserDetailIDWiseResponse> GetUserDetailIDWise(@Path("USERID") String USERID);

    @GET(AppConstant.GetDashboardData)
    Call<GetDashboardData> GetDashboardData(@Path("USERID") String USERID);


}
