package com.zemulla.android.app.api.payment;

import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationRequest;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by raghavthakkar on 08-08-2016.
 */
public interface PaymentAPI {

    @POST(AppConstant.TopUpTransactionChargeCalculation)
    Call<TopUpTransactionChargeCalculationResponse> GetTopUpCharge(@Body TopUpTransactionChargeCalculationRequest request);

}
