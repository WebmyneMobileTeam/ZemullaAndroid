package com.zemulla.android.app.topup.transaction.paypal;

/**
 * Created by raghavthakkar on 19-08-2016.
 */
public class PayPalReport {


    private String CreatedDate;
    private String FirstName;
    private String IsPaidSuccess;
    private String LastName;
    private String PayableAmount;
    private String RequestedAmount;
    private String TransactionCharge;
    private String USDAmount;
    private String USDRate;
    private String ZemullaTransactionID;

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getIsPaidSuccess() {
        return IsPaidSuccess;
    }

    public void setIsPaidSuccess(String IsPaidSuccess) {
        this.IsPaidSuccess = IsPaidSuccess;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getPayableAmount() {
        return PayableAmount;
    }

    public void setPayableAmount(String PayableAmount) {
        this.PayableAmount = PayableAmount;
    }

    public String getRequestedAmount() {
        return RequestedAmount;
    }

    public void setRequestedAmount(String RequestedAmount) {
        this.RequestedAmount = RequestedAmount;
    }

    public String getTransactionCharge() {
        return TransactionCharge;
    }

    public void setTransactionCharge(String TransactionCharge) {
        this.TransactionCharge = TransactionCharge;
    }

    public String getUSDAmount() {
        return USDAmount;
    }

    public void setUSDAmount(String USDAmount) {
        this.USDAmount = USDAmount;
    }

    public String getUSDRate() {
        return USDRate;
    }

    public void setUSDRate(String USDRate) {
        this.USDRate = USDRate;
    }

    public String getZemullaTransactionID() {
        return ZemullaTransactionID;
    }

    public void setZemullaTransactionID(String ZemullaTransactionID) {
        this.ZemullaTransactionID = ZemullaTransactionID;
    }
}
