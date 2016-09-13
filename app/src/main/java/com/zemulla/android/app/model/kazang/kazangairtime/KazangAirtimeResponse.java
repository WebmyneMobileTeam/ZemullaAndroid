package com.zemulla.android.app.model.kazang.kazangairtime;

import com.zemulla.android.app.model.base.BaseResponse;

import java.io.Serializable;

/**
 * Created by raghavthakkar on 15-08-2016.
 */
public class KazangAirtimeResponse extends BaseResponse implements Serializable{

    private String ZemullaTransactionID;
    private String amount;
    private String pin;
    private String product;
    private String response_code;
    private String response_message;
    private String serial_number;
    private String transaction_reference;

    public String getZemullaTransactionID() {
        return ZemullaTransactionID;
    }

    public void setZemullaTransactionID(String ZemullaTransactionID) {
        this.ZemullaTransactionID = ZemullaTransactionID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
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

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getTransaction_reference() {
        return transaction_reference;
    }

    public void setTransaction_reference(String transaction_reference) {
        this.transaction_reference = transaction_reference;
    }
}
