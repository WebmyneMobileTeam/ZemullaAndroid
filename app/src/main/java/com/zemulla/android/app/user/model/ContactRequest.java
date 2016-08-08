package com.zemulla.android.app.user.model;

/**
 * Created by sagartahelyani on 08-08-2016.
 */
public class ContactRequest {

    /**
     * Description : String content
     * Subject : String content
     * UserID : 9223372036854775807
     * ZemullaServicesID : 9223372036854775807
     * ZemullaTransactionID : String content
     */

    private String Description;
    private String Subject;
    private long UserID;
    private long ZemullaServicesID;
    private String ZemullaTransactionID;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long UserID) {
        this.UserID = UserID;
    }

    public long getZemullaServicesID() {
        return ZemullaServicesID;
    }

    public void setZemullaServicesID(long ZemullaServicesID) {
        this.ZemullaServicesID = ZemullaServicesID;
    }

    public String getZemullaTransactionID() {
        return ZemullaTransactionID;
    }

    public void setZemullaTransactionID(String ZemullaTransactionID) {
        this.ZemullaTransactionID = ZemullaTransactionID;
    }
}

