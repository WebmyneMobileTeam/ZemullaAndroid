package com.zemulla.android.app.model.reports.getkazangdirectrechargedetails;

/**
 * Created by raghavthakkar on 22-08-2016.
 */
public class DirectRechargeReport {



    private String Amount;
    private String CreatedDate;
    private String FirstName;
    private String LastName;
    private String Product_ID;
    private String Response_Code;
    private String Response_Message;
    private long ServiceDetailID;
    private String TotalCharge;
    private String TotalPayableAmount;
    private String Transaction_Reference;
    private String ZemullaTransactionID;
    private String balance;
    private String cost;
    private String product;

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

    public String getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(String Product_ID) {
        this.Product_ID = Product_ID;
    }

    public String getResponse_Code() {
        return Response_Code;
    }

    public void setResponse_Code(String Response_Code) {
        this.Response_Code = Response_Code;
    }

    public String getResponse_Message() {
        return Response_Message;
    }

    public void setResponse_Message(String Response_Message) {
        this.Response_Message = Response_Message;
    }

    public long getServiceDetailID() {
        return ServiceDetailID;
    }

    public void setServiceDetailID(long ServiceDetailID) {
        this.ServiceDetailID = ServiceDetailID;
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

    public String getTransaction_Reference() {
        return Transaction_Reference;
    }

    public void setTransaction_Reference(String Transaction_Reference) {
        this.Transaction_Reference = Transaction_Reference;
    }

    public String getZemullaTransactionID() {
        return ZemullaTransactionID;
    }

    public void setZemullaTransactionID(String ZemullaTransactionID) {
        this.ZemullaTransactionID = ZemullaTransactionID;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
