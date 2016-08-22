package com.zemulla.android.app.api.reports;

import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.model.reports.getkazangairtimedetails.AirtimeDetailsResponse;
import com.zemulla.android.app.model.reports.getkazangdirectrechargedetails.DirectRechargeDetailsReportResponse;
import com.zemulla.android.app.model.reports.getkazangdstvpaymentdetails.DSTVPaymentDetailsReportResponse;
import com.zemulla.android.app.model.reports.getkazangelectricitydetails.ElectricityDetailsReportResponse;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.GetTopUpApiReportDetailsResponse;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.ReportRequest;
import com.zemulla.android.app.model.reports.w2w.W2WReportResponse;
import com.zemulla.android.app.topup.transaction.bank.GetTopUpBankTransferReportDetailsResponse;
import com.zemulla.android.app.topup.transaction.cybersource.GetCyberSourceReportDetailsResponse;
import com.zemulla.android.app.topup.transaction.paypal.GetPayPalReportDetailsReponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by raghavthakkar on 08-08-2016.
 */
public interface ReportsAPI {

    @POST(AppConstant.GetCyberSourceReportDetails)
    Call<GetCyberSourceReportDetailsResponse> getCyberSourceReportDetails(@Body ReportRequest reportRequest);

    @POST(AppConstant.GetPayPalReportDetails)
    Call<GetPayPalReportDetailsReponse> getPayPalReportDetails(@Body ReportRequest reportRequest);

    @POST(AppConstant.GetTopUpApiReportDetails)
    Call<GetTopUpApiReportDetailsResponse> getTopUpApiReportDetails(@Body ReportRequest reportRequest);

    @POST(AppConstant.GetTopUpBankTransferReportDetails)
    Call<GetTopUpBankTransferReportDetailsResponse> getTopUpBankTransferReportDetails(@Body ReportRequest reportRequest);


    @POST(AppConstant.GetSendMoneyApiReportDetails)
    Call<GetTopUpApiReportDetailsResponse> getSendMoneyApiReportDetails(@Body ReportRequest reportRequest);


    @POST(AppConstant.GetSendMoneyW2WReportDetails)
    Call<W2WReportResponse> getSendMoneyW2WReportDetails(@Body ReportRequest reportRequest);

    @POST(AppConstant.GetSendMoneyBTReportDetails)
    Call<GetTopUpBankTransferReportDetailsResponse> getSendMoneyBTReportDetails(@Body ReportRequest reportRequest);

    @POST(AppConstant.GetKazangAirtimeDetails)
    Call<AirtimeDetailsResponse> getKazangAirtimeDetails(@Body ReportRequest reportRequest);

    @POST(AppConstant.GetKazangDirectRechargeDetails)
    Call<DirectRechargeDetailsReportResponse> getKazangDirectRechargeDetails(@Body ReportRequest reportRequest);

    @POST(AppConstant.GetKazangDSTVPaymentDetails)
    Call<DSTVPaymentDetailsReportResponse> getKazangDSTVPaymentDetails(@Body ReportRequest reportRequest);

    @POST(AppConstant.GetKazangElectricityDetails)
    Call<ElectricityDetailsReportResponse> getKazangElectricityDetails(@Body ReportRequest reportRequest);


}
