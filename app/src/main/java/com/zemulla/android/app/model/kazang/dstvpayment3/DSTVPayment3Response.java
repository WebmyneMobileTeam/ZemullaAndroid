package com.zemulla.android.app.model.kazang.dstvpayment3;

import com.zemulla.android.app.model.base.BaseResponse;

/**
 * Created by raghavthakkar on 17-08-2016.
 */
public class DSTVPayment3Response extends BaseResponse {


    private String balance;
    private String cost;
    private String response_code;
    private String response_message;
    private String transaction_reference;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
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
