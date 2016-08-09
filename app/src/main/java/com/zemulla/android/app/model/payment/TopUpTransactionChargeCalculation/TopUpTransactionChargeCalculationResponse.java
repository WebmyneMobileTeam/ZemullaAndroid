package com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation;

import com.zemulla.android.app.model.base.BaseResponse;

/**
 * Created by sagartahelyani on 09-08-2016.
 */
public class TopUpTransactionChargeCalculationResponse extends BaseResponse {

    /**
     * Amount : 1.2678967543233E7
     * FixedAmount : 1.2678967543233E7
     * Percentage : 1.2678967543233E7
     * TopUpAmount : 1.2678967543233E7
     * TotalCharge : 1.2678967543233E7
     */

    private double Amount;
    private double FixedAmount;
    private double Percentage;
    private double TopUpAmount;
    private double TotalCharge;

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public double getFixedAmount() {
        return FixedAmount;
    }

    public void setFixedAmount(double FixedAmount) {
        this.FixedAmount = FixedAmount;
    }

    public double getPercentage() {
        return Percentage;
    }

    public void setPercentage(double Percentage) {
        this.Percentage = Percentage;
    }

    public double getTopUpAmount() {
        return TopUpAmount;
    }

    public void setTopUpAmount(double TopUpAmount) {
        this.TopUpAmount = TopUpAmount;
    }

    public double getTotalCharge() {
        return TotalCharge;
    }

    public void setTotalCharge(double TotalCharge) {
        this.TotalCharge = TotalCharge;
    }
}
