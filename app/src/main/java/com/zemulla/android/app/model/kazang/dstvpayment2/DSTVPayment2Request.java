package com.zemulla.android.app.model.kazang.dstvpayment2;

/**
 * Created by raghavthakkar on 17-08-2016.
 */
public class DSTVPayment2Request {

    private double Amount;
    private long ServiceDetailID;
    private double TotalCharge;
    private double TotalPayableAmount;
    private long UserID;
    private String VerificationCode;
    private String ZemullaTransactinoID;
    private String confirmation_number;
    private String product_id;

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
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

    public String getZemullaTransactinoID() {
        return ZemullaTransactinoID;
    }

    public void setZemullaTransactinoID(String ZemullaTransactinoID) {
        this.ZemullaTransactinoID = ZemullaTransactinoID;
    }

    public String getConfirmation_number() {
        return confirmation_number;
    }

    public void setConfirmation_number(String confirmation_number) {
        this.confirmation_number = confirmation_number;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
