package com.zemulla.android.app.model.forgotpassword;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class ForgotPasswordRequest {


    private String CallingCode;
    private String Email;
    private String Mobile;

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

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }
}
