package com.zemulla.android.app.model.account.login;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public class LoginRequest {

    private String CallingCode;
    private String password;
    private int roleID;
    private String username;

    public String getCallingCode() {
        return CallingCode;
    }

    public void setCallingCode(String CallingCode) {
        this.CallingCode = CallingCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
