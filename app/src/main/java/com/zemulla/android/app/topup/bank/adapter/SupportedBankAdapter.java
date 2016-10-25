package com.zemulla.android.app.topup.bank.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        @BindView(R.id.branchText)
        TfTextView branchText;
        @BindView(R.id.branchValue)
        TfTextView branchValue;
        @BindView(R.id.accountNameText)
        TfTextView accountNameText;
        @BindView(R.id.accountNameValue)
        TfTextView accountNameValue;
        @BindView(R.id.accountNumberText)
        TfTextView accountNumberText;
        @BindView(R.id.accountNumberValue)
        TfTextView accountNumberValue;
        @BindView(R.id.accountBranchText)
        TfTextView accountBranchText;
        @BindView(R.id.accountBranchValue)
        TfTextView accountBranchValue;
        @BindView(R.id.swiftCodeText)
        TfTextView swiftCodeText;
        @BindView(R.id.swiftCodeValue)
        TfTextView swiftCodeValue;
        @BindView(R.id.mainHolder)
        CardView mainHolder;

        public SupportedBankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setDetails(GetSupportedBankDetails details) {
            try {
                branchValue.setText(details.getBranchName());
                accountNameValue.setText(details.getAccountHolderName());
                accountNumberValue.setText(details.getAccountNumber());
                accountBranchValue.setText(details.getBranchName());
                swiftCodeValue.setText(details.getBankSWIFTCode());
            } catch (Exception e) {
                Log.d("error","Exception");
            }

        }
    }
}