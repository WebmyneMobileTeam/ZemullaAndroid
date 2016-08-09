package com.zemulla.android.app.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;

/**
 * Created by sagartahelyani on 04-08-2016.
 */
public class OTPDialog {

    private ImageView imgClose;
    private Context context;
    private MaterialDialog materialDialog;
    private TfEditText edtOTP;
    private TfButton btnOk, btnResend, btnChangeEmail;
    private onSubmitListener onSubmitListener;
    private TextView displayTextView;


    public OTPDialog(Context context, onSubmitListener onSubmitListener) {
        this.context = context;
        this.onSubmitListener = onSubmitListener;

        View customView = View.inflate(context, R.layout.dialog_otp, null);

        materialDialog = new MaterialDialog.Builder(context)
                .customView(customView, true)
                .cancelable(false)
                .build();

        init(customView);

    }

    private void init(View customView) {
        imgClose = (ImageView) customView.findViewById(R.id.imgClose);
        edtOTP = (TfEditText) customView.findViewById(R.id.edtOTP);
        btnOk = (TfButton) customView.findViewById(R.id.btnOk);
        btnChangeEmail = (TfButton) customView.findViewById(R.id.changeEmail);
        btnResend = (TfButton) customView.findViewById(R.id.btnResend);
        displayTextView = (TfTextView) customView.findViewById(R.id.displayTextView);

        btnChangeEmail.setVisibility(View.GONE);
        actionListener();

    }

    private void actionListener() {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disMissDiaLog();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.hideKeyPad(context, v);

                if (TextUtils.isEmpty(edtOTP.getText().toString().trim())) {
                    Functions.showError(context, "Enter OTP", false);

                } else if (Functions.getLength(edtOTP) < 6) {
                    Functions.showError(context, "Enter Valid OTP", false);

                } else {

                    if (onSubmitListener != null) {
                        onSubmitListener.onSubmit(Functions.toStingEditText(edtOTP));
                    }
                }
            }
        });

        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.hideKeyPad(context, v);
                onSubmitListener.onResend();
            }
        });


        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitListener.ChangeEmail();
            }
        });
    }

    public void show() {
        materialDialog.show();
    }


    public void setDisplayText(boolean isEmail, String PhoneNumber, String emailID) {

        //We have sent an OTP to your registered mobile number and email. Enter OTP below.
        if (!isEmail) {
            displayTextView.setText(String.format("We have sent an OTP to your registered mobile number %s.", replaceLastFour(PhoneNumber)));
        } else {
            displayTextView.setText(String.format("We have sent an OTP to your registered email %s.", replaceLastFour(PhoneNumber)));
        }
    }

    public interface onSubmitListener {
        void onSubmit(String OTP);

        void onResend();

        void ChangeEmail();
    }

    public String replaceLastFour(String s) {
        int length = s.length();
        //Check whether or not the string contains at least four characters; if not, this method is useless
        if (length < 4) {
            return "Error: The provided string is not greater than four characters long.";
        }
        return s.substring(0, length - 4) + "****";
    }

    public void disMissDiaLog() {
        materialDialog.dismiss();
    }


    public void showChangeEmail() {
        btnChangeEmail.setVisibility(View.VISIBLE);
    }
}
