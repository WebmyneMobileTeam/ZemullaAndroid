package com.zemulla.android.app.model.user.notification;

/**
 * Created by raghavthakkar on 24-08-2016.
 */
public class ResponseData {
    private long PKID;
    private long ServiceDetailID;
    private long UserID;

    public long getPKID() {
        return PKID;
    }

    public void setPKID(long PKID) {
        this.PKID = PKID;
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
