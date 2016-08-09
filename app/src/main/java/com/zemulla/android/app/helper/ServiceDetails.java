package com.zemulla.android.app.helper;

/**
 * Created by sagartahelyani on 09-08-2016.
 */
public enum ServiceDetails {

    TopUpByAdmin(1),

    WithdrawalByAdmin(2),

    TopUpByGetway(3),

    WalletToWallet(4),

    AirtimeByVoucher(5),

    KazangElectricity(6),

    KazangDirectRecharge(7),

    DSTVPayment(8),

    PaypalPayment(9),

    CyberSource(10),

    MTNCredit(11),

    AirtelCredit(12),

    ZoonaCredit(13),

    MTNDebit(14),

    AirtelDebit(15),

    ZoonaDebit(16);


    ServiceDetails(int id) {
        this.id = id;
    }

    int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
