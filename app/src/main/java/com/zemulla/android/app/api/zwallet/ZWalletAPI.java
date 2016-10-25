package com.zemulla.android.app.api.zwallet;

import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.model.zwallet.DynamicTextResponse;
import com.zemulla.android.app.model.zwallet.RecepientBankListResponse;
import com.zemulla.android.app.model.zwallet.isvalidsendwallet.IsValidSendWalletRequest;
import com.zemulla.android.app.model.zwallet.isvalidsendwallet.IsValidSendWalletResponse;
import com.zemulla.android.app.model.zwallet.sendmoneybanttransfer.SendMoneyBantTransferRequest;
import com.zemulla.android.app.model.zwallet.sendmoneybanttransfer.SendMoneyBantTransferResponse;
import com.zemulla.android.app.model.zwallet.sendmoneytoapiwallet.SendMoneyToAPIWalletRequest;
import com.zemulla.android.app.model.zwallet.sendmoneytoapiwallet.SendMoneyToAPIWalletResponse;
import com.zemulla.android.app.model.zwallet.sendmoneyw2wad.SendMoneyW2WRequest;
import com.zemulla.android.app.model.zwallet.sendmoneyw2wad.SendMoneyW2WResponse;
import com.zemulla.android.app.model.zwallet.topupwallet.TopUpRequest;
import com.zemulla.android.app.model.zwallet.topupwallet.TopUpResponse;
import com.zemulla.android.app.model.zwallet.topupwalletbanktransfer.TopUpWalletBankTransferRequest;
import com.zemulla.android.app.model.zwallet.topupwalletbanktransfer.TopUpWalletBankTransferResponse;

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
    Call<TopUpResponse> getTopUpCharge(@Body TopUpRequest request);

    @POST(AppConstant.SendMoneyToAPIWallet)
    Call<SendMoneyToAPIWalletResponse> getSendMoneyToAPIWalletRequest(@Body SendMoneyToAPIWalletRequest request);

    @GET(AppConstant.GetDynamicText)
    Call<DynamicTextResponse> getDynamicText(@Path("APIMASTERID") int masterID);

    @GET(AppConstant.BANKLISTAD)
    Call<RecepientBankListResponse> getBanklist();

    @POST(AppConstant.TopUpWalletBankTransfer)
    Call<TopUpWalletBankTransferResponse> getTopUpWalletBankTransfer(@Body TopUpWalletBankTransferRequest topUpWalletBankTransferRequest);

    @POST(AppConstant.SendMoneyBantTransfer)
    Call<SendMoneyBantTransferResponse> sendMoneyBantTransfer(@Body SendMoneyBantTransferRequest sendMoneyBantTransferRequest);

    @POST(AppConstant.IsValidSendWallet)
    Call<IsValidSendWalletResponse> isValidSendWallet(@Body IsValidSendWalletRequest isValidSendWalletRequest);

    @POST(AppConstant.SendMoneyW2W)
    Call<SendMoneyW2WResponse> sendMoneyW2W(@Body SendMoneyW2WRequest sendMoneyW2WRequest);

}
