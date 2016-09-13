package com.zemulla.android.app.widgets;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.account.OTPGenValAPI;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.helper.RetrofitErrorHelper;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.account.optgenval.OTPGenValRequest;
import com.zemulla.android.app.model.account.optgenval.OTPGenValResponse;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 04-08-2016.
 */
public class OTPDialogAfterLogin extends MaterialDialog {

    private ImageView imgClose;
    private TfEditText edtOTP;
    private TfButton btnOk, btnResend, btnChangeEmail;
    private TextView displayTextView;
    MaterialDialog materialDialog;
    private OTPGenValRequest otpGenValRequest;
    private OTPGenValAPI otpGenValAPI;
    private LoginResponse loginResponse;
    private View customView;

    private OnSubmitListener onSubmitListener;


    public OTPDialogAfterLogin(Builder builder) {
        super(builder);
        init(builder);
        otpGenValRequest = new OTPGenValRequest();
        otpGenValAPI = new OTPGenValAPI();
    }

    private void init(Builder builder) {
        customView = View.inflate(getContext(), R.layout.dialog_otp, null);

        materialDialog = builder.customView(customView, true)
                .cancelable(false)
                .build();
        ButterKnife.bind(customView);
        loginResponse = PrefUtils.getUserProfile(getContext());
        initViews(customView);
    }

    private void initViews(View customView) {

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
                dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.hideKeyPad(getContext(), customView);

                if (TextUtils.isEmpty(edtOTP.getText().toString().trim())) {
                    Functions.showError(getContext(), "Please Enter OTP", false);

                } else if (Functions.getLength(edtOTP) < 6) {
                    Functions.showError(getContext(), "Please Enter Valid OTP", false);

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
                generateOPT();
            }
        });

    }

    public void show() {

        materialDialog.show();
    }

    public void dismiss() {
        materialDialog.dismiss();
    }

    public void generateOPT() {

        otpGenValRequest.setCallingCode(loginResponse.getCallingCode());
        otpGenValRequest.setMobile(loginResponse.getMobile());
        otpGenValRequest.setUserID(loginResponse.getUserID());
        otpGenValAPI.otpGenVal(otpGenValRequest, otpApiListener);

    }

    APIListener<OTPGenValResponse> otpApiListener = new APIListener<OTPGenValResponse>() {
        @Override
        public void onResponse(Response<OTPGenValResponse> response) {

            try {

                if (response.isSuccessful() && response.body() != null) {
                    onSubmitListener.OTPReceived();
                    //Todo remove this OTPResponseSuccess oon release time
                    try {
                        if (response.body().getResponse().getResponseCode() == AppConstant.OTPResponseSuccess) {
                            if (!isShowing()) {
                                edtOTP.setText("");
                                show();
                                setDisplayText(false, otpGenValRequest.getMobile(), "");
                                Toast.makeText(getContext(), response.body().getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Functions.showError(getContext(), response.body().getResponse().getResponseMsg(), false);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    onSubmitListener.OTPReceived();
                    Functions.showError(getContext(), "Something went wrong. Please try again.", false);
                }
            } catch (Exception e) {
                e.printStackTrace();
                onSubmitListener.OTPReceived();
                Functions.showError(getContext(), "Something went wrong. Please try again.", false);
            }
        }

        @Override
        public void onFailure(Call<OTPGenValResponse> call, Throwable t) {
            onSubmitListener.OTPReceived();
            RetrofitErrorHelper.showErrorMsg(t, getOwnerActivity());
        }
    };


    public interface OnSubmitListener {
        void onSubmit(String OTP);

        void OTPReceived();
    }

    public void setOnSubmitListener(OnSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    public void setDisplayText(boolean isEmail, String PhoneNumber, String emailID) {

        //We have sent an OTP to your registered mobile number and email. Enter OTP below.
        if (!isEmail) {
            displayTextView.setText(String.format("We have sent an OTP to your registered mobile number %s.", replaceLastFour(PhoneNumber)));
        } else {
            displayTextView.setText(String.format("We have sent an OTP to your registered email %s.", replaceLastFour(PhoneNumber)));
        }
    }

    public String replaceLastFour(String s) {
        int length = s.length();
        //Check whether or not the string contains at least four characters; if not, this method is useless
        if (length < 4) {
            return "Error: The provided string is not greater than four characters long.";
        }
        return s.substring(0, length - 4) + "****";
    }

}
