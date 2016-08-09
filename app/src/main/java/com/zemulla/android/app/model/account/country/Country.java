package com.zemulla.android.app.model.account.country;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public class Country {

    private String CallingCode;
    private int CountryID;
    private String CountryName;
    private String CurrencyCode;
    private String ISO2Name;
    private Object ISO3Name;

    public String getCallingCode() {
        return CallingCode;
    }

    public void setCallingCode(String CallingCode) {
        this.CallingCode = CallingCode;
    }

    public int getCountryID() {
        return CountryID;
    }

    public void setCountryID(int CountryID) {
        this.CountryID = CountryID;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String CountryName) {
        this.CountryName = CountryName;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String CurrencyCode) {
        this.CurrencyCode = CurrencyCode;
    }

    public String getISO2Name() {
        return ISO2Name;
    }

    public void setISO2Name(String ISO2Name) {
        this.ISO2Name = ISO2Name;
    }

    public Object getISO3Name() {
        return ISO3Name;
    }

    public void setISO3Name(Object ISO3Name) {
        this.ISO3Name = ISO3Name;
    }
}
