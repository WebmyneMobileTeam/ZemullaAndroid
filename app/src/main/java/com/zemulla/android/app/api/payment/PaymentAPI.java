package com.zemulla.android.app.api.payment;

import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.FundTransferTransactionChargeCalculationResponse;
import com.zemulla.android.app.model.payment.PaypalPayment.PaypalPayment1Request;
import com.zemulla.android.app.model.payment.PaypalPayment.PaypalPayment1Response;
import com.zemulla.android.app.model.payment.PaypalPayment.PaypalPayment2Request;
import com.zemulla.android.app.model.payment.PaypalPayment.PaypalPayment2Response;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationRequest;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationResponse;
import com.zemulla.android.app.model.payment.getsupportedbankdetails.GetSupportedBankDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by raghavthakkar on 08-08-2016.
 */
public interface PaymentAPI {

    @POST(AppConstant.TopUpTransactionChargeCalculation)
    Call<TopUpTransactionChargeCalculationResponse> GetTopUpCharge(@Body TopUpTransactionChargeCalculationRequest request);

    @POST(AppConstant.TransactionChargeCalculation)
    Call<FundTransferTransactionChargeCalculationResponse> GetFundTransferCharge(@Body TopUpTransactionChargeCalculationRequest request);

    @GET(AppConstant.GetSupportedBankDetails)
    Call<GetSupportedBankDetailsResponse> GetSupportedBankDetails();


    @POST(AppConstant.PaypalPayment1)
    Call<PaypalPayment1Response> GetPayPalCharge(@Body PaypalPayment1Request request);

    @POST(AppConstant.PaypalPayment2)
    Call<PaypalPayment2Response> ProcessPaypal(@Body PaypalPayment2Request request);

}
