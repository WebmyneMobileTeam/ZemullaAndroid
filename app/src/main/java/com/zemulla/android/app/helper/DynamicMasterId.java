package com.zemulla.android.app.helper;

/**
 * Created by sagartahelyani on 09-08-2016.
 */
public enum DynamicMasterId {

    Kazang(1),
    Airtel(2),
    MTN(3),
    Zoona(4);

    int id;

    DynamicMasterId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
