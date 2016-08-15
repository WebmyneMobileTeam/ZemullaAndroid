package com.zemulla.android.app.model.kazang.kazangtestelectricity;

import com.zemulla.android.app.model.base.BaseResponse;

/**
 * Created by raghavthakkar on 15-08-2016.
 */
public class KazangTestElectricityResponse extends BaseResponse {

    private String response_code;
    private String response_message;

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }
}
