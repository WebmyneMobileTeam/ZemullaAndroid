package com.zemulla.android.app.api.reports;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.reports.getkazangelectricitydetails.ElectricityDetailsReportResponse;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.ReportRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class GetKazangElectricityDetailsAPI {

    private ReportsAPI paymentAPI;
    private APIListener apiListener;
    Call<ElectricityDetailsReportResponse> call;

    public GetKazangElectricityDetailsAPI() {

        paymentAPI = ZemullaApplication.getRetrofit().create(ReportsAPI.class);
    }

    public void getSendMoneyApiReportDetailsAPI(final ReportRequest reportRequest, final APIListener apiListener) {
        this.apiListener = apiListener;
        call = paymentAPI.getKazangElectricityDetails(reportRequest);
        call.enqueue(new Callback<ElectricityDetailsReportResponse>() {
            @Override
            public void onResponse(Call<ElectricityDetailsReportResponse> call, Response<ElectricityDetailsReportResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<ElectricityDetailsReportResponse> call, Throwable t) {
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
