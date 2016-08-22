package com.zemulla.android.app.api.reports;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.reports.getkazangdirectrechargedetails.DirectRechargeDetailsReportResponse;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.ReportRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class GetKazangDirectRechargeDetailsAPI {

    private ReportsAPI paymentAPI;
    private APIListener apiListener;
    Call<DirectRechargeDetailsReportResponse> call;

    public GetKazangDirectRechargeDetailsAPI() {

        paymentAPI = ZemullaApplication.getRetrofit().create(ReportsAPI.class);
    }

    public void getSendMoneyApiReportDetailsAPI(final ReportRequest reportRequest, final APIListener apiListener) {
        this.apiListener = apiListener;
        call = paymentAPI.getKazangDirectRechargeDetails(reportRequest);
        call.enqueue(new Callback<DirectRechargeDetailsReportResponse>() {
            @Override
            public void onResponse(Call<DirectRechargeDetailsReportResponse> call, Response<DirectRechargeDetailsReportResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<DirectRechargeDetailsReportResponse> call, Throwable t) {
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
