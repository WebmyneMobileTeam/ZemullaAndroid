package com.zemulla.android.app.model.kazang.getkazangproductplan;

/**
 * Created by raghavthakkar on 15-08-2016.
 */
public class GetKazangProductPlanRequest {


    private String product_category;
    private String product_provider;

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_provider() {
        return product_provider;
    }

    public void setProduct_provider(String product_provider) {
        this.product_provider = product_provider;
    }
}
