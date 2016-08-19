package com.zemulla.android.app.model.reports.gettopupapireportdetails;

/**
 * Created by raghavthakkar on 19-08-2016.
 */
public class ReportRequest {

    private String From;
    private boolean IsPageLoad;
    private long ServiceDetailID;
    private String To;
    private long UserID;

    public String getFrom() {
        return From;
    }

    public void setFrom(String From) {
        this.From = From;
    }

    public boolean isIsPageLoad() {
        return IsPageLoad;
    }

    public void setIsPageLoad(boolean IsPageLoad) {
        this.IsPageLoad = IsPageLoad;
    }

    public long getServiceDetailID() {
        return ServiceDetailID;
    }

    public void setServiceDetailID(long ServiceDetailID) {
        this.ServiceDetailID = ServiceDetailID;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String To) {
        this.To = To;
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long UserID) {
        this.UserID = UserID;
    }
}
