package com.zemulla.android.app.model.login;

import com.zemulla.android.app.model.base.BaseResponse;

/**
 * Created by raghavthakkar on 02-08-2016.
 */
public class LoginResponse extends BaseResponse{


    private String CallingCode;
    private String Email;
    private String FirstName;
    private boolean IsEmailVerified;
    private boolean IsMobileVerified;
    private String LastName;
    private String Mobile;
    private String ProfilePic;
    private int RoleID;
    private int UserID;

    public String getCallingCode() {
        return CallingCode;
    }

    public void setCallingCode(String CallingCode) {
        this.CallingCode = CallingCode;
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

    public boolean isIsEmailVerified() {
        return IsEmailVerified;
    }

    public void setIsEmailVerified(boolean IsEmailVerified) {
        this.IsEmailVerified = IsEmailVerified;
    }

    public boolean isIsMobileVerified() {
        return IsMobileVerified;
    }

    public void setIsMobileVerified(boolean IsMobileVerified) {
        this.IsMobileVerified = IsMobileVerified;
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

    public String getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(String ProfilePic) {
        this.ProfilePic = ProfilePic;
    }


    public int getRoleID() {
        return RoleID;
    }

    public void setRoleID(int RoleID) {
        this.RoleID = RoleID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }


}
