package com.zemulla.android.app.model.reports.gettopupapireportdetails;

/**
 * Created by raghavthakkar on 19-08-2016.
 */
public class TopUpApiReportDetails {


    private String Amount;
    private String CreatedDate;
    private String FirstName;
    private String IsPaidSuccess;
    private String LastName;
    private String MobileNo;
    private String NationalID;
    private String PIN;
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
