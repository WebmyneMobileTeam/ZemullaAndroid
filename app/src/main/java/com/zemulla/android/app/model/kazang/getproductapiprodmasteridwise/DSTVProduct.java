package com.zemulla.android.app.model.kazang.getproductapiprodmasteridwise;

/**
 * Created by raghavthakkar on 17-08-2016.
 */
public class DSTVProduct {

    private String product_id;
    private String product_provider;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_provider() {
        return product_provider;
    }

    public void setProduct_provider(String product_provider) {
        this.product_provider = product_provider;
    }

    public DSTVProduct(String product_id, String product_provider) {
        this.product_id = product_id;
        this.product_provider = product_provider;
    }
}
