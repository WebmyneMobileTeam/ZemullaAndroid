package com.zemulla.android.app.model.kazang.dstvpayment1;

import com.zemulla.android.app.model.base.BaseResponse;

/**
 * Created by raghavthakkar on 17-08-2016.
 */
public class DSTVPayment1Response extends BaseResponse {

    private double Amount;
    private String ZemullaTransactinoID;
    private String balance;
    private String confirmation_message;
    private String confirmation_number;
    private String response_code;
    private String response_message;



    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
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

    public String getConfirmation_message() {
        return confirmation_message;
    }

    public void setConfirmation_message(String confirmation_message) {
        this.confirmation_message = confirmation_message;
    }

    public String getConfirmation_number() {
        return confirmation_number;
    }

    public void setConfirmation_number(String confirmation_number) {
        this.confirmation_number = confirmation_number;
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
}
