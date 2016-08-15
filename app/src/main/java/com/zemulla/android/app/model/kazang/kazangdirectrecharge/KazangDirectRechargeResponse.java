package com.zemulla.android.app.model.kazang.kazangdirectrecharge;

import com.zemulla.android.app.model.base.BaseResponse;

/**
 * Created by raghavthakkar on 15-08-2016.
 */
public class KazangDirectRechargeResponse extends BaseResponse {

    private String CretaedDate;
    private double TotalCharge;
    private double TotalPayableAmount;
    private String ZemullaTransactinoID;
    private String amount;
    private String msisdn;
    private String product;
    private String response_code;
    private String response_message;
    private String transaction_reference;

    public String getCretaedDate() {
        return CretaedDate;
    }

    public void setCretaedDate(String CretaedDate) {
        this.CretaedDate = CretaedDate;
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

    public String getZemullaTransactinoID() {
        return ZemullaTransactinoID;
    }

    public void setZemullaTransactinoID(String ZemullaTransactinoID) {
        this.ZemullaTransactinoID = ZemullaTransactinoID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }

    public String getTransaction_reference() {
        return transaction_reference;
    }

    public void setTransaction_reference(String transaction_reference) {
        this.transaction_reference = transaction_reference;
    }
}
