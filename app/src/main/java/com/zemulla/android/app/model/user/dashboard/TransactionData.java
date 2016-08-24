package com.zemulla.android.app.model.user.dashboard;

/**
 * Created by raghavthakkar on 23-08-2016.
 */
public class TransactionData {

    private int Amount;
    private String TransactionType;

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int Amount) {
        this.Amount = Amount;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String TransactionType) {
        this.TransactionType = TransactionType;
    }
}
