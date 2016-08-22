package com.zemulla.android.app.model.reports.getkazangairtimedetails;

/**
 * Created by raghavthakkar on 22-08-2016.
 */
public class AirtimeDetails {


    private String Amount;
    private String CreatedDate;
    private String FirstName;
    private String LastName;
    private String Pin;
    private String Product;
    private String Response_Code;
    private String TotalCharge;
    private String TotalPayableAmount;
    private String Transaction_reference;
    private String ZemullaTransactionID;

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String CreatedDate) {
        this.CreatedDate = CreatedDate;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getPin() {
        return Pin;
    }

    public void setPin(String Pin) {
        this.Pin = Pin;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String Product) {
        this.Product = Product;
    }

    public String getResponse_Code() {
        return Response_Code;
    }

    public void setResponse_Code(String Response_Code) {
        this.Response_Code = Response_Code;
    }

    public String getTotalCharge() {
        return TotalCharge;
    }

    public void setTotalCharge(String TotalCharge) {
        this.TotalCharge = TotalCharge;
    }

    public String getTotalPayableAmount() {
        return TotalPayableAmount;
    }

    public void setTotalPayableAmount(String TotalPayableAmount) {
        this.TotalPayableAmount = TotalPayableAmount;
    }

    public String getTransaction_reference() {
        return Transaction_reference;
    }

    public void setTransaction_reference(String Transaction_reference) {
        this.Transaction_reference = Transaction_reference;
    }

    public String getZemullaTransactionID() {
        return ZemullaTransactionID;
    }

    public void setZemullaTransactionID(String ZemullaTransactionID) {
        this.ZemullaTransactionID = ZemullaTransactionID;
    }
}
