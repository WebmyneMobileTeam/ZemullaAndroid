package com.zemulla.android.app.model.account.otpgenvaltemporary;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class OTPGenValRequest {


    private String CallingCode;
    private String Email;
    private long EmailTempleteID;
    private String Mobile;
    private String UniqueID;
    private String VerificationCode;

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

    public long getEmailTempleteID() {
        return EmailTempleteID;
    }

    public void setEmailTempleteID(long EmailTempleteID) {
        this.EmailTempleteID = EmailTempleteID;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getUniqueID() {
        return UniqueID;
    }

    public void setUniqueID(String UniqueID) {
        this.UniqueID = UniqueID;
    }

    public String getVerificationCode() {
        return VerificationCode;
    }

    public void setVerificationCode(String VerificationCode) {
        this.VerificationCode = VerificationCode;
    }


    public OTPGenValRequest() {
        CallingCode = "";
        Email = "";
        EmailTempleteID = 0;
        Mobile = "";
        UniqueID = "";
        VerificationCode = "";
    }
}
