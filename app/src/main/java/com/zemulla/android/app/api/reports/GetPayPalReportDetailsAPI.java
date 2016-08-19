package com.zemulla.android.app.api.reports;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.ReportRequest;
import com.zemulla.android.app.topup.transaction.paypal.GetPayPalReportDetailsReponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class GetPayPalReportDetailsAPI {

    private ReportsAPI paymentAPI;
    private APIListener apiListener;
    Call<GetPayPalReportDetailsReponse> call;

    public GetPayPalReportDetailsAPI() {

        paymentAPI = ZemullaApplication.getRetrofit().create(ReportsAPI.class);
    }

    public void getSendMoneyApiReportDetailsAPI(final ReportRequest reportRequest, final APIListener apiListener) {
        this.apiListener = apiListener;
        call = paymentAPI.getPayPalReportDetails(reportRequest);
        call.enqueue(new Callback<GetPayPalReportDetailsReponse>() {
            @Override
            public void onResponse(Call<GetPayPalReportDetailsReponse> call, Response<GetPayPalReportDetailsReponse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<GetPayPalReportDetailsReponse> call, Throwable t) {
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
