package com.zemulla.android.app.emarket.dstv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.model.month.Month;
import com.zemulla.android.app.widgets.TfTextView;

/**
 * Created by raghavthakkar on 15-08-2016.
 */
public class MonthAdapter extends ArrayAdapter<Month> {


    private Context context;
    private LayoutInflater inflater;

    public MonthAdapter(Context context) {
        super(context, 0);
        this.context = context;
        inflater = LayoutInflater.from(context);
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

        Month month = getItem(position);
        viewHolder.txtTitle.setText(String.format("%s", month.getDisplayText()));
        return convertView;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.spinner_list_header, null);
        TextView txt = (TextView) view.findViewById(R.id.txtTitle);
        txt.setTypeface(Functions.getLatoFont(context));
        Month month = getItem(position);
        txt.setText(String.format("%s", month.getDisplayText()));

        return view;
    }

    class ViewHolder {

        TfTextView txtTitle;

        ViewHolder() {

        }
    }
}
