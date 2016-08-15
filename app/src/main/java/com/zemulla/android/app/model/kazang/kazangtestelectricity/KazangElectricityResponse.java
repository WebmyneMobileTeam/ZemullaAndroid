package com.zemulla.android.app.model.kazang.kazangtestelectricity;

import com.zemulla.android.app.model.base.BaseResponse;

import java.io.Serializable;

/**
 * Created by raghavthakkar on 15-08-2016.
 */
public class KazangElectricityResponse extends BaseResponse implements Serializable{

    private double Amount;
    private String MeterNumber;
    private double TotalCharge;
    private double TotalPayableAmount;
    private String TransactionDate;
    private String ZemullaTransactionID;
    private String code;
    private String confirmation_message;
    private String confirmation_number;
    private String customer_account_no;
    private String customer_address;
    private String customer_messages;
    private String customer_name;
    private String desc;
    private String external_reference_number;
    private String maximum_vend_amount;
    private String outstanding_charges;
    private String processor_description;
    private String receipt;
    private String receipt_no;
    private String remaining_qouta;
    private String response_code;
    private String response_message;
    private String std_tokens;
    private String tariff;
    private String tariff_blocks;
    private String tariff_name;
    private String tax;
    private String transaction_reference;
    private String units;
    private String utility_name;

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public String getMeterNumber() {
        return MeterNumber;
    }

    public void setMeterNumber(String MeterNumber) {
        this.MeterNumber = MeterNumber;
    }

    public double getTotalCharge() {
        return TotalCharge;
    }

    public void setTotalCharge(double TotalCharge) {
        this.TotalCharge = TotalCharge;
    }

    public double getTotalPayableAmount() {
        return TotalPayableAmount;
    }

    public void setTotalPayableAmount(double TotalPayableAmount) {
        this.TotalPayableAmount = TotalPayableAmount;
    }

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(String TransactionDate) {
        this.TransactionDate = TransactionDate;
    }

    public String getZemullaTransactionID() {
        return ZemullaTransactionID;
    }

    public void setZemullaTransactionID(String ZemullaTransactionID) {
        this.ZemullaTransactionID = ZemullaTransactionID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getConfirmation_message() {
        return confirmation_message;
    }

    public void setConfirmation_message(String confirmation_message) {
        this.confirmation_message = confirmation_message;
    }

    public String getConfirmation_number() {
        return confirmation_number;
    }

    public void setConfirmation_number(String confirmation_number) {
        this.confirmation_number = confirmation_number;
    }

    public String getCustomer_account_no() {
        return customer_account_no;
    }

    public void setCustomer_account_no(String customer_account_no) {
        this.customer_account_no = customer_account_no;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_messages() {
        return customer_messages;
    }

    public void setCustomer_messages(String customer_messages) {
        this.customer_messages = customer_messages;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExternal_reference_number() {
        return external_reference_number;
    }

    public void setExternal_reference_number(String external_reference_number) {
        this.external_reference_number = external_reference_number;
    }

    public String getMaximum_vend_amount() {
        return maximum_vend_amount;
    }

    public void setMaximum_vend_amount(String maximum_vend_amount) {
        this.maximum_vend_amount = maximum_vend_amount;
    }

    public String getOutstanding_charges() {
        return outstanding_charges;
    }

    public void setOutstanding_charges(String outstanding_charges) {
        this.outstanding_charges = outstanding_charges;
    }

    public String getProcessor_description() {
        return processor_description;
    }

    public void setProcessor_description(String processor_description) {
        this.processor_description = processor_description;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }

    public String getRemaining_qouta() {
        return remaining_qouta;
    }

    public void setRemaining_qouta(String remaining_qouta) {
        this.remaining_qouta = remaining_qouta;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }

    public String getStd_tokens() {
        return std_tokens;
    }

    public void setStd_tokens(String std_tokens) {
        this.std_tokens = std_tokens;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public String getTariff_blocks() {
        return tariff_blocks;
    }

    public void setTariff_blocks(String tariff_blocks) {
        this.tariff_blocks = tariff_blocks;
    }

    public String getTariff_name() {
        return tariff_name;
    }

    public void setTariff_name(String tariff_name) {
        this.tariff_name = tariff_name;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTransaction_reference() {
        return transaction_reference;
    }

    public void setTransaction_reference(String transaction_reference) {
        this.transaction_reference = transaction_reference;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getUtility_name() {
        return utility_name;
    }

    public void setUtility_name(String utility_name) {
        this.utility_name = utility_name;
    }
}
