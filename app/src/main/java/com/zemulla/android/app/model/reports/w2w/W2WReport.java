package com.zemulla.android.app.model.reports.w2w;

/**
 * Created by raghavthakkar on 22-08-2016.
 */
public class W2WReport {


    private String CreatedDate;
    private String FirstName;
    private String LastName;
    private String PayableAmount;
    private String ReceiverMobileNumber;
    private String ReceiverName;
    private String RequestedAmount;
    private String TransactionCharge;
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

    public String getReceiverMobileNumber() {
        return ReceiverMobileNumber;
    }

    public void setReceiverMobileNumber(String ReceiverMobileNumber) {
        this.ReceiverMobileNumber = ReceiverMobileNumber;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String ReceiverName) {
        this.ReceiverName = ReceiverName;
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

    public String getZemullaTransactionID() {
        return ZemullaTransactionID;
    }

    public void setZemullaTransactionID(String ZemullaTransactionID) {
        this.ZemullaTransactionID = ZemullaTransactionID;
    }
}
