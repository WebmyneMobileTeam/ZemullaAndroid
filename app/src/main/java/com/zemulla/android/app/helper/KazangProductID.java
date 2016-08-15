package com.zemulla.android.app.helper;

/**
 * Created by sagartahelyani on 09-08-2016.
 */
public enum KazangProductID {

    BuyElectricity(20004),
    TestElectricity(20010),
    ReprintElectricity(20007),
    ReissueElectricity(20005),
    DSTVPayment(20009);

    int id;

    KazangProductID(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
