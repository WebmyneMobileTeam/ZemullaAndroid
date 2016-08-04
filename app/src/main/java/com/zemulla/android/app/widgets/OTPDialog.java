package com.zemulla.android.app.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
    private TfButton btnOk, btnResend;
    private onSubmitListener onSubmitListener;

    public OTPDialog(Context context, onSubmitListener onSubmitListener) {
        this.context = context;
        this.onSubmitListener = onSubmitListener;

        View customView = View.inflate(context, R.layout.dialog_otp, null);

        materialDialog = new MaterialDialog.Builder(context)
                .customView(customView, true)
                .build();

        init(customView);

    }

    private void init(View customView) {
        imgClose = (ImageView) customView.findViewById(R.id.imgClose);
        edtOTP = (TfEditText) customView.findViewById(R.id.edtOTP);
        btnOk = (TfButton) customView.findViewById(R.id.btnOk);
        btnResend = (TfButton) customView.findViewById(R.id.btnResend);

        actionListener();

    }

    private void actionListener() {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtOTP.getText().toString().trim())) {
                    Functions.showError(context, "Enter OTP", false);

                } else if (Functions.getLength(edtOTP) < 6) {
                    Functions.showError(context, "Enter Valid OTP", false);

                } else {
                    materialDialog.dismiss();
                    if (onSubmitListener != null) {
                        onSubmitListener.onSubmit();
                    }
                }
            }
        });

        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "OTP Resend", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void show() {
        materialDialog.show();
    }

    public interface onSubmitListener {
        void onSubmit();
    }
}
