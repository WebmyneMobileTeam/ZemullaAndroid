package com.zemulla.android.app.api.zwallet;

import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.model.zwallet.DynamicTextResponse;
import com.zemulla.android.app.model.zwallet.topupwallet.TopUpRequest;
import com.zemulla.android.app.model.zwallet.topupwallet.TopUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by raghavthakkar on 08-08-2016.
 */
public interface ZWalletAPI {

    @POST(AppConstant.TopupAPIWallet)
    Call<TopUpResponse> GetTopUpCharge(@Body TopUpRequest request);

    @GET(AppConstant.GetDynamicText)
    Call<DynamicTextResponse> GetDynamicText(@Path("APIMASTERID") int masterID);

}
