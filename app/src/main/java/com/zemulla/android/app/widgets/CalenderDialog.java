package com.zemulla.android.app.widgets;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.DateUtils;
import com.zemulla.android.app.helper.Functions;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by raghavthakkar on 26-08-2016.
 */
public class CalenderDialog extends DialogFragment {


    @BindView(R.id.imgClose)
    ImageView imgClose;
    @BindView(R.id.edtFromDate)
    TfTextView edtFromDate;
    @BindView(R.id.edtToDate)
    TfTextView edtToDate;
    @BindView(R.id.changeEmail)
    TfButton changeEmail;
    @BindView(R.id.btnOk)
    TfButton btnOk;
    @BindView(R.id.btnClear)
    TfButton btnClear;
    OnSuccessListener onSuccessListener;
    private String fromDateValue="", toDateValue = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_date, container, false);
        ButterKnife.bind(this, view);
        if (!TextUtils.isEmpty(fromDateValue) && !TextUtils.isEmpty(toDateValue)) {
            edtFromDate.setText(fromDateValue);
            edtToDate.setText(toDateValue);
        }
        return view;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // dialog.getWindow().getAttributes().width = (int) (width * .8);
        return dialog;
    }

    @OnClick({R.id.imgClose, R.id.btnOk, R.id.edtFromDate, R.id.edtToDate, R.id.btnClear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgClose:
                dismiss();
                break;
            case R.id.btnOk:

                try {
                    Date fromDate = DateUtils.parseDate(Functions.toStingTextView(edtFromDate), DateUtils.mDDMMYYYY);
                    Date toDate = DateUtils.parseDate(Functions.toStingTextView(edtToDate), DateUtils.mDDMMYYYY);
                    if (fromDate.after(toDate)) {
                        Functions.showError(getActivity(), "Invalid Date Selection", false);
                        return;
                    }

                    Calendar calendar = Calendar.getInstance();
                    if (fromDate.after(calendar.getTime()) || toDate.after(calendar.getTime())) {
                        Functions.showError(getActivity(), "Invalid Date Selection", false);
                        return;
                    }

                    dismiss();
                    onSuccessListener.onSuccess(
                            DateUtils.formatDate(fromDate, DateUtils.mDDMMYYYY),
                            DateUtils.formatDate(toDate, DateUtils.mDDMMYYYY));
                } catch (Exception e) {

                    Functions.showError(getActivity(), "Invalid Date Selection", false);
                }

                break;
            case R.id.edtFromDate:
                showDateDialog(edtFromDate);
                break;
            case R.id.edtToDate:
                showDateDialog(edtToDate);
                break;
            case R.id.btnClear:
                setDefaultData();
                break;

        }
    }

    private void setDefaultData() {

        edtToDate.setText(getString(R.string.select_to_date));
        edtFromDate.setText(getString(R.string.select_from_date));
    }

    private void showDateDialog(final TfTextView textView) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = DateUtils.parseDate(Functions.toStingTextView(textView), DateUtils.mDDMMYYYY);
            calendar.setTime(date);

        } catch (Exception e) {
            //e.printStackTrace();
        }


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textView.setText(dayOfMonth + "-"
                        + (monthOfYear + 1) + "-" + year);

            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)

        );
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTime().getTime());
        datePickerDialog.show();


    }


    public void showDialog(String fromValue, String toValue, FragmentManager fragmentManager, OnSuccessListener onSuccessListener) {

        fromDateValue = fromValue;
        toDateValue = toValue;

        show(fragmentManager, "");
        this.onSuccessListener = onSuccessListener;
    }

    public interface OnSuccessListener {

        void onSuccess(String fromDate, String toDate);
    }


}
