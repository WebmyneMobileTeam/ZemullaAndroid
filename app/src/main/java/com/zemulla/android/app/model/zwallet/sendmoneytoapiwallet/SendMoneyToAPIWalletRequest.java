package com.zemulla.android.app.model.zwallet.sendmoneytoapiwallet;

/**
 * Created by raghavthakkar on 11-08-2016.
 */
public class SendMoneyToAPIWalletRequest {

    private double Amount;
    private String MobileNo;
    private long ServiceDetailID;
    private double TotalCharge;
    private double TotalPayableAmount;
    private long UserID;
    private String VerificationCode;

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
