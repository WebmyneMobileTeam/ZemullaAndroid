package com.zemulla.android.app.model.zwallet.sendmoneyw2wad;

import com.zemulla.android.app.model.base.BaseSmallCaseResponse;

/**
 * Created by raghavthakkar on 11-08-2016.
 */
public class SendMoneyW2WResponse extends BaseSmallCaseResponse{



    private String ZemullaTransID;

    public String getZemullaTransID() {
        return ZemullaTransID;
    }

    public void setZemullaTransID(String ZemullaTransID) {
        this.ZemullaTransID = ZemullaTransID;
    }
}
