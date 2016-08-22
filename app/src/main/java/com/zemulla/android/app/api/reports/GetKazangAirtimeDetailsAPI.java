package com.zemulla.android.app.api.reports;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.reports.getkazangairtimedetails.AirtimeDetailsResponse;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.ReportRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class GetKazangAirtimeDetailsAPI {

    private ReportsAPI paymentAPI;
    private APIListener apiListener;
    Call<AirtimeDetailsResponse> call;

    public GetKazangAirtimeDetailsAPI() {

        paymentAPI = ZemullaApplication.getRetrofit().create(ReportsAPI.class);
    }

    public void getSendMoneyApiReportDetailsAPI(final ReportRequest reportRequest, final APIListener apiListener) {
        this.apiListener = apiListener;
        call = paymentAPI.getKazangAirtimeDetails(reportRequest);
        call.enqueue(new Callback<AirtimeDetailsResponse>() {
            @Override
            public void onResponse(Call<AirtimeDetailsResponse> call, Response<AirtimeDetailsResponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<AirtimeDetailsResponse> call, Throwable t) {
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
