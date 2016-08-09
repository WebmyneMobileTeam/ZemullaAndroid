package com.zemulla.android.app.model.account.forgotpassword;

import com.zemulla.android.app.model.base.BaseResponse;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class ForgotPasswordResponse extends BaseResponse {


    private String CallingCode;
    private String Email;
    private String HiddenMobile;
    private String Mobile;
    private String Token;
    private String UniqueID;

    public String getCallingCode() {
        return CallingCode;
    }

    public void setCallingCode(String CallingCode) {
        this.CallingCode = CallingCode;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getHiddenMobile() {
        return HiddenMobile;
    }

    public void setHiddenMobile(String HiddenMobile) {
        this.HiddenMobile = HiddenMobile;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public String getUniqueID() {
        return UniqueID;
    }

    public void setUniqueID(String UniqueID) {
        this.UniqueID = UniqueID;
    }
}
