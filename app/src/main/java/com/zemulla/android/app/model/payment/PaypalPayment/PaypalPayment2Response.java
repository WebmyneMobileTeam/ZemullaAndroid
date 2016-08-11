package com.zemulla.android.app.model.payment.PaypalPayment;

import com.zemulla.android.app.model.base.BaseResponse;

/**
 * Created by sagartahelyani on 10-08-2016.
 */
public class PaypalPayment2Response extends BaseResponse {


    /**
     * Amount : 1.2678967543233E7
     * TotalCharge : 1.2678967543233E7
     * TotalPayableAmount : 1.2678967543233E7
     * TransactionDate : String content
     * USDAmount : 1.2678967543233E7
     * USDRate : 1.2678967543233E7
     * ZemullaTransID : String content
     */

    private double Amount;
    private double TotalCharge;
    private double TotalPayableAmount;
    private String TransactionDate;
    private double USDAmount;
    private double USDRate;
    private String ZemullaTransID;

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
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

    public double getUSDAmount() {
        return USDAmount;
    }

    public void setUSDAmount(double USDAmount) {
        this.USDAmount = USDAmount;
    }

    public double getUSDRate() {
        return USDRate;
    }

    public void setUSDRate(double USDRate) {
        this.USDRate = USDRate;
    }

    public String getZemullaTransID() {
        return ZemullaTransID;
    }

    public void setZemullaTransID(String ZemullaTransID) {
        this.ZemullaTransID = ZemullaTransID;
    }

    @Override
    public String toString() {
        return "PaypalPayment2Response{" +
                "Amount=" + Amount +
                ", TotalCharge=" + TotalCharge +
                ", TotalPayableAmount=" + TotalPayableAmount +
                ", TransactionDate='" + TransactionDate + '\'' +
                ", USDAmount=" + USDAmount +
                ", USDRate=" + USDRate +
                ", ZemullaTransID='" + ZemullaTransID + '\'' +
                '}';
    }
}
