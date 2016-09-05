package com.zemulla.android.app.model.payment.generatesignaturecs;

/**
 * Created by raghavthakkar on 02-09-2016.
 */
public class GenerateSignatureCSRequest {


    private long UserID;
    private String amount;

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long UserID) {
        this.UserID = UserID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
