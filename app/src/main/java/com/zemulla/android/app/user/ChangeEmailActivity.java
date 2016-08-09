package com.zemulla.android.app.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;
import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.account.ChangeEmailAPI;
import com.zemulla.android.app.api.account.OTPGenValTemporaryAPI;
import com.zemulla.android.app.api.account.ValidateMobileEmailAPI;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.model.account.changeemail.ChangeEmailRequest;
import com.zemulla.android.app.model.account.changeemail.ChangeEmailResponse;
import com.zemulla.android.app.model.account.otpgenvaltemporary.OTPGenValRequest;
import com.zemulla.android.app.model.account.otpgenvaltemporary.OTPGenValResponse;
import com.zemulla.android.app.model.account.validatemobileemail.ValidateMobileEmailRequest;
import com.zemulla.android.app.model.account.validatemobileemail.ValidateMobileEmailResponse;
import com.zemulla.android.app.widgets.OTPDialog;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Response;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ChangeEmailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.submitButton)
    Button submitButton;
    Unbinder unbinder;
    @BindView(R.id.oldEmailEditText)
    EditText oldEmailEditText;
    @BindView(R.id.newEmailEditText)
    EditText newEmailEditText;
    @BindView(R.id.currentPasswordEditText)
    EditText currentPasswordEditText;
    private OTPDialog otpDialog;
    private OTPGenValTemporaryAPI otpGenValTemporaryAPI;
    private OTPGenValRequest otpGenValTemporaryRequest;
    private OTPGenValResponse otpGenValTemporaryResponse;
    private ChangeEmailRequest changeEmailRequest;
    private ChangeEmailAPI changeEmailAPI;
    private ChangeEmailResponse changeEmailResponse;
    private ProgressDialog progressDialog;
    private Subscription emailSubscription;
    private ValidateMobileEmailAPI validateMobileEmailAPI;
    private ValidateMobileEmailRequest validateMobileEmailRequest;
    private boolean isEmailValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        init();

    }

    private void init() {
        initToolbar();
        initProgressDialog();
        changeEmailAPI = new ChangeEmailAPI();
        changeEmailRequest = new ChangeEmailRequest();
        otpGenValTemporaryAPI = new OTPGenValTemporaryAPI();
        otpGenValTemporaryRequest = new OTPGenValRequest();
        validateMobileEmailAPI = new ValidateMobileEmailAPI();
        validateMobileEmailRequest = new ValidateMobileEmailRequest();

        emailSubscription = RxTextView.afterTextChangeEvents(newEmailEditText)
                .skip(1)
                .debounce(AppConstant.DebounceTime, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {

                        String emailId = textViewAfterTextChangeEvent.view().getText().toString();
                        checkValidEmailId(emailId);

                        Log.d("edtEmail", textViewAfterTextChangeEvent.view().getText().toString());
                    }
                });

    }

    private void checkValidEmailId(String emailId) {

        if (Functions.emailValidation(emailId)) {
            //check for email web service
            validateMobileEmailRequest.setEmail(emailId);
            validateMobileEmailRequest.setMobile("");
            validateMobileEmailRequest.setCallingCode("");

            showProgressDialog();
            validateMobileEmailAPI.validateMobileEmail(validateMobileEmailRequest, validateEmailResponseAPIListener);
        }

    }

    APIListener<ValidateMobileEmailResponse> validateEmailResponseAPIListener = new APIListener<ValidateMobileEmailResponse>() {
        @Override
        public void onResponse(Response<ValidateMobileEmailResponse> response) {
            hidProgressDialog();
            try {
                if (response.isSuccessful()) {
                    if (!response.body().isResult()) {
                        isEmailValid = false;
                        Functions.showError(ChangeEmailActivity.this, response.body().getResponse().getResponseMsg(), false);
                    } else {
                        isEmailValid = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ValidateMobileEmailResponse> call, Throwable t) {
            hidProgressDialog();
        }
    };

    private void initToolbar() {
        if (toolbar != null) {
            toolbar.setTitle("Change Email");

        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.submitButton)
    public void onClick() {


        if (Functions.isEmpty(oldEmailEditText)) {
            Functions.showError(this, "Please Enter Old Email.", false);
            return;
        }
        if (!Functions.emailValidation(Functions.toStingEditText(oldEmailEditText))) {
            Functions.showError(this, "Invalid Old e-mail.", false);
            return;
        }

        if (Functions.isEmpty(newEmailEditText)) {
            Functions.showError(this, "Please Enter New Email.", false);
            return;
        }
        if (!Functions.emailValidation(Functions.toStingEditText(oldEmailEditText))) {
            Functions.showError(this, "Invalid New e-mail.", false);
            return;
        }
        if (Functions.isEmpty(currentPasswordEditText)) {
            Functions.showError(this, "Please Enter Your Current password.", false);
            return;
        }
        if (!isEmailValid) {
            Functions.showError(this, "Email Already Exist.", false);
            return;
        }
        changeEmail();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        changeEmailAPI.onDestory();
        if (changeEmailResponseAPIListener != null) {
            changeEmailResponseAPIListener = null;
        }
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

    private void changeEmail() {

        changeEmailRequest.setNewEmail(Functions.toStingEditText(newEmailEditText));
        changeEmailRequest.setOldEmail(Functions.toStingEditText(oldEmailEditText));
        changeEmailRequest.setPassword(Functions.toStingEditText(currentPasswordEditText));
        changeEmailAPI.changeEmail(changeEmailRequest, changeEmailResponseAPIListener);


    }

    APIListener<ChangeEmailResponse> changeEmailResponseAPIListener = new APIListener<ChangeEmailResponse>() {
        @Override
        public void onResponse(Response<ChangeEmailResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                changeEmailResponse = response.body();
                if (changeEmailResponse.getResponseCode() == AppConstant.ResponseSuccess) {
                    GenerateOTP();
                }
                Toast.makeText(ChangeEmailActivity.this, changeEmailResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(Call<ChangeEmailResponse> call, Throwable t) {

        }
    };

    private void GenerateOTP() {
        try {

            otpGenValTemporaryRequest.setMobile("");
            otpGenValTemporaryRequest.setCallingCode("");
            otpGenValTemporaryRequest.setEmail(Functions.toStingEditText(newEmailEditText));
            otpGenValTemporaryRequest.setEmailTempleteID(0);
            otpGenValTemporaryRequest.setUniqueID("");
            otpGenValTemporaryRequest.setVerificationCode("");
            showProgressDialog();
            otpGenValTemporaryAPI.otpGenValTemporary(otpGenValTemporaryRequest, otpGenValTemporaryResponseAPIListener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showOTPDialog() {

        if (otpDialog == null) {
            initOTPDiaLog();
        }

        otpDialog.show();
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

                Functions.fireIntent(ChangeEmailActivity.this, ChangeEmailActivity.class);

            }
        });


        otpDialog.setDisplayText(true, Functions.toStingEditText(newEmailEditText), "");
        //otpDialog.showChangeEmail();

    }

    private void submitOTP(String OTP) {
        try {
            if (otpGenValTemporaryResponse != null) {
                otpGenValTemporaryRequest.setMobile("");
                otpGenValTemporaryRequest.setCallingCode("");
                otpGenValTemporaryRequest.setEmail(Functions.toStingEditText(newEmailEditText));
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

    APIListener<OTPGenValResponse> otpGenValTemporaryResponseAPIListener = new APIListener<OTPGenValResponse>() {
        @Override
        public void onResponse(Response<OTPGenValResponse> response) {
            hidProgressDialog();
            try {
                if (response.isSuccessful()) {
                    showOTPDialog();
                    otpGenValTemporaryResponse = response.body();
                    if (otpGenValTemporaryResponse != null && otpGenValTemporaryResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {

                        Toast.makeText(ChangeEmailActivity.this, otpGenValTemporaryResponse.getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChangeEmailActivity.this, otpGenValTemporaryResponse.getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
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

    APIListener<OTPGenValResponse> submitotpGenValTemporaryResponseAPIListener = new APIListener<OTPGenValResponse>() {
        @Override
        public void onResponse(Response<OTPGenValResponse> response) {
            hidProgressDialog();
            try {
                if (response.isSuccessful()) {

                    OTPGenValResponse otpGenValTemporaryResponse = response.body();
                    if (otpGenValTemporaryResponse != null && otpGenValTemporaryResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        Toast.makeText(ChangeEmailActivity.this, otpGenValTemporaryResponse.getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
                        otpDialog.disMissDiaLog();
                        doLogin();
                    } else {
                        Toast.makeText(ChangeEmailActivity.this, otpGenValTemporaryResponse.getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
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

    private void doLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }
}
