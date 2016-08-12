package com.zemulla.android.app.model.base;

/**
 * Created by raghavthakkar on 03-08-2016.
 */
public class Data<T> {

    T Data;

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
