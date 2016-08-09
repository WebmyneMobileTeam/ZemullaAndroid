package com.zemulla.android.app.model.account.resetpassword;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class ResetPasswordRequest {


    private String Password;
    private String Token;

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public ResetPasswordRequest() {
        Password = "";
        Token = "";
    }
}
