package com.zemulla.android.app.model.otpgenvaltemporary;

import com.zemulla.android.app.model.base.BaseResponse;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class OTPGenValTemporaryResponse extends BaseResponse{



    private String HiddenMobile;
    private String UniqueID;

    public String getHiddenMobile() {
        return HiddenMobile;
    }

    public void setHiddenMobile(String HiddenMobile) {
        this.HiddenMobile = HiddenMobile;
    }

    public String getUniqueID() {
        return UniqueID;
    }

    public void setUniqueID(String UniqueID) {
        this.UniqueID = UniqueID;
    }
}
