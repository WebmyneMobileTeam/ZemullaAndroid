package com.zemulla.android.app.model.base;

import java.io.Serializable;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public class Response implements Serializable {

    private int ResponseCode;
    private String ResponseMsg;

    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int ResponseCode) {
        this.ResponseCode = ResponseCode;
    }

    public String getResponseMsg() {
        return ResponseMsg;
    }

    public void setResponseMsg(String ResponseMsg) {
        this.ResponseMsg = ResponseMsg;
    }
}
