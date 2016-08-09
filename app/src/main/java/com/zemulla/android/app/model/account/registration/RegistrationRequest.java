package com.zemulla.android.app.model.account.registration;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public class RegistrationRequest {


    private String Address;
    private String CallingCode;
    private String City;
    private long CountryID;
    private String Email;
    private String FirstName;
    private String LastName;
    private String Mobile;
    private String Password;
    private long RoleID;
    private String State;
    private String ZipCode;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getCallingCode() {
        return CallingCode;
    }

    public void setCallingCode(String CallingCode) {
        this.CallingCode = CallingCode;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public long getCountryID() {
        return CountryID;
    }

    public void setCountryID(long CountryID) {
        this.CountryID = CountryID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public long getRoleID() {
        return RoleID;
    }

    public void setRoleID(long RoleID) {
        this.RoleID = RoleID;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String ZipCode) {
        this.ZipCode = ZipCode;
    }
}
