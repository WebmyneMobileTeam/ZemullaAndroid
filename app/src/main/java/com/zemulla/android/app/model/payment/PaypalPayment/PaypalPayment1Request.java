package com.zemulla.android.app.model.payment.PaypalPayment;

/**
 * Created by sagartahelyani on 10-08-2016.
 */
public class PaypalPayment1Request {


    /**
     * Amount : 1.2678967543233E7
     * UserID : 9223372036854775807
     */

    private double Amount;
    private long UserID;

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long UserID) {
        this.UserID = UserID;
    }
}
