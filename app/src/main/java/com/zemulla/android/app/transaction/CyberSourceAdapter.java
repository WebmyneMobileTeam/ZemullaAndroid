package com.zemulla.android.app.transaction;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zemulla.android.app.R;

import java.util.ArrayList;

/**
 * Created by krishnakumar on 29-07-2016.
 */

public class CyberSourceAdapter extends RecyclerView.Adapter<CyberSourceAdapter.ViewHolder> {

    private ArrayList<CyberSourceBean> mainBean;

    public CyberSourceAdapter(ArrayList<CyberSourceBean> data) {
        this.mainBean = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trans_cyber_source, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CyberSourceBean data = mainBean.get(position);
        holder.txtName.setText(data.getFirstName()+" "+data.lastName);
        holder.txtZemulaTransID.setText(data.amount);
        holder.txtDate.setText(data.getZemullaTransDate());

        holder.txtStatus.setText("Status: "+data.getPaymentStatus());
        if(data.getPaymentStatus().toString().equalsIgnoreCase("Fail"))
        holder.txtStatus.setTextColor(Color.RED);
        else
            holder.txtStatus.setTextColor(Color.parseColor("#0a545c"));

        holder.txtAmount.setText("Amount: "+data.getAmount());
        holder.txtCharge.setText("Charge: "+data.getCharge());
        holder.txtTotalAmount.setText("TOTAL: "+data.getTotalAmount());
    }

    @Override
    public int getItemCount() {
        return mainBean.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtZemulaTransID, txtDate,txtStatus,txtAmount,txtCharge,txtTotalAmount;
        public ViewHolder(View view) {
            super(view);
            txtZemulaTransID= (TextView) view.findViewById(R.id.txtZemulaTransID);
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtDate = (TextView) view.findViewById(R.id.txtDate);
            txtStatus = (TextView) view.findViewById(R.id.txtStatus);
            txtAmount= (TextView)view.findViewById(R.id.txtAmount);
            txtCharge= (TextView)view.findViewById(R.id.txtCharge);
            txtTotalAmount= (TextView)view.findViewById(R.id.txtTotalAmount);
        }
    }
}
