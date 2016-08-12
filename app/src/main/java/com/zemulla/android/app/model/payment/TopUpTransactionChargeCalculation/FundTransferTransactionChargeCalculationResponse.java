package com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation;

import com.zemulla.android.app.model.base.BaseResponse;

/**
 * Created by sagartahelyani on 09-08-2016.
 */
public class FundTransferTransactionChargeCalculationResponse extends BaseResponse {

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

    public double getTotalCharge() {
        return TotalCharge;
    }

    public void setTotalCharge(double totalCharge) {
        TotalCharge = totalCharge;
    }

    public double getTotalPayableAmount() {
        return TotalPayableAmount;
    }

    public void setTotalPayableAmount(double totalPayableAmount) {
        TotalPayableAmount = totalPayableAmount;
    }

    private double TotalCharge;
    private double TotalPayableAmount;

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


    @Override
    public String toString() {
        return "FundTransferTransactionChargeCalculationResponse{" +
                "Amount=" + Amount +
                ", FixedAmount=" + FixedAmount +
                ", Percentage=" + Percentage +
                ", TotalCharge=" + TotalCharge +
                ", TotalPayableAmount=" + TotalPayableAmount +
                '}';
    }
}
