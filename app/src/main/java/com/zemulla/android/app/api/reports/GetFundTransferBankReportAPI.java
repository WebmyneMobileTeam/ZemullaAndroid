package com.zemulla.android.app.api.reports;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.ReportRequest;
import com.zemulla.android.app.topup.transaction.bank.GetTopUpBankTransferReportDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class GetFundTransferBankReportAPI {

    private ReportsAPI paymentAPI;
    private APIListener apiListener;
    Call<GetTopUpBankTransferReportDetailsResponse> call;

    public GetFundTransferBankReportAPI() {

        paymentAPI = ZemullaApplication.getRetrofit().create(ReportsAPI.class);
    }

    public void getFundTransferBankReportAPI(final ReportRequest reportRequest, final APIListener apiListener) {
        this.apiListener = apiListener;
        call = paymentAPI.getSendMoneyBTReportDetails(reportRequest);
        call.enqueue(new Callback<GetTopUpBankTransferReportDetailsResponse>() {
            @Override
            public void onResponse(Call<GetTopUpBankTransferReportDetailsResponse> call, Response<GetTopUpBankTransferReportDetailsResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<GetTopUpBankTransferReportDetailsResponse> call, Throwable t) {
                apiListener.onFailure(call, t);
            }
        });

    }

    public void onDestory() {
        if (paymentAPI != null) {
            paymentAPI = null;
        }
        if (apiListener != null) {
            apiListener = null;
        }
    }
}
