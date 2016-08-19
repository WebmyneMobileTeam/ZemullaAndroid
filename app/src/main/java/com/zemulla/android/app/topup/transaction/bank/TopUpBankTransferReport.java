package com.zemulla.android.app.topup.transaction.bank;

/**
 * Created by raghavthakkar on 19-08-2016.
 */
public class TopUpBankTransferReport {

    private String AccountName;
    private String AccountNumber;
    private String BankName;
    private String CreatedDate;
    private String FirstName;
    private String IsPaidSuccess;
    private String LastName;
    private String PayableAmount;
    private String RequestedAmount;
    private String TransactionCharge;
    private String ZemullaTransactionID;

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String AccountName) {
        this.AccountName = AccountName;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String AccountNumber) {
        this.AccountNumber = AccountNumber;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
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

    public String getZemullaTransactionID() {
        return ZemullaTransactionID;
    }

    public void setZemullaTransactionID(String ZemullaTransactionID) {
        this.ZemullaTransactionID = ZemullaTransactionID;
    }
}
