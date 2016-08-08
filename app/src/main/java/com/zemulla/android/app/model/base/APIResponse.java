package com.zemulla.android.app.model.base;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public class APIResponse<T> extends Response {

    ResponseData<T> ResponseData;

    public com.zemulla.android.app.model.base.ResponseData<T> getResponseData() {
        return ResponseData;
    }

    public void setResponseData(com.zemulla.android.app.model.base.ResponseData<T> responseData) {
        ResponseData = responseData;
    }
}
