package com.zemulla.android.app.model.payment.PaypalPayment;

import com.zemulla.android.app.model.base.BaseResponse;

/**
 * Created by sagartahelyani on 10-08-2016.
 */
public class PaypalPayment1Response extends BaseResponse {

    /**
     * Amount : 1.2678967543233E7
     * TotalCharge : 1.2678967543233E7
     * TotalPayableAmount : 1.2678967543233E7
     * USDAmount : 1.2678967543233E7
     * USDRate : 1.2678967543233E7
     * ZemullaTransID : String content
     */

    private double Amount;
    private double TotalCharge;
    private double TotalPayableAmount;
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
}
