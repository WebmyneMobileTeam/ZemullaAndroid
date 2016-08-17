package com.zemulla.android.app.model.kazang.dstvpayment2;

import com.zemulla.android.app.model.base.BaseResponse;

/**
 * Created by raghavthakkar on 17-08-2016.
 */
public class DSTVPayment2Response extends BaseResponse{

    private double Amount;
    private double TotalCharge;
    private double TotalPayableAmount;
    private String TransationDate;
    private String ZemullaTransactinoID;
    private String balance;
    private String confirmation_number;
    private String cost;
    private String receipt_confirmation_required;
    private String response_code;
    private String response_message;
    private String transaction_reference;

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

    public String getTransationDate() {
        return TransationDate;
    }

    public void setTransationDate(String TransationDate) {
        this.TransationDate = TransationDate;
    }

    public String getZemullaTransactinoID() {
        return ZemullaTransactinoID;
    }

    public void setZemullaTransactinoID(String ZemullaTransactinoID) {
        this.ZemullaTransactinoID = ZemullaTransactinoID;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getConfirmation_number() {
        return confirmation_number;
    }

    public void setConfirmation_number(String confirmation_number) {
        this.confirmation_number = confirmation_number;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getReceipt_confirmation_required() {
        return receipt_confirmation_required;
    }

    public void setReceipt_confirmation_required(String receipt_confirmation_required) {
        this.receipt_confirmation_required = receipt_confirmation_required;
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
