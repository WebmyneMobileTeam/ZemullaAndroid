package com.zemulla.android.app.model.kazang.kazangtestelectricity;

import java.io.Serializable;

/**
 * Created by raghavthakkar on 15-08-2016.
 */
public class KazangElectricityRequest implements Serializable{

    private double Amount;
    private String MeterNumber;
    private long ServiceDetailID;
    private double TotalCharge;
    private double TotalPayableAmount;
    private long UserID;
    private String VerificationCode;
    private String ZemullaTransionID;
    private String confirmation_number;
    private String product_id;

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public String getMeterNumber() {
        return MeterNumber;
    }

    public void setMeterNumber(String MeterNumber) {
        this.MeterNumber = MeterNumber;
    }

    public long getServiceDetailID() {
        return ServiceDetailID;
    }

    public void setServiceDetailID(long ServiceDetailID) {
        this.ServiceDetailID = ServiceDetailID;
    }

    public double getTotalCharge() {
        return TotalCharge;
    }

    public void setTotalCharge(double TotalCharge) {
        this.TotalCharge = TotalCharge;
    }

    public double getTotalPayableAmount() {
        return TotalPayableAmount;
    }

    public void setTotalPayableAmount(double TotalPayableAmount) {
        this.TotalPayableAmount = TotalPayableAmount;
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long UserID) {
        this.UserID = UserID;
    }

    public String getVerificationCode() {
        return VerificationCode;
    }

    public void setVerificationCode(String VerificationCode) {
        this.VerificationCode = VerificationCode;
    }

    public String getZemullaTransionID() {
        return ZemullaTransionID;
    }

    public void setZemullaTransionID(String ZemullaTransionID) {
        this.ZemullaTransionID = ZemullaTransionID;
    }

    public String getConfirmation_number() {
        return confirmation_number;
    }

    public void setConfirmation_number(String confirmation_number) {
        this.confirmation_number = confirmation_number;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public KazangElectricityRequest() {
        Amount = 0.0;
        MeterNumber = "";
        ServiceDetailID = 0;
        TotalCharge = 0;
        TotalPayableAmount = 0;
        UserID = 0;
        VerificationCode = "";
        ZemullaTransionID = "0";
        this.confirmation_number = "";
        this.product_id = "";
    }
}
