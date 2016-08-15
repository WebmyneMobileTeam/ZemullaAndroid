package com.zemulla.android.app.api.kazang;

import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.model.kazang.getkazangproductplan.GetKazangProductPlanRequest;
import com.zemulla.android.app.model.kazang.getkazangproductplan.GetKazangProductPlanResponse;
import com.zemulla.android.app.model.kazang.getkazangproductprovider.GetKazangProductProviderResponse;
import com.zemulla.android.app.model.kazang.kazangairtime.KazangAirtimeRequest;
import com.zemulla.android.app.model.kazang.kazangairtime.KazangAirtimeResponse;
import com.zemulla.android.app.model.kazang.kazangdirectrecharge.KazangDirectRechargeRequest;
import com.zemulla.android.app.model.kazang.kazangdirectrecharge.KazangDirectRechargeResponse;
import com.zemulla.android.app.model.kazang.kazangtestelectricity.KazangElectricityRequest;
import com.zemulla.android.app.model.kazang.kazangtestelectricity.KazangElectricityResponse;
import com.zemulla.android.app.model.kazang.kazangtestelectricity.KazangTestElectricityResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by raghavthakkar on 08-08-2016.
 */
public interface KazangAPI {

    @GET(AppConstant.GetKazangProductProvider)
    Call<GetKazangProductProviderResponse> getKazangProductProvider(@Path("PRODUCTCATEGORY") String PRODUCTCATEGORY);

    @POST(AppConstant.GetKazangProductPlan)
    Call<GetKazangProductPlanResponse> getKazangProductPlan(@Body GetKazangProductPlanRequest getKazangProductPlanRequest);

    @POST(AppConstant.KazangAirtime)
    Call<KazangAirtimeResponse> getKazangAirtime(@Body KazangAirtimeRequest kazangAirtimeRequest);

    @POST(AppConstant.KazangDirectRecharge)
    Call<KazangDirectRechargeResponse> getKazangDirectRecharge(@Body KazangDirectRechargeRequest kazangDirectRechargeRequest);

    @GET(AppConstant.KazangTestElectricity)
    Call<KazangTestElectricityResponse> getKazangTestElectricity(@Path("METERNUMBER") String METERNUMBER);

    @POST(AppConstant.KazangElectricity)
    Call<KazangElectricityResponse> getKazangElectricity(@Body KazangElectricityRequest kazangElectricityRequest);


}
