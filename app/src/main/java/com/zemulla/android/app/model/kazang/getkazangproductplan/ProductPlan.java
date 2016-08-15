package com.zemulla.android.app.model.kazang.getkazangproductplan;

/**
 * Created by raghavthakkar on 15-08-2016.
 */
public class ProductPlan {


    private String description;
    private String product_id;
    private String product_value;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_value() {
        return product_value;
    }

    public void setProduct_value(String product_value) {
        this.product_value = product_value;
    }

    public ProductPlan() {
        this.description = "Select Plan";
        this.product_id = "0";
        this.product_value = "0";
    }
}
