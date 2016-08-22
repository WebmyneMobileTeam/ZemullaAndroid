package com.zemulla.android.app.topup.transaction.cybersource;

/**
 * Created by raghavthakkar on 22-08-2016.
 */
public class CyberSourceReport {

    private String Amount;
    private String CreatedDate;
    private String FirstName;
    private String IsPaidSuccess;
    private String LastName;
    private String TotalCharge;
    private String TotalPayableAmount;
    private String ZemullaTransactionID;

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

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

    public String getTotalCharge() {
        return TotalCharge;
    }

    public void setTotalCharge(String TotalCharge) {
        this.TotalCharge = TotalCharge;
    }

    public String getTotalPayableAmount() {
        return TotalPayableAmount;
    }

    public void setTotalPayableAmount(String TotalPayableAmount) {
        this.TotalPayableAmount = TotalPayableAmount;
    }

    public String getZemullaTransactionID() {
        return ZemullaTransactionID;
    }

    public void setZemullaTransactionID(String ZemullaTransactionID) {
        this.ZemullaTransactionID = ZemullaTransactionID;
    }
}
