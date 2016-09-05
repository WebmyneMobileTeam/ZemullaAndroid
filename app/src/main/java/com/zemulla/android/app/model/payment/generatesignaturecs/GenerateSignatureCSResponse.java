package com.zemulla.android.app.model.payment.generatesignaturecs;

import com.zemulla.android.app.model.base.BaseResponse;

import java.io.Serializable;

/**
 * Created by raghavthakkar on 02-09-2016.
 */
public class GenerateSignatureCSResponse extends BaseResponse implements Serializable{




    private String access_key;
    private String bill_to_address_city;
    private String bill_to_address_country;
    private String bill_to_address_line1;
    private String bill_to_address_postal_code;
    private String bill_to_address_state;
    private String bill_to_email;
    private String bill_to_forename;
    private String bill_to_phone;
    private String bill_to_surname;
    private String currency;
    private String locale;
    private String payment_method;
    private String profile_id;
    private String reference_number;
    private String signature;
    private String signed_date_time;
    private String signed_field_names;
    private String transaction_type;
    private String transaction_uuid;
    private String unsigned_field_names;

    public String getAccess_key() {
        return access_key;
    }

    public void setAccess_key(String access_key) {
        this.access_key = access_key;
    }

    public String getBill_to_address_city() {
        return bill_to_address_city;
    }

    public void setBill_to_address_city(String bill_to_address_city) {
        this.bill_to_address_city = bill_to_address_city;
    }

    public String getBill_to_address_country() {
        return bill_to_address_country;
    }

    public void setBill_to_address_country(String bill_to_address_country) {
        this.bill_to_address_country = bill_to_address_country;
    }

    public String getBill_to_address_line1() {
        return bill_to_address_line1;
    }

    public void setBill_to_address_line1(String bill_to_address_line1) {
        this.bill_to_address_line1 = bill_to_address_line1;
    }

    public String getBill_to_address_postal_code() {
        return bill_to_address_postal_code;
    }

    public void setBill_to_address_postal_code(String bill_to_address_postal_code) {
        this.bill_to_address_postal_code = bill_to_address_postal_code;
    }

    public String getBill_to_address_state() {
        return bill_to_address_state;
    }

    public void setBill_to_address_state(String bill_to_address_state) {
        this.bill_to_address_state = bill_to_address_state;
    }

    public String getBill_to_email() {
        return bill_to_email;
    }

    public void setBill_to_email(String bill_to_email) {
        this.bill_to_email = bill_to_email;
    }

    public String getBill_to_forename() {
        return bill_to_forename;
    }

    public void setBill_to_forename(String bill_to_forename) {
        this.bill_to_forename = bill_to_forename;
    }

    public String getBill_to_phone() {
        return bill_to_phone;
    }

    public void setBill_to_phone(String bill_to_phone) {
        this.bill_to_phone = bill_to_phone;
    }

    public String getBill_to_surname() {
        return bill_to_surname;
    }

    public void setBill_to_surname(String bill_to_surname) {
        this.bill_to_surname = bill_to_surname;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getReference_number() {
        return reference_number;
    }

    public void setReference_number(String reference_number) {
        this.reference_number = reference_number;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSigned_date_time() {
        return signed_date_time;
    }

    public void setSigned_date_time(String signed_date_time) {
        this.signed_date_time = signed_date_time;
    }

    public String getSigned_field_names() {
        return signed_field_names;
    }

    public void setSigned_field_names(String signed_field_names) {
        this.signed_field_names = signed_field_names;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getTransaction_uuid() {
        return transaction_uuid;
    }

    public void setTransaction_uuid(String transaction_uuid) {
        this.transaction_uuid = transaction_uuid;
    }

    public String getUnsigned_field_names() {
        return unsigned_field_names;
    }

    public void setUnsigned_field_names(String unsigned_field_names) {
        this.unsigned_field_names = unsigned_field_names;
    }
}
