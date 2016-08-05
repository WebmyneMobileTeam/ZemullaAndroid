package com.zemulla.android.app.home.model;

import com.zemulla.android.app.model.base.BaseResponse;

/**
 * Created by sagartahelyani on 05-08-2016.
 */
public class ProfileResponse extends BaseResponse {

    private FullProfile Data;

    public FullProfile getData() {
        return Data;
    }

    public void setData(FullProfile data) {
        Data = data;
    }

}
