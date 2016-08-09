package com.zemulla.android.app.model.zwallet.topupwallet;

/**
 * Created by sagartahelyani on 09-08-2016.
 */
public class TopUpRequest {

    /**
     * Amount : 1.2678967543233E7
     * MobileNo : String content
     * NationalID : String content
     * PIN : String content
     * ServiceDetailID : 9223372036854775807
     * TotalCharge : 1.2678967543233E7
     * TotalPayableAmount : 1.2678967543233E7
     * UserID : 9223372036854775807
     * VerificationCode : String content
     */

    private double Amount;
    private String MobileNo;
    private String NationalID;
    private String PIN;
    private long ServiceDetailID;
    private double TotalCharge;
    private double TotalPayableAmount;
    private long UserID;
    private String VerificationCode;

    public TopUpRequest() {
        Amount = 0;
        MobileNo = "";
        NationalID = "";
        this.PIN = "";
        ServiceDetailID = 0;
        TotalCharge = 0;
        TotalPayableAmount = 0;
        UserID = 0;
        VerificationCode = "";
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String MobileNo) {
        this.MobileNo = MobileNo;
    }

    public String getNationalID() {
        return NationalID;
    }

    public void setNationalID(String NationalID) {
        this.NationalID = NationalID;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public long getServiceDetailID() {
        return ServiceDetailID;
    }

    public void setServiceDetailID(long ServiceDetailID) {
        this.ServiceDetailID = ServiceDetailID;
    }

    public double getTotalCharge() {
        return TotalCharge;
    }

    public void setTotalCharge(double TotalCharge) {
        this.TotalCharge = TotalCharge;
    }

    public double getTotalPayableAmount() {
        return TotalPayableAmount;
    }

    public void setTotalPayableAmount(double TotalPayableAmount) {
        this.TotalPayableAmount = TotalPayableAmount;
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long UserID) {
        this.UserID = UserID;
    }

    public String getVerificationCode() {
        return VerificationCode;
    }

    public void setVerificationCode(String VerificationCode) {
        this.VerificationCode = VerificationCode;
    }
}
