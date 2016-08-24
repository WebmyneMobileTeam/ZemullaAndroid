package com.zemulla.android.app.model.user.notification;

/**
 * Created by raghavthakkar on 24-08-2016.
 */
public class Notification {



    private String Message;
    private long PKID;
    private String ProfilePicWithURL;
    private long ServiceDetailID;
    private long UserID;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public long getPKID() {
        return PKID;
    }

    public void setPKID(long PKID) {
        this.PKID = PKID;
    }

    public String getProfilePicWithURL() {
        return ProfilePicWithURL;
    }

    public void setProfilePicWithURL(String ProfilePicWithURL) {
        this.ProfilePicWithURL = ProfilePicWithURL;
    }

    public long getServiceDetailID() {
        return ServiceDetailID;
    }

    public void setServiceDetailID(long ServiceDetailID) {
        this.ServiceDetailID = ServiceDetailID;
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long UserID) {
        this.UserID = UserID;
    }
}
