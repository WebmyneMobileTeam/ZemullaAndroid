package com.zemulla.android.app.model.kazang.dstvpayment3;

/**
 * Created by raghavthakkar on 17-08-2016.
 */
public class DSTVPayment3Request {

    private long UserID;
    private String ZemullaTransactinoID;
    private String confirmation_number;
    private String product_id;

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long UserID) {
        this.UserID = UserID;
    }

    public String getZemullaTransactinoID() {
        return ZemullaTransactinoID;
    }

    public void setZemullaTransactinoID(String ZemullaTransactinoID) {
        this.ZemullaTransactinoID = ZemullaTransactinoID;
    }

    public String getConfirmation_number() {
        return confirmation_number;
    }

    public void setConfirmation_number(String confirmation_number) {
        this.confirmation_number = confirmation_number;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
