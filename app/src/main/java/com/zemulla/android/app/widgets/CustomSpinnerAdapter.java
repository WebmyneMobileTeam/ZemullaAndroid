package com.zemulla.android.app.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.model.payment.getsupportedbankdetails.GetSupportedBankDetails;

import java.util.List;

/**
 * Created by priyasindkar on 03-08-2016.
 */
public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private final Context context;
    public void setAsr(List<GetSupportedBankDetails> asr) {
        this.asr = asr;
        asr.add(0, new GetSupportedBankDetails(true));
        notifyDataSetChanged();
    }

    private List<GetSupportedBankDetails> asr;
    private LayoutInflater inflater;

    public CustomSpinnerAdapter(Context context, List<GetSupportedBankDetails> asr) {
        this.asr = asr;
        this.context = context;
        inflater = (LayoutInflater.from(context));
    }


    public int getCount() {
        return asr.size();
    }

    public Object getItem(int i) {
        return asr.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.txtTitle = (TfTextView) convertView.findViewById(R.id.txtTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        GetSupportedBankDetails getSupportedBankDetails = asr.get(position);
        viewHolder.txtTitle.setText(String.format("%s %s", getSupportedBankDetails.getBankName(), getSupportedBankDetails.getBranchName()));
//        txt.setPadding(16, 16, 16, 16);
//        txt.setTypeface(Functions.getLatoFont(context));
//        //   txt.setTextSize(18);
//        txt.setGravity(Gravity.CENTER_VERTICAL);
        // txt.setText(asr.get(position));
//        txt.setTextColor(Color.parseColor("#000000"));
        return convertView;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        view = inflater.inflate(R.layout.spinner_list_header, null);
        GetSupportedBankDetails getSupportedBankDetails = asr.get(i);
        TextView txt = (TextView) view.findViewById(R.id.txtTitle);
        txt.setTypeface(Functions.getLatoFont(context));
        txt.setText(String.format("%s %s", getSupportedBankDetails.getBankName(), getSupportedBankDetails.getBranchName()));
        // txt.setTextSize(16);
//        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
        // txt.setText(asr.get(i));
//        txt.setTextColor(Color.parseColor("#000000"));
        return txt;
    }

    class ViewHolder {

        TfTextView txtTitle;

        ViewHolder() {


        }
    }
}