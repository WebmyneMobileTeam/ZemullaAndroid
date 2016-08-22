package com.zemulla.android.app.api.reports;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.reports.w2w.W2WReportResponse;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.ReportRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class GetW2WReportDetailsAPI {

    private ReportsAPI paymentAPI;
    private APIListener apiListener;
    Call<W2WReportResponse> call;

    public GetW2WReportDetailsAPI() {

        paymentAPI = ZemullaApplication.getRetrofit().create(ReportsAPI.class);
    }

    public void getSendMoneyApiReportDetailsAPI(final ReportRequest reportRequest, final APIListener apiListener) {
        this.apiListener = apiListener;
        call = paymentAPI.getSendMoneyW2WReportDetails(reportRequest);
        call.enqueue(new Callback<W2WReportResponse>() {
            @Override
            public void onResponse(Call<W2WReportResponse> call, Response<W2WReportResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<W2WReportResponse> call, Throwable t) {
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
