package com.zemulla.android.app.model.payment.cybersourcepayment1;

/**
 * Created by raghavthakkar on 02-09-2016.
 */
public class CybersourcePayment1Request {


    private double Amount;
    private long ServiceDetailsID;
    private double TotalCharge;
    private double TotalPayableAmount;
    private long UserID;

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public long getServiceDetailsID() {
        return ServiceDetailsID;
    }

    public void setServiceDetailsID(long ServiceDetailsID) {
        this.ServiceDetailsID = ServiceDetailsID;
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
}
