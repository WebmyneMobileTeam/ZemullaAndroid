package com.zemulla.android.app.model.base;

import java.io.Serializable;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public class BaseResponse implements Serializable{

    Response Response;


    public com.zemulla.android.app.model.base.Response getResponse() {
        return Response;
    }

    public void setResponse(com.zemulla.android.app.model.base.Response response) {
        this.Response = response;
    }

}
