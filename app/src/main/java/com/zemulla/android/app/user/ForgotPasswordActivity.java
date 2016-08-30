package com.zemulla.android.app.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.account.ForgotPasswordAPI;
import com.zemulla.android.app.api.account.OTPGenValTemporaryAPI;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.home.LogUtils;
import com.zemulla.android.app.model.account.country.Country;
import com.zemulla.android.app.model.account.forgotpassword.ForgotPasswordRequest;
import com.zemulla.android.app.model.account.forgotpassword.ForgotPasswordResponse;
import com.zemulla.android.app.model.account.otpgenvaltemporary.OTPGenValRequest;
import com.zemulla.android.app.model.account.otpgenvaltemporary.OTPGenValResponse;
import com.zemulla.android.app.widgets.OTPDialog;
import com.zemulla.android.app.widgets.countrypicker.CountryPickerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.backToLogin)
    Button backToLogin;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.countryPicker)
    CountryPickerView countryPicker;

    @BindView(R.id.submbitButton)
    Button submbitButton;
    @BindView(R.id.emailEditText)
    EditText emailEditText;
    Country mSelectedCountry = null;
    boolean isMobile, isEmail;
    ForgotPasswordAPI forgotPasswordAPI;
    ForgotPasswordRequest forgotPasswordRequest;
    ForgotPasswordResponse forgotPasswordResponse;
    ProgressDialog progressDialog;
    private OTPDialog otpDialog;
    private OTPGenValTemporaryAPI otpGenValTemporaryAPI;
    private OTPGenValRequest otpGenValTemporaryRequest;
    private OTPGenValResponse otpGenValTemporaryResponse;
    Unbinder unbinder;
    private int RequestCode = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        unbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        initToolbar();
        initApplyFont();
        forgotPasswordAPI = new ForgotPasswordAPI();
        forgotPasswordRequest = new ForgotPasswordRequest();
        otpGenValTemporaryAPI = new OTPGenValTemporaryAPI();
        otpGenValTemporaryRequest = new OTPGenValRequest();
        initProgressDialog();
        countryPicker.fetchCountry();
        countryPicker.setCountryPickerListener(countryPickerListener);
    }


    CountryPickerView.CountryPickerListener countryPickerListener = new CountryPickerView.CountryPickerListener() {
        @Override
        public void OnFailed(Throwable t) {
            Toast.makeText(ForgotPasswordActivity.this, "Failed to load country.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void OnSelected(Country country) {

            mSelectedCountry = country;
            LogUtils.LOGD("Selected Country", country.getCountryName());

        }
    };

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.forgot_password);
        setSupportActionBar(toolbar);
    }

    private void initApplyFont() {

    }


    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
    }


    private void showProgressDialog() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    private void hidProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @OnClick(R.id.backToLogin)
    public void onbackToLoginClick() {
        finish();
    }

    @OnClick(R.id.submbitButton)
    public void onsubmbitButtonClick() {

        if (Functions.isEmpty(emailEditText) && Functions.isEmpty(countryPicker.getEditText())) {
            Functions.showError(this, "Enter EmailID or Mobile Number.", false);

        } else if (!Functions.isEmpty(emailEditText)) {
            if (!Functions.emailValidation(Functions.toStingEditText(emailEditText))) {
                Functions.showError(this, "Invalid E-mail.", false);
                isEmail = false;
                return;
            } else {
                isEmail = true;
            }

        } else if (!Functions.isEmpty(countryPicker.getEditText())) {
            if (countryPicker.getPhoneNumber().length() < 10) {
                Functions.showError(this, "Invalid Mobile Number.", false);
                isMobile = false;
                return;
            } else {
                isMobile = true;
            }

        }


        if (isEmail) {
            forgotPasswordRequest.setEmail(Functions.toStingEditText(emailEditText));
        } else if (isMobile) {
            forgotPasswordRequest.setMobile(countryPicker.getPhoneNumber());
            forgotPasswordRequest.setCallingCode(mSelectedCountry.getCallingCode());
        }

        GenerateOTP();


    }

    private void GenerateOTP() {
        showProgressDialog();
        forgotPasswordAPI.forgotPassword(forgotPasswordRequest, forgotPasswordResponseAPIListener);
    }

    APIListener<ForgotPasswordResponse> forgotPasswordResponseAPIListener = new APIListener<ForgotPasswordResponse>() {
        @Override
        public void onResponse(Response<ForgotPasswordResponse> response) {
            hidProgressDialog();
            try {
                if (response.isSuccessful() && response.body() != null) {

                    forgotPasswordResponse = response.body();
                    if (forgotPasswordResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        showOTPDialog();
                        Toast.makeText(ForgotPasswordActivity.this, forgotPasswordResponse.getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
                        Log.d("test", forgotPasswordResponse.getResponse().getResponseMsg());
                    } else {
                        Functions.showError(ForgotPasswordActivity.this, forgotPasswordResponse.getResponse().getResponseMsg(), false);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {

        }
    };


    private void initOTPDiaLog() {
        otpDialog = new OTPDialog(this, new OTPDialog.onSubmitListener() {
            @Override
            public void onSubmit(String OTP) {
                submitOTP(OTP);
            }

            @Override
            public void onResend() {
                GenerateOTP();
            }

            @Override
            public void ChangeEmail() {


            }
        });

    }

    private void showOTPDialog() {

        if (otpDialog == null) {
            initOTPDiaLog();
        }
        if (isEmail) {
            otpDialog.setDisplayText(true, Functions.toStingEditText(emailEditText), "");
        } else {

            otpDialog.setDisplayText(false, countryPicker.getPhoneNumber(), "");
        }

        otpDialog.show();
    }


    private void submitOTP(String OTP) {
        try {
            if (forgotPasswordResponse != null) {
                if (isEmail) {
                    otpGenValTemporaryRequest.setEmail(forgotPasswordResponse.getEmail());
                } else {
                    otpGenValTemporaryRequest.setCallingCode(forgotPasswordResponse.getCallingCode());
                    otpGenValTemporaryRequest.setMobile(forgotPasswordResponse.getMobile());
                }
                otpGenValTemporaryRequest.setEmailTempleteID(0);
                otpGenValTemporaryRequest.setVerificationCode(OTP);
                otpGenValTemporaryRequest.setUniqueID(forgotPasswordResponse.getUniqueID());
                showProgressDialog();
                otpGenValTemporaryAPI.otpGenValTemporary(otpGenValTemporaryRequest, submitotpGenValTemporaryResponseAPIListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    APIListener<OTPGenValResponse> submitotpGenValTemporaryResponseAPIListener = new APIListener<OTPGenValResponse>() {
        @Override
        public void onResponse(Response<OTPGenValResponse> response) {
            hidProgressDialog();
            try {
                if (response.isSuccessful()) {
                    otpGenValTemporaryResponse = response.body();
                    if (otpGenValTemporaryResponse != null && otpGenValTemporaryResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        Toast.makeText(ForgotPasswordActivity.this, otpGenValTemporaryResponse.getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
                        otpDialog.disMissDiaLog();
                        Intent intent = new Intent(ForgotPasswordActivity.this, ChangeForgotPasswordActivity.class);
                        intent.putExtra(Intent.EXTRA_TEXT, forgotPasswordResponse.getToken());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, otpGenValTemporaryResponse.getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<OTPGenValResponse> call, Throwable t) {
            hidProgressDialog();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unbinder.unbind();
            Functions.removeListener(submitotpGenValTemporaryResponseAPIListener);
            Functions.removeListener(forgotPasswordResponseAPIListener);
            otpGenValTemporaryAPI.onDestory();
            forgotPasswordAPI.onDestory();
            if (countryPickerListener != null) {
                countryPickerListener = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
