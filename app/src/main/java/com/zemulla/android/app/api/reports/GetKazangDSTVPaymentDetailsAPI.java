package com.zemulla.android.app.api.reports;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.reports.getkazangdstvpaymentdetails.DSTVPaymentDetailsReportResponse;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.ReportRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class GetKazangDSTVPaymentDetailsAPI {

    private ReportsAPI paymentAPI;
    private APIListener apiListener;
    Call<DSTVPaymentDetailsReportResponse> call;

    public GetKazangDSTVPaymentDetailsAPI() {

        paymentAPI = ZemullaApplication.getRetrofit().create(ReportsAPI.class);
    }

    public void getSendMoneyApiReportDetailsAPI(final ReportRequest reportRequest, final APIListener apiListener) {
        this.apiListener = apiListener;
        call = paymentAPI.getKazangDSTVPaymentDetails(reportRequest);
        call.enqueue(new Callback<DSTVPaymentDetailsReportResponse>() {
            @Override
            public void onResponse(Call<DSTVPaymentDetailsReportResponse> call, Response<DSTVPaymentDetailsReportResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<DSTVPaymentDetailsReportResponse> call, Throwable t) {
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
