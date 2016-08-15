package com.zemulla.android.app.helper;

/**
 * Created by raghavthakkar on 15-08-2016.
 */
public enum KazangProductProvider {

    AirTime("Airtime"),
    DirectRecharge("PinlessAirtime"),
    ElectricityBill("Electricity");

    private String name;

    KazangProductProvider(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
