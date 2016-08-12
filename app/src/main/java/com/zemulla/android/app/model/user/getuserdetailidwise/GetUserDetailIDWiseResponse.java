package com.zemulla.android.app.model.user.getuserdetailidwise;

import com.zemulla.android.app.model.base.BaseResponse;

/**
 * Created by raghavthakkar on 11-08-2016.
 */
public class GetUserDetailIDWiseResponse extends BaseResponse {

    GetUserDetailIDWise Data;

    public GetUserDetailIDWise getData() {
        return Data;
    }

    public void setData(GetUserDetailIDWise data) {
        Data = data;
    }
}
