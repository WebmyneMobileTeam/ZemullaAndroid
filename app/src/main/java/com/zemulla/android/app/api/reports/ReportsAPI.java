package com.zemulla.android.app.api.reports;

import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.GetTopUpApiReportDetailsResponse;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.ReportRequest;
import com.zemulla.android.app.topup.transaction.bank.GetTopUpBankTransferReportDetailsResponse;
import com.zemulla.android.app.topup.transaction.paypal.GetPayPalReportDetailsReponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by raghavthakkar on 08-08-2016.
 */
public interface ReportsAPI {

    @POST(AppConstant.GetCyberSourceReportDetails)
    Call<GetTopUpApiReportDetailsResponse> getCyberSourceReportDetails(@Body ReportRequest reportRequest);

    @POST(AppConstant.GetPayPalReportDetails)
    Call<GetPayPalReportDetailsReponse> getPayPalReportDetails(@Body ReportRequest reportRequest);

    @POST(AppConstant.GetTopUpApiReportDetails)
    Call<GetTopUpApiReportDetailsResponse> getTopUpApiReportDetails(@Body ReportRequest reportRequest);

    @POST(AppConstant.GetTopUpBankTransferReportDetails)
    Call<GetTopUpBankTransferReportDetailsResponse> getTopUpBankTransferReportDetails(@Body ReportRequest reportRequest);


    @POST(AppConstant.GetSendMoneyApiReportDetails)
    Call<GetTopUpApiReportDetailsResponse> getSendMoneyApiReportDetails(@Body ReportRequest reportRequest);

}
