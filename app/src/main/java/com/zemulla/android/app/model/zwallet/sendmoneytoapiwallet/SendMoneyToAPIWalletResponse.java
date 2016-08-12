package com.zemulla.android.app.model.zwallet.sendmoneytoapiwallet;

import com.zemulla.android.app.model.base.BaseResponse;

/**
 * Created by raghavthakkar on 11-08-2016.
 */
public class SendMoneyToAPIWalletResponse extends BaseResponse {



    private double Amount;
    private String MobileNo;
    private double TotalCharge;
    private double TotalPayableAmount;
    private String TransactionDate;
    private String ZemullaTransactionID;

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

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(String TransactionDate) {
        this.TransactionDate = TransactionDate;
    }

    public String getZemullaTransactionID() {
        return ZemullaTransactionID;
    }

    public void setZemullaTransactionID(String ZemullaTransactionID) {
        this.ZemullaTransactionID = ZemullaTransactionID;
    }
}
