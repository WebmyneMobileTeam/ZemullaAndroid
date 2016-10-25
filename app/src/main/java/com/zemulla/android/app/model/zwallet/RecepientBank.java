package com.zemulla.android.app.model.zwallet;

/**
 * Created by raghavthakkar on 12-10-2016.
 */
public class RecepientBank {


    private int BankID;
    private String BankName;

    public int getBankID() {
        return BankID;
    }

    public void setBankID(int BankID) {
        this.BankID = BankID;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
    }

    public RecepientBank() {
        this.BankID = -1;
        this.BankName = "Please Select Recipient Bank";
    }
}
