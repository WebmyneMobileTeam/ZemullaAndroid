package com.zemulla.android.app.model.base;

/**
 * Created by sagartahelyani on 09-08-2016.
 */
public class TransactionCharge {


    /**
     * Amount : 1.2678967543233E7
     * ServiceDetailsID : 9223372036854775807
     */

    private double Amount;
    private long ServiceDetailsID;

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public long getServiceDetailsID() {
        return ServiceDetailsID;
    }

    public void setServiceDetailsID(long ServiceDetailsID) {
        this.ServiceDetailsID = ServiceDetailsID;
    }
}
