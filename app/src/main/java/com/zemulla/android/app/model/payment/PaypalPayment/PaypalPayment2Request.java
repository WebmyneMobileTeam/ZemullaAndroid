package com.zemulla.android.app.model.payment.PaypalPayment;

/**
 * Created by sagartahelyani on 10-08-2016.
 */
public class PaypalPayment2Request {

    /**
     * JSONData : String content
     * UserID : 9223372036854775807
     * ZemullaTransID : String content
     * cart : String content
     * experience_profile_id : String content
     * id : String content
     * intent : String content
     * invoice_number : String content
     * merchant_id : String content
     * note_to_payer : String content
     * payer_account_number : String content
     * payer_account_type : String content
     * payer_email : String content
     * payer_first_name : String content
     * payer_id : String content
     * payer_last_name : String content
     * payer_payment_method : String content
     * payer_status : String content
     * purchase_unit_reference_id : String content
     * state : String content
     * token : String content
     */

    private String JSONData;
    private long UserID;
    private String ZemullaTransID;
    private String cart;
    private String experience_profile_id;
    private String id;
    private String intent;
    private String invoice_number;
    private String merchant_id;
    private String note_to_payer;
    private String payer_account_number;
    private String payer_account_type;
    private String payer_email;
    private String payer_first_name;
    private String payer_id;
    private String payer_last_name;
    private String payer_payment_method;
    private String payer_status;
    private String purchase_unit_reference_id;
    private String state;
    private String token;

    public String getJSONData() {
        return JSONData;
    }

    public void setJSONData(String JSONData) {
        this.JSONData = JSONData;
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long UserID) {
        this.UserID = UserID;
    }

    public String getZemullaTransID() {
        return ZemullaTransID;
    }

    public void setZemullaTransID(String ZemullaTransID) {
        this.ZemullaTransID = ZemullaTransID;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    public String getExperience_profile_id() {
        return experience_profile_id;
    }

    public void setExperience_profile_id(String experience_profile_id) {
        this.experience_profile_id = experience_profile_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getNote_to_payer() {
        return note_to_payer;
    }

    public void setNote_to_payer(String note_to_payer) {
        this.note_to_payer = note_to_payer;
    }

    public String getPayer_account_number() {
        return payer_account_number;
    }

    public void setPayer_account_number(String payer_account_number) {
        this.payer_account_number = payer_account_number;
    }

    public String getPayer_account_type() {
        return payer_account_type;
    }

    public void setPayer_account_type(String payer_account_type) {
        this.payer_account_type = payer_account_type;
    }

    public String getPayer_email() {
        return payer_email;
    }

    public void setPayer_email(String payer_email) {
        this.payer_email = payer_email;
    }

    public String getPayer_first_name() {
        return payer_first_name;
    }

    public void setPayer_first_name(String payer_first_name) {
        this.payer_first_name = payer_first_name;
    }

    public String getPayer_id() {
        return payer_id;
    }

    public void setPayer_id(String payer_id) {
        this.payer_id = payer_id;
    }

    public String getPayer_last_name() {
        return payer_last_name;
    }

    public void setPayer_last_name(String payer_last_name) {
        this.payer_last_name = payer_last_name;
    }

    public String getPayer_payment_method() {
        return payer_payment_method;
    }

    public void setPayer_payment_method(String payer_payment_method) {
        this.payer_payment_method = payer_payment_method;
    }

    public String getPayer_status() {
        return payer_status;
    }

    public void setPayer_status(String payer_status) {
        this.payer_status = payer_status;
    }

    public String getPurchase_unit_reference_id() {
        return purchase_unit_reference_id;
    }

    public void setPurchase_unit_reference_id(String purchase_unit_reference_id) {
        this.purchase_unit_reference_id = purchase_unit_reference_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "PaypalPayment2Request{" +
                "JSONData='" + JSONData + '\'' +
                ", UserID=" + UserID +
                ", ZemullaTransID='" + ZemullaTransID + '\'' +
                ", cart='" + cart + '\'' +
                ", experience_profile_id='" + experience_profile_id + '\'' +
                ", id='" + id + '\'' +
                ", intent='" + intent + '\'' +
                ", invoice_number='" + invoice_number + '\'' +
                ", merchant_id='" + merchant_id + '\'' +
                ", note_to_payer='" + note_to_payer + '\'' +
                ", payer_account_number='" + payer_account_number + '\'' +
                ", payer_account_type='" + payer_account_type + '\'' +
                ", payer_email='" + payer_email + '\'' +
                ", payer_first_name='" + payer_first_name + '\'' +
                ", payer_id='" + payer_id + '\'' +
                ", payer_last_name='" + payer_last_name + '\'' +
                ", payer_payment_method='" + payer_payment_method + '\'' +
                ", payer_status='" + payer_status + '\'' +
                ", purchase_unit_reference_id='" + purchase_unit_reference_id + '\'' +
                ", state='" + state + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public PaypalPayment2Request() {
        this.JSONData = "";
        UserID = 0;
        ZemullaTransID = "";
        this.cart = "";
        this.experience_profile_id = "";
        this.id = "";
        this.intent = "";
        this.invoice_number = "";
        this.merchant_id = "";
        this.note_to_payer = "";
        this.payer_account_number = "";
        this.payer_account_type = "";
        this.payer_email = "";
        this.payer_first_name = "";
        this.payer_id = "";
        this.payer_last_name = "";
        this.payer_payment_method = "";
        this.payer_status = "";
        this.purchase_unit_reference_id = "";
        this.state = "";
        this.token = "";
    }
}
