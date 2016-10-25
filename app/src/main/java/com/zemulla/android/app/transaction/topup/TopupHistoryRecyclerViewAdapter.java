package com.zemulla.android.app.transaction.topup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zemulla.android.app.R;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.ServiceDetails;
import com.zemulla.android.app.model.reports.getkazangairtimedetails.AirtimeDetails;
import com.zemulla.android.app.model.reports.getkazangdirectrechargedetails.DirectRechargeReport;
import com.zemulla.android.app.model.reports.getkazangdstvpaymentdetails.DSTVPaymentReport;
import com.zemulla.android.app.model.reports.getkazangelectricitydetails.ElectricityDetailsReport;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.TopUpApiReportDetails;
import com.zemulla.android.app.model.reports.w2w.W2WReport;
import com.zemulla.android.app.topup.transaction.bank.BankReport;
import com.zemulla.android.app.topup.transaction.cybersource.CyberSourceReport;
import com.zemulla.android.app.topup.transaction.paypal.PayPalReport;
import com.zemulla.android.app.widgets.TfTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by raghavthakkar on 19-08-2016.
 */
public class TopupHistoryRecyclerViewAdapter<T> extends RecyclerView.Adapter<TopupHistoryRecyclerViewAdapter.HistoryViewHolder> {
    private final Context context;
    private List<Object> items;


    private int serviceDetailsId;

    public TopupHistoryRecyclerViewAdapter(List<Object> items, Context context) {
        this.items = items;
        this.context = context;

    }

    public void setItems(List<Object> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setServiceDetailsId(int serviceDetailsId) {
        this.serviceDetailsId = serviceDetailsId;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trasaction_history, parent, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {


        if (serviceDetailsId == ServiceDetails.TopUpByAdmin.getId()) {
            BankReport bankReport = (BankReport) items.get(position);
            holder.setTotUpBankDetails(bankReport, true);
        } else if (serviceDetailsId == ServiceDetails.CyberSource.getId()) {
            CyberSourceReport cyberSourceReport = (CyberSourceReport) items.get(position);
            holder.setCyberSourceDetails(cyberSourceReport);
        } else if (serviceDetailsId == ServiceDetails.PaypalPayment.getId()) {
            PayPalReport item = (PayPalReport) items.get(position);
            holder.setPayPalDetails(item);
        } else if (serviceDetailsId == ServiceDetails.MTNCredit.getId() || serviceDetailsId == ServiceDetails.AirtelCredit.getId()) {
            TopUpApiReportDetails item = (TopUpApiReportDetails) items.get(position);
            holder.setTopUpMTNAndAirtelDetails(item, true);
        } else if (serviceDetailsId == ServiceDetails.ZoonaCredit.getId() || serviceDetailsId == ServiceDetails.ZoonaDebit.getId()) {
            TopUpApiReportDetails item = (TopUpApiReportDetails) items.get(position);
            holder.setTopUpZoonaDetails(item);
        } else if (serviceDetailsId == ServiceDetails.WalletToWallet.getId()) {
            W2WReport item = (W2WReport) items.get(position);
            holder.setW2WDetails(item);
        } else if (serviceDetailsId == ServiceDetails.MTNDebit.getId() || serviceDetailsId == ServiceDetails.AirtelDebit.getId()) {
            TopUpApiReportDetails item = (TopUpApiReportDetails) items.get(position);
            holder.setTopUpMTNAndAirtelDetails(item, false);
        } else if (serviceDetailsId == ServiceDetails.WithdrawalByAdmin.getId()) {
            BankReport bankReport = (BankReport) items.get(position);
            holder.setTotUpBankDetails(bankReport, false);
        } else if (serviceDetailsId == ServiceDetails.AirtimeByVoucher.getId()) {
            AirtimeDetails airtimeDetails = (AirtimeDetails) items.get(position);
            holder.setAirtimeDetails(airtimeDetails);
        } else if (serviceDetailsId == ServiceDetails.KazangElectricity.getId()) {
            ElectricityDetailsReport electricityDetailsReport = (ElectricityDetailsReport) items.get(position);
            holder.setElectricityDetailsReport(electricityDetailsReport);
        } else if (serviceDetailsId == ServiceDetails.KazangDirectRecharge.getId()) {
            DirectRechargeReport directRechargeReport = (DirectRechargeReport) items.get(position);
            holder.setDirectRechargeReport(directRechargeReport);
        } else if (serviceDetailsId == ServiceDetails.DSTVPayment.getId()) {
            DSTVPaymentReport dstvPaymentReport = (DSTVPaymentReport) items.get(position);
            holder.setDSTVPaymentReport(dstvPaymentReport);
        }
        //TODO Fill in your logic for binding the view.
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }


    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.transactionIdText)
        TfTextView transactionIdText;
        @BindView(R.id.transactionIdValue)
        TfTextView transactionIdValue;
        @BindView(R.id.transactionDateText)
        TfTextView transactionDateText;
        @BindView(R.id.transactionDateValue)
        TfTextView transactionDateValue;
        @BindView(R.id.extraOneText)
        TfTextView extraOneText;
        @BindView(R.id.extraOneValue)
        TfTextView extraOneValue;
        @BindView(R.id.extraTwoText)
        TfTextView extraTwoText;
        @BindView(R.id.extraTwoValue)
        TfTextView extraTwoValue;
        @BindView(R.id.extraThreeText)
        TfTextView extraThreeText;
        @BindView(R.id.extraThreeValue)
        TfTextView extraThreeValue;
        @BindView(R.id.amountText)
        TfTextView amountText;
        @BindView(R.id.amountValue)
        TfTextView amountValue;
        @BindView(R.id.totalChargeText)
        TfTextView totalChargeText;
        @BindView(R.id.totalChargeValue)
        TfTextView totalChargeValue;
        @BindView(R.id.totalPayableAmountText)
        TfTextView totalPayableAmountText;
        @BindView(R.id.totalPayableAmountValue)
        TfTextView totalPayableAmountValue;
        @BindView(R.id.paymentStatusText)
        TfTextView paymentStatusText;
        @BindView(R.id.paymentStatusValue)
        TfTextView paymentStatusValue;


        public HistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setDetails(TopUpApiReportDetails item) {

        }

        public void setCyberSourceDetails(CyberSourceReport item) {

            setVisiblity(false, false, false);
            transactionIdValue.setText(item.getZemullaTransactionID());
            transactionDateValue.setText(item.getCreatedDate());

            amountValue.setText(item.getAmount());
            totalChargeValue.setText(item.getTotalCharge());
            totalPayableAmountValue.setText(item.getTotalPayableAmount());
            paymentStatusValue.setText(item.getIsPaidSuccess());
        }

        public void setPayPalDetails(PayPalReport item) {

            setVisiblity(true, true, false);

            transactionIdValue.setText(item.getZemullaTransactionID());
            transactionDateValue.setText(item.getCreatedDate());

            extraOneText.setText("Total Payable Amount");
            extraOneValue.setText(String.format("%s %s", AppConstant.ZMW, item.getPayableAmount()));

            extraTwoText.setText("Conversion Rate");
            extraTwoValue.setText(item.getUSDRate());

            amountValue.setText(item.getRequestedAmount());
            totalChargeValue.setText(item.getTransactionCharge());
            totalPayableAmountValue.setText(String.format("$ %s", item.getUSDAmount()));
            paymentStatusValue.setText(item.getIsPaidSuccess());
        }

        public void setTopUpMTNAndAirtelDetails(TopUpApiReportDetails item, boolean isShowNationID) {

            setVisiblity(isShowNationID, true, false);

            transactionIdValue.setText(item.getZemullaTransactionID());
            transactionDateValue.setText(item.getCreatedDate());

            extraOneText.setText("National ID");
            extraOneValue.setText(item.getNationalID());

            extraTwoText.setText("Mobile");
            extraTwoValue.setText(item.getMobileNo());

            amountValue.setText(item.getAmount());
            totalChargeValue.setText(item.getTotalCharge());
            totalPayableAmountValue.setText(item.getTotalPayableAmount());
            paymentStatusValue.setText(item.getIsPaidSuccess());

        }

        public void setTopUpZoonaDetails(TopUpApiReportDetails item) {

            setVisiblity(true, true, false);

            transactionIdValue.setText(item.getZemullaTransactionID());
            transactionDateValue.setText(item.getCreatedDate());

            extraOneText.setText("National ID");
            extraOneValue.setText(item.getNationalID());

            extraTwoText.setText("PIN");
            extraTwoValue.setText(item.getPIN());

            extraThreeText.setText("Mobile");
            extraThreeValue.setText(item.getMobileNo());

            amountValue.setText(item.getAmount());
            totalChargeValue.setText(item.getTotalCharge());
            totalPayableAmountValue.setText(item.getTotalPayableAmount());
            paymentStatusValue.setText(item.getIsPaidSuccess());

        }


        public void setTotUpBankDetails(BankReport item, boolean isShownBankName) {

            setVisiblity(isShownBankName, true, true);

            transactionIdValue.setText(item.getZemullaTransactionID());
            transactionDateValue.setText(item.getCreatedDate());

            extraOneText.setText("Bank Name");
            extraOneValue.setText(item.getBankName());

            extraTwoText.setText("Account Name");
            extraTwoValue.setText(item.getAccountName());

            extraThreeText.setText("Account Number");
            extraThreeValue.setText(item.getAccountNumber());

            amountValue.setText(item.getRequestedAmount());
            totalChargeValue.setText(item.getTransactionCharge());
            totalPayableAmountValue.setText(item.getPayableAmount());
            paymentStatusValue.setText(item.getIsPaidSuccess());


        }


        public void setW2WDetails(W2WReport item) {

            transactionIdValue.setText(item.getZemullaTransactionID());
            transactionDateValue.setText(item.getCreatedDate());

            extraOneText.setText("Receiver Name");
            extraOneValue.setText(item.getReceiverName());

            extraTwoText.setText("Receiver Mobile Number");
            extraTwoValue.setText(item.getReceiverMobileNumber());

            amountValue.setText(item.getRequestedAmount());
            totalChargeValue.setText(item.getTransactionCharge());
            totalPayableAmountValue.setText(item.getPayableAmount());
            paymentStatusValue.setText("Success");

        }


        public void setAirtimeDetails(AirtimeDetails item) {


            setVisiblity(true, true, false);

            transactionIdValue.setText(item.getZemullaTransactionID());
            transactionDateValue.setText(item.getCreatedDate());

            extraOneText.setText("Pin");
            extraOneValue.setText(item.getPin());

            extraTwoText.setText("Product");
            extraTwoValue.setText(item.getProduct());


            amountValue.setText(item.getAmount());
            totalChargeValue.setText(item.getTotalCharge());
            totalPayableAmountValue.setText(item.getTotalPayableAmount());
            paymentStatusValue.setText(item.getResponse_Code());

        }

        public void setElectricityDetailsReport(ElectricityDetailsReport item) {

            setVisiblity(true, true, false);

            transactionIdValue.setText(item.getZemullaTransactionID());
            transactionDateValue.setText(item.getCreatedDate());

            extraOneText.setText("Customer Address");
            extraOneValue.setText(item.getCustomer_Address());

            extraTwoText.setText("Meter Number");
            extraTwoValue.setText(item.getMeterNumber());


            amountValue.setText(item.getAmount());
            totalChargeValue.setText(item.getTotalCharge());
            totalPayableAmountValue.setText(item.getTotalPayableAmount());
            paymentStatusValue.setText(item.getResponse_Code2());

        }

        public void setDirectRechargeReport(DirectRechargeReport item) {

            setVisiblity(false, false, false);

            transactionIdValue.setText(item.getZemullaTransactionID());
            transactionDateValue.setText(item.getCreatedDate());
            amountValue.setText(item.getAmount());
            totalChargeValue.setText(item.getTotalCharge());
            totalPayableAmountValue.setText(item.getTotalPayableAmount());
            paymentStatusValue.setText(item.getResponse_Message());

        }

        public void setDSTVPaymentReport(DSTVPaymentReport item) {

            setVisiblity(false, false, false);

            transactionIdValue.setText(item.getZemullaTransactionID());
            transactionDateValue.setText(item.getCreatedDate());

            amountValue.setText(item.getAmount());
            totalChargeValue.setText(item.getTotalCharge());
            totalPayableAmountValue.setText(item.getTotalPayableAmount());
            paymentStatusValue.setText(item.getResponse_Code2());
        }

        public void setVisiblity(boolean isShowone, boolean isShowtwo, boolean isShowThree) {

            if (!isShowone) {
                extraOneText.setVisibility(View.GONE);
                extraOneValue.setVisibility(View.GONE);
            } else {
                extraOneText.setVisibility(View.VISIBLE);
                extraOneValue.setVisibility(View.VISIBLE);
            }

            if (!isShowtwo) {
                extraTwoText.setVisibility(View.GONE);
                extraTwoValue.setVisibility(View.GONE);
            } else {
                extraTwoText.setVisibility(View.VISIBLE);
                extraTwoValue.setVisibility(View.VISIBLE);
            }

            if (!isShowThree) {
                extraThreeText.setVisibility(View.GONE);
                extraThreeValue.setVisibility(View.GONE);
            } else {
                extraThreeText.setVisibility(View.VISIBLE);
                extraThreeValue.setVisibility(View.VISIBLE);
            }

        }
    }
}