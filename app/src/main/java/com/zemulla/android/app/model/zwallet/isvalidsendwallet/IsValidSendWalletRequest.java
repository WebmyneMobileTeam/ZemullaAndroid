package com.zemulla.android.app.model.zwallet.isvalidsendwallet;

/**
 * Created by raghavthakkar on 11-08-2016.
 */
public class IsValidSendWalletRequest {

    private String CallingCode;
    private String Mobile;
    private long UserID;

    public String getCallingCode() {
        return CallingCode;
    }

    public void setCallingCode(String CallingCode) {
        this.CallingCode = CallingCode;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long UserID) {
        this.UserID = UserID;
    }
}
