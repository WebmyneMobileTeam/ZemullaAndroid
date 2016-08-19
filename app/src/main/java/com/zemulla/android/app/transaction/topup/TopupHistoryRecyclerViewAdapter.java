package com.zemulla.android.app.transaction.topup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.ServiceDetails;
import com.zemulla.android.app.model.reports.gettopupapireportdetails.TopUpApiReportDetails;
import com.zemulla.android.app.topup.transaction.bank.TopUpBankTransferReport;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {


        if (serviceDetailsId == ServiceDetails.TopUpByAdmin.getId()) {
            TopUpBankTransferReport topUpBankTransferReport = (TopUpBankTransferReport) items.get(position);
            holder.setTotUpBankDetails(topUpBankTransferReport);
        } else if (serviceDetailsId == ServiceDetails.PaypalPayment.getId()) {
            PayPalReport item = (PayPalReport) items.get(position);
            holder.setDetails(item);
        } else if (serviceDetailsId == ServiceDetails.PaypalPayment.getId()) {
            TopUpApiReportDetails item = (TopUpApiReportDetails) items.get(position);
            holder.setDetails(item);
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
        @BindView(R.id.txtZemulaTransID)
        TextView txtZemulaTransID;

        @BindView(R.id.txtDate)
        TfTextView txtDate;

        @BindView(R.id.txtStatus)
        TfTextView txtStatus;

        @BindView(R.id.txtAmount)
        TfTextView txtAmount;

        @BindView(R.id.txtCharge)
        TfTextView txtCharge;

        @BindView(R.id.txtTotalAmount)
        TfTextView txtTotalAmount;


        public HistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setDetails(TopUpApiReportDetails item) {
            txtZemulaTransID.setText(item.getZemullaTransactionID());
            txtDate.setText(item.getCreatedDate());
            txtStatus.setText(String.format("Status :%s", item.getIsPaidSuccess()));
            txtAmount.setText(String.format("Amount : %s", item.getAmount()));
            txtCharge.setText(String.format("Charge : %s", item.getTotalCharge()));
            txtTotalAmount.setText(String.format("Payable Amount : %s", item.getTotalPayableAmount()));

        }

        public void setTotUpBankDetails(TopUpBankTransferReport item) {
            txtZemulaTransID.setText(item.getZemullaTransactionID());
            txtDate.setText(item.getCreatedDate());
            txtStatus.setText(String.format("Status :%s", item.getIsPaidSuccess()));
            txtAmount.setText(String.format("Amount : %s", item.getRequestedAmount()));
            txtCharge.setText(String.format("Charge : %s", item.getTransactionCharge()));
            txtTotalAmount.setText(String.format("Payable Amount : %s", item.getPayableAmount()));
        }

        public void setDetails(PayPalReport item) {

            txtZemulaTransID.setText(item.getZemullaTransactionID());
            txtDate.setText(item.getCreatedDate());
            txtStatus.setText(String.format("Status :%s", item.getIsPaidSuccess()));
            txtAmount.setText(String.format("Amount : %s", item.getRequestedAmount()));
            txtCharge.setText(String.format("Charge : %s", item.getTransactionCharge()));
            txtTotalAmount.setText(String.format("Payable Amount : %s", item.getPayableAmount()));
        }
    }
}