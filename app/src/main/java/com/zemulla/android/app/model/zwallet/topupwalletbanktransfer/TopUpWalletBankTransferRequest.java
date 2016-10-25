package com.zemulla.android.app.model.zwallet.topupwalletbanktransfer;

/**
 * Created by raghavthakkar on 10-08-2016.
 */
public class TopUpWalletBankTransferRequest {


    private String AccountName;
    private String AccountNumber;

   // private long BankDetailID;
    private long BankID;
    private String BranchName;
    private String RejectReason;
    private double RequestedAmount;
    private long ServiceDetailID;
    private String SwiftCode;
    private double TopUpAmount;
    private long TopUpWalletID;
    private double TransactionCharge;
    private long UserID;
    private String UserRemark;
    private String VerificationCode;
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

//    public long getBankDetailID() {
//        return BankDetailID;
//    }
//
//    public void setBankDetailID(long BankDetailID) {
//        this.BankDetailID = BankDetailID;
//    }

    public long getBankID() {
        return BankID;
    }

    public void setBankID(long BankID) {
        this.BankID = BankID;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String BranchName) {
        this.BranchName = BranchName;
    }

    public String getRejectReason() {
        return RejectReason;
    }

    public void setRejectReason(String RejectReason) {
        this.RejectReason = RejectReason;
    }

    public double getRequestedAmount() {
        return RequestedAmount;
    }

    public void setRequestedAmount(double RequestedAmount) {
        this.RequestedAmount = RequestedAmount;
    }

    public long getServiceDetailID() {
        return ServiceDetailID;
    }

    public void setServiceDetailID(long ServiceDetailID) {
        this.ServiceDetailID = ServiceDetailID;
    }

    public String getSwiftCode() {
        return SwiftCode;
    }

    public void setSwiftCode(String SwiftCode) {
        this.SwiftCode = SwiftCode;
    }

    public double getTopUpAmount() {
        return TopUpAmount;
    }

    public void setTopUpAmount(double TopUpAmount) {
        this.TopUpAmount = TopUpAmount;
    }

    public long getTopUpWalletID() {
        return TopUpWalletID;
    }

    public void setTopUpWalletID(long TopUpWalletID) {
        this.TopUpWalletID = TopUpWalletID;
    }

    public double getTransactionCharge() {
        return TransactionCharge;
    }

    public void setTransactionCharge(double TransactionCharge) {
        this.TransactionCharge = TransactionCharge;
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long UserID) {
        this.UserID = UserID;
    }

    public String getUserRemark() {
        return UserRemark;
    }

    public void setUserRemark(String UserRemark) {
        this.UserRemark = UserRemark;
    }

    public String getVerificationCode() {
        return VerificationCode;
    }

    public void setVerificationCode(String VerificationCode) {
        this.VerificationCode = VerificationCode;
    }

    public String getZemullaTransactionID() {
        return ZemullaTransactionID;
    }

    public void setZemullaTransactionID(String ZemullaTransactionID) {
        this.ZemullaTransactionID = ZemullaTransactionID;
    }

    public TopUpWalletBankTransferRequest() {
        AccountName = "";
        AccountNumber = "";
      //  BankDetailID = 0;
        BankID = 0;
        BranchName = "";
        RejectReason = "";
        RequestedAmount = 0;
        ServiceDetailID = 0;
        SwiftCode = "";
        TopUpAmount = 0;
        TopUpWalletID = 0;
        TransactionCharge = 0;
        UserID = 0;
        UserRemark = "";
        VerificationCode = "";
        ZemullaTransactionID = "";
    }
}
