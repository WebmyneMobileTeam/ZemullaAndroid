package com.zemulla.android.app.model.base;

import java.io.Serializable;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public class BaseSmallCaseResponse implements Serializable {

    Response response;


    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
