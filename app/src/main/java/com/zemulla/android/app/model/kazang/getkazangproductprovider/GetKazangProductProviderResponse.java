package com.zemulla.android.app.model.kazang.getkazangproductprovider;

import com.zemulla.android.app.model.base.BaseResponse;

import java.util.List;

/**
 * Created by raghavthakkar on 15-08-2016.
 */
public class GetKazangProductProviderResponse extends BaseResponse {


    private List<String> ProductProvider;

    public List<String> getProductProvider() {
        return ProductProvider;
    }

    public void setProductProvider(List<String> ProductProvider) {
        this.ProductProvider = ProductProvider;
    }
}
