package com.zemulla.android.app.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.account.LoginAPI;
import com.zemulla.android.app.api.account.OTPGenValTemporaryAPI;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.AdvancedSpannableString;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.home.HomeActivity;
import com.zemulla.android.app.home.LogUtils;
import com.zemulla.android.app.model.account.country.Country;
import com.zemulla.android.app.model.account.login.LoginRequest;
import com.zemulla.android.app.model.account.login.LoginResponse;
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


public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.imgLogo)
    AppCompatImageView imgLogo;
    @BindView(R.id.countryPicker)
    CountryPickerView countryPicker;

    //    @BindView(R.id.headerView)
//    TextView headerView;

    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.txtForgotPass)
    Button txtForgotPass;

    Country mSelectedCountry = null;
    Unbinder unbinder;
    ProgressDialog progressDialog;
    LoginAPI loginAPI;
    LoginRequest loginRequest;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    @BindView(R.id.mainHolder)
    FrameLayout mainHolder;
    @BindView(R.id.txtBottom)
    TextView txtBottom;


    private OTPDialog otpDialog;
    private OTPGenValTemporaryAPI otpGenValTemporaryAPI;
    private OTPGenValRequest otpGenValTemporaryRequest;
    private OTPGenValResponse otpGenValTemporaryResponse;
    private LoginResponse loginResponse;
    private int RequestCode = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);

        init();

    }

    private void init() {
        loginAPI = new LoginAPI();
        loginRequest = new LoginRequest();
        otpGenValTemporaryAPI = new OTPGenValTemporaryAPI();
        otpGenValTemporaryRequest = new OTPGenValRequest();
        setSignUpText();
        initProgressDialog();
        showProgressDialog();

        countryPicker.fetchCountry();
        countryPicker.setCountryPickerListener(countryPickerListener);

    }

    CountryPickerView.CountryPickerListener countryPickerListener = new CountryPickerView.CountryPickerListener() {
        @Override
        public void OnFailed(Throwable t) {
            Toast.makeText(LoginActivity.this, "Failed to load country.", Toast.LENGTH_LONG).show();
            hidProgressDialog();
        }

        @Override
        public void OnSelected(Country country) {
            mSelectedCountry = country;
            hidProgressDialog();
            LogUtils.LOGV("Selected Country", country.getCountryName());

        }
    };

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

    private void submitOTP(String OTP) {
        try {
            if (otpGenValTemporaryResponse != null && loginResponse != null) {
                otpGenValTemporaryRequest.setMobile(loginResponse.getMobile());
                otpGenValTemporaryRequest.setCallingCode(loginResponse.getMobile());
                otpGenValTemporaryRequest.setEmail(loginResponse.getMobile());
                otpGenValTemporaryRequest.setEmailTempleteID(0);
                otpGenValTemporaryRequest.setVerificationCode(OTP);
                otpGenValTemporaryRequest.setUniqueID(otpGenValTemporaryResponse.getUniqueID());
                showProgressDialog();
                otpGenValTemporaryAPI.otpGenValTemporary(otpGenValTemporaryRequest, submitotpGenValTemporaryResponseAPIListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

                Intent intent = new Intent(LoginActivity.this, ChangeEmailActivity.class);
                startActivityForResult(intent, RequestCode);


            }
        });

    }

    private void showOTPDialog() {

        if (otpDialog == null) {
            initOTPDiaLog();
        }

        if (loginResponse != null) {
            otpDialog.setDisplayText(true, loginResponse.getEmail(), "");
            otpDialog.showChangeEmail();
        }
        if (!otpDialog.isShowing()) {
            otpDialog.show();
        }
    }

    private void hideOTPDialog() {
        otpDialog.disMissDiaLog();
    }

    APIListener<OTPGenValResponse> submitotpGenValTemporaryResponseAPIListener = new APIListener<OTPGenValResponse>() {
        @Override
        public void onResponse(Response<OTPGenValResponse> response) {
            hidProgressDialog();
            try {
                if (response.isSuccessful()) {

                    otpGenValTemporaryResponse = response.body();
                    if (otpGenValTemporaryResponse != null && otpGenValTemporaryResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        Toast.makeText(LoginActivity.this, otpGenValTemporaryResponse.getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
                        otpDialog.disMissDiaLog();
                        saveDataAndLogin();

                    } else {
                        Toast.makeText(LoginActivity.this, otpGenValTemporaryResponse.getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
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

    private void GenerateOTP() {
        try {
            if (loginResponse != null) {
                otpGenValTemporaryRequest.setMobile(loginResponse.getMobile());
                otpGenValTemporaryRequest.setCallingCode(loginResponse.getCallingCode());
                otpGenValTemporaryRequest.setEmail(loginResponse.getEmail());
                otpGenValTemporaryRequest.setEmailTempleteID(0);
                otpGenValTemporaryRequest.setUniqueID("");
                otpGenValTemporaryRequest.setVerificationCode("");
                showProgressDialog();
                otpGenValTemporaryAPI.otpGenValTemporary(otpGenValTemporaryRequest, otpGenValTemporaryResponseAPIListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    APIListener<OTPGenValResponse> otpGenValTemporaryResponseAPIListener = new APIListener<OTPGenValResponse>() {
        @Override
        public void onResponse(Response<OTPGenValResponse> response) {
            hidProgressDialog();
            try {
                if (response.isSuccessful()) {

                    showOTPDialog();
                    otpGenValTemporaryResponse = response.body();
                    if (otpGenValTemporaryResponse != null && otpGenValTemporaryResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {

                        Toast.makeText(LoginActivity.this, otpGenValTemporaryResponse.getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, otpGenValTemporaryResponse.getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
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

    @OnClick({R.id.btnLogin, R.id.txtForgotPass})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                checkValidation();
                break;
            case R.id.txtForgotPass:
                Intent txtForgotPassActivity = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(txtForgotPassActivity);
                break;
        }
    }

    private void checkValidation() {

        if (mSelectedCountry == null) {
            Functions.showError(this, "Please Select Country.", false);
            return;
        }

        if (Functions.isEmpty(countryPicker.getEditText())) {
            Functions.showError(this, "Please Enter Mobile Number.", false);
            return;
        }

        if (countryPicker.getPhoneNumber().length() < 10) {
            Functions.showError(this, "Invalid Mobile Number.", false);
            return;
        }

        if (Functions.isEmpty(passwordEditText)) {
            Functions.showError(this, "Please Enter password.", false);
            return;
        }

        doLogin();

    }

    private void doLogin() {
        loginRequest.setCallingCode(mSelectedCountry.getCallingCode());
        loginRequest.setUsername(Functions.toStingEditText(countryPicker.getEditText()));
        loginRequest.setPassword(Functions.toStingEditText(passwordEditText));
        loginRequest.setRoleID(AppConstant.ClientRoleID);

        showProgressDialog();

        loginAPI.login(loginRequest, new APIListener<LoginResponse>() {
            @Override
            public void onResponse(Response<LoginResponse> response) {
                hidProgressDialog();
                try {
                    if (response.isSuccessful()) {
                        loginResponse = response.body();
                        if (loginResponse != null) {
                            if (loginResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess && loginResponse.isIsEmailVerified() && loginResponse.isIsEmailVerified()) {
                                saveDataAndLogin();
                            } else if (loginResponse.getUserID() == 0 && loginResponse.getResponse().getResponseCode() == AppConstant.WorngCredential) {
                                Functions.showError(LoginActivity.this, loginResponse.getResponse().getResponseMsg(), false);
                            } else if (!loginResponse.isIsEmailVerified()) {
                                GenerateOTP();
                            } else {
                                Functions.showError(LoginActivity.this, loginResponse.getResponse().getResponseMsg(), false);

                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                hidProgressDialog();
            }
        });
    }

    private void saveDataAndLogin() {
        PrefUtils.setUserProfile(LoginActivity.this, loginResponse);
        PrefUtils.setLoggedIn(LoginActivity.this, true);
        Intent txtForgotPassActivity = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(txtForgotPassActivity);
        finish();

        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
    }

    public void setSignUpText() {

        AdvancedSpannableString span = new AdvancedSpannableString(getResources().getString(R.string.signup_msg));
        span.setBold("Signup");
        span.setUnderLine("Signup");
        span.setColor(ContextCompat.getColor(this, R.color.yellow), "Signup");
        span.setColor(ContextCompat.getColor(this, R.color.colorDark), "Don't have an account?");

        span.setClickableSpanTo("Signup");

        span.setSpanClickListener(new AdvancedSpannableString.OnClickableSpanListner() {
            @Override
            public void onSpanClick() {
                Intent iHomeActivity = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(iHomeActivity);
            }
        });
        txtBottom.setText(span);
        txtBottom.setMovementMethod(LinkMovementMethod.getInstance());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (otpDialog != null) {
                hideOTPDialog();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unbinder.unbind();
            otpGenValTemporaryAPI.onDestory();
            loginAPI.onDestory();
            removeListener(submitotpGenValTemporaryResponseAPIListener);
            removeListener(otpGenValTemporaryResponseAPIListener);
            countryPickerListener = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void removeListener(APIListener<?> listener) {

        if (listener != null) {
            listener = null;
        }

    }

    public void createAccountClick(View view) {
    }
}
