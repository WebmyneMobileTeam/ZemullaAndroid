package com.zemulla.android.app.model.account.changeemail;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class ChangeEmailRequest {


    private String NewEmail;
    private String OldEmail;
    private String Password;

    public String getNewEmail() {
        return NewEmail;
    }

    public void setNewEmail(String NewEmail) {
        this.NewEmail = NewEmail;
    }

    public String getOldEmail() {
        return OldEmail;
    }

    public void setOldEmail(String OldEmail) {
        this.OldEmail = OldEmail;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
}
