package com.zemulla.android.app.user.model;

/**
 * Created by sagartahelyani on 08-08-2016.
 */
public class ChangePasswordRequest {

    /**
     * NewPassword : String content
     * OldPassword : String content
     * UserID : 9223372036854775807
     */

    private String NewPassword;
    private String OldPassword;
    private long UserID;

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String NewPassword) {
        this.NewPassword = NewPassword;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String OldPassword) {
        this.OldPassword = OldPassword;
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long UserID) {
        this.UserID = UserID;
    }
}
