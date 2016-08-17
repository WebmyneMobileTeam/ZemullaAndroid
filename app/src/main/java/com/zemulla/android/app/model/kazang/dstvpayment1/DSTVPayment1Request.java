package com.zemulla.android.app.model.kazang.dstvpayment1;

/**
 * Created by raghavthakkar on 17-08-2016.
 */
public class DSTVPayment1Request {


    private long UserID;
    private String months;
    private String number;
    private String number_type;
    private String product_id;
    private String products;

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long UserID) {
        this.UserID = UserID;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber_type() {
        return number_type;
    }

    public void setNumber_type(String number_type) {
        this.number_type = number_type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }
}
