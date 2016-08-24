package com.zemulla.android.app.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zemulla.android.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by raghavthakkar on 23-08-2016.
 */
public class ReportTile extends LinearLayout {

    @BindView(R.id.headerText)
    TextView headerText;
    @BindView(R.id.amountText)
    TextView amountText;

    private View rootView;
    private Unbinder unbinder;


    public ReportTile(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = inflate(context, R.layout.item_trasaction_report, this);
        unbinder = ButterKnife.bind(rootView);
    }


    public void setAmountText(String amount) {
        amountText.setText(amount);
    }

    public void setHeaderText(String header) {
        headerText.setText(header);
    }
}
