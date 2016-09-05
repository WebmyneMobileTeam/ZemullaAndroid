package com.zemulla.android.app.model.payment.cybersourcepayment1;

import com.zemulla.android.app.model.base.BaseResponse;

/**
 * Created by raghavthakkar on 02-09-2016.
 */
public class CybersourcePayment1Response extends BaseResponse {

    private String ZemullaTransID;

    public String getZemullaTransID() {
        return ZemullaTransID;
    }

    public void setZemullaTransID(String ZemullaTransID) {
        this.ZemullaTransID = ZemullaTransID;
    }
}
