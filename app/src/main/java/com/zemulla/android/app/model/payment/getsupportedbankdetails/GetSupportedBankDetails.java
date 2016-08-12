package com.zemulla.android.app.model.payment.getsupportedbankdetails;

/**
 * Created by raghavthakkar on 10-08-2016.
 */
public class GetSupportedBankDetails {


    private String AccountHolderName;
    private String AccountNumber;
    private int BankDetailID;
    private int BankID;
    private String BankName;
    private String BankRouting;
    private String BankSWIFTCode;
    private String BankSortCode;
    private String BranchName;
    private String CreatedDate;
    private boolean IsDelete;
    private int UserID;

    public String getAccountHolderName() {
        return AccountHolderName;
    }

    public void setAccountHolderName(String AccountHolderName) {
        this.AccountHolderName = AccountHolderName;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String AccountNumber) {
        this.AccountNumber = AccountNumber;
    }

    public int getBankDetailID() {
        return BankDetailID;
    }

    public void setBankDetailID(int BankDetailID) {
        this.BankDetailID = BankDetailID;
    }

    public int getBankID() {
        return BankID;
    }

    public void setBankID(int BankID) {
        this.BankID = BankID;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
    }

    public String getBankRouting() {
        return BankRouting;
    }

    public void setBankRouting(String BankRouting) {
        this.BankRouting = BankRouting;
    }

    public String getBankSWIFTCode() {
        return BankSWIFTCode;
    }

    public void setBankSWIFTCode(String BankSWIFTCode) {
        this.BankSWIFTCode = BankSWIFTCode;
    }

    public String getBankSortCode() {
        return BankSortCode;
    }

    public void setBankSortCode(String BankSortCode) {
        this.BankSortCode = BankSortCode;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String BranchName) {
        this.BranchName = BranchName;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public boolean isIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(boolean IsDelete) {
        this.IsDelete = IsDelete;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public GetSupportedBankDetails(boolean isCustomSppinerAdater) {
        AccountHolderName = "";
        AccountNumber = "";
        BankDetailID = 0;
        BankID = 0;
        BankName = "Please Select Recipient Bank";
        BankRouting = "";
        BankSWIFTCode = "";
        BankSortCode = "";
        BranchName = "";
        CreatedDate = "";
        IsDelete = false;
        UserID = -1;
    }
}
