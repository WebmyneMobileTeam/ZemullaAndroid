package com.zemulla.android.app.transaction;

/**
 * Created by krishnakumar on 29-07-2016.
 */

public class CyberSourceBean {
    public String zemullaTransId;
    public String zemullaTransDate;
    public String firstName;
    public String lastName;
    public String amount;
    public String charge;
    public String totalAmount;
    public String paymentStatus;

    public CyberSourceBean(String zemullaTransId, String zemullaTransDate, String firstName, String lastName, String amount, String charge, String totalAmount, String paymentStatus) {
        this.zemullaTransId = zemullaTransId;
        this.zemullaTransDate = zemullaTransDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.amount = amount;
        this.charge = charge;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
    }

    public String getZemullaTransId() {
        return zemullaTransId;
    }

    public void setZemullaTransId(String zemullaTransId) {
        this.zemullaTransId = zemullaTransId;
    }

    public String getZemullaTransDate() {
        return zemullaTransDate;
    }

    public void setZemullaTransDate(String zemullaTransDate) {
        this.zemullaTransDate = zemullaTransDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
