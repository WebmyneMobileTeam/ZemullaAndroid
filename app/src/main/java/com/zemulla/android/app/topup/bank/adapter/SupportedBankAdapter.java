package com.zemulla.android.app.topup.bank.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zemulla.android.app.R;
import com.zemulla.android.app.model.payment.getsupportedbankdetails.GetSupportedBankDetails;
import com.zemulla.android.app.widgets.TfTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by raghavthakkar on 10-08-2016.
 */
public class SupportedBankAdapter extends RecyclerView.Adapter<SupportedBankAdapter.SupportedBankViewHolder> {
    private final Context context;


    public void setItems(List<GetSupportedBankDetails> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    private List<GetSupportedBankDetails> items;

    public SupportedBankAdapter(List<GetSupportedBankDetails> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public SupportedBankViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_otherbank, parent, false);
        return new SupportedBankViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SupportedBankViewHolder holder, int position) {
        GetSupportedBankDetails item = items.get(position);
        holder.setDetails(item);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class SupportedBankViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.branchNameTextView)
        TfTextView branchNameTextView;
        @BindView(R.id.accountName)
        TfTextView accountName;
        @BindView(R.id.accountNumber)
        TfTextView accountNumber;
        @BindView(R.id.accountBranch)
        TfTextView accountBranch;
        @BindView(R.id.swiftCode)
        TfTextView swiftCode;
        @BindView(R.id.mainHolder)
        CardView mainHolder;


        public SupportedBankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setDetails(GetSupportedBankDetails details) {
            try {
                branchNameTextView.setText(String.format("%s Branch", details.getBranchName()));
                accountName.setText(String.format("Account Name : %s", details.getAccountHolderName()));
                accountNumber.setText(String.format("Account Number : %s", details.getAccountNumber()));
                accountBranch.setText(String.format("Account Branch : %s", details.getBranchName()));
                swiftCode.setText(String.format("Swift Code : %s", details.getBankSWIFTCode()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}