package com.zemulla.android.app.model.base;

import java.util.List;

/**
 * Created by raghavthakkar on 03-08-2016.
 */
public class ResponseData<T> {

    List<T> Data;

    public List<T> getData() {
        return Data;
    }

    public void setData(List<T> data) {
        Data = data;
    }
}
