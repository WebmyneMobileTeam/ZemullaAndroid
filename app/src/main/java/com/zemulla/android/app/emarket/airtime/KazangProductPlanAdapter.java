package com.zemulla.android.app.emarket.airtime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.model.kazang.getkazangproductplan.ProductPlan;
import com.zemulla.android.app.widgets.TfTextView;

/**
 * Created by raghavthakkar on 15-08-2016.
 */
public class KazangProductPlanAdapter extends ArrayAdapter<ProductPlan> {


    private Context context;
    private LayoutInflater inflater;

    public KazangProductPlanAdapter(Context context) {
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

        ProductPlan productPlan = getItem(position);
        viewHolder.txtTitle.setText(String.format("%s", productPlan.getDescription()));
        return convertView;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.spinner_list_header, null);
        TextView txt = (TextView) view.findViewById(R.id.txtTitle);
        txt.setTypeface(Functions.getLatoFont(context));
        ProductPlan productPlan = getItem(position);
        txt.setText(String.format("%s", productPlan.getDescription()));

        return view;
    }

    class ViewHolder {

        TfTextView txtTitle;

        ViewHolder() {

        }
    }
}
