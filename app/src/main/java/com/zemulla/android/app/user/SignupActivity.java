package com.zemulla.android.app.user;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;
import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.account.OTPGenValTemporaryAPI;
import com.zemulla.android.app.api.account.SignUpAPI;
import com.zemulla.android.app.api.account.ValidateMobileEmailAPI;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PasswordTracker;
import com.zemulla.android.app.home.LogUtils;
import com.zemulla.android.app.model.account.country.Country;
import com.zemulla.android.app.model.account.otpgenvaltemporary.OTPGenValRequest;
import com.zemulla.android.app.model.account.otpgenvaltemporary.OTPGenValResponse;
import com.zemulla.android.app.model.account.registration.RegistrationRequest;
import com.zemulla.android.app.model.account.registration.RegistrationResponse;
import com.zemulla.android.app.model.account.validatemobileemail.ValidateMobileEmailRequest;
import com.zemulla.android.app.model.account.validatemobileemail.ValidateMobileEmailResponse;
import com.zemulla.android.app.widgets.OTPDialog;
import com.zemulla.android.app.widgets.TfButton;
import com.zemulla.android.app.widgets.TfEditText;
import com.zemulla.android.app.widgets.countrypicker.CountryPickerView;

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


public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.edtFirstName)
    TfEditText edtFirstName;
    @BindView(R.id.edtLastName)
    TfEditText edtLastName;
    @BindView(R.id.edtEmail)
    TfEditText edtEmail;
    @BindView(R.id.edtState)
    TfEditText edtState;
    @BindView(R.id.edtCity)
    TfEditText edtCity;
    @BindView(R.id.edtPassword)
    TfEditText edtPassword;
    @BindView(R.id.edtConfirmPassword)
    TfEditText edtConfirmPassword;
    @BindView(R.id.edtAddress)
    TfEditText edtAddress;
    @BindView(R.id.edtZip)
    TfEditText edtZip;
    @BindView(R.id.btnSignUp)
    TfButton btnSignUp;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.countryPicker)
    CountryPickerView countryPicker;
    @BindView(R.id.backToLogin)
    Button backToLogin;
    Country mSelectedCountry = null;
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.termsConditionCheckBox)
    CheckBox termsConditionCheckBox;
    @BindView(R.id.termsConditionTextView)
    TextView termsConditionTextView;
    @BindView(R.id.txtPasswordType)
    TextView txtPasswordType;
    private Subscription passowordSubscription, phoneNumberSubscription;
    private Unbinder unbinder;
    private ProgressDialog progressDialog;
    private ValidateMobileEmailAPI validateMobileEmailAPI;
    private ValidateMobileEmailRequest validateMobileEmailRequest;
    private OTPGenValTemporaryAPI otpGenValTemporaryAPI;
    private OTPGenValRequest otpGenValTemporaryRequest;
    private OTPGenValResponse otpGenValTemporaryResponse;
    private OTPDialog otpDialog;
    private RegistrationRequest registrationRequest;
    private SignUpAPI signUpAPI;
    private boolean isEmailValid, isMobileValid;
    private PasswordTracker tracker;
    private int passwordType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        unbinder = ButterKnife.bind(this);

        init();

    }


    private void init() {
        initToolBar();
        initApplyFont();
        initProgressDialog();

        validateMobileEmailAPI = new ValidateMobileEmailAPI();
        validateMobileEmailRequest = new ValidateMobileEmailRequest();
        otpGenValTemporaryAPI = new OTPGenValTemporaryAPI();
        otpGenValTemporaryRequest = new OTPGenValRequest();
        registrationRequest = new RegistrationRequest();
        signUpAPI = new SignUpAPI();


        loadCountry();

        setTermAndConditionText();

        verifyEmailAndPhoneNumber();

        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Functions.getLength(edtPassword) == 0) {
                    txtPasswordType.setVisibility(View.GONE);

                } else {
                    txtPasswordType.setVisibility(View.VISIBLE);
                    tracker = Functions.getPasswordStr(Functions.toStingEditText(edtPassword));
                    txtPasswordType.setText(tracker.getText());
                    txtPasswordType.setTextColor(tracker.getColor());
                    passwordType = tracker.getPasswordType();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.signup);
        setSupportActionBar(toolbar);
    }

    private void setTermAndConditionText() {
        termsConditionTextView.setText(TextUtils.concat("I agree to all "
                , Functions.getTermsAndConditionAndPrivacyAndContecPolicy("Terms & Conditions", "", this, termsConditionTextView)
                , " , "
                , Functions.getTermsAndConditionAndPrivacyAndContecPolicy("Privacy Policy", "", this, termsConditionTextView)
        ));
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

            }
        });

        otpDialog.setDisplayText(false, countryPicker.getPhoneNumber(), "");
    }

    private void submitOTP(String OTP) {
        if (otpGenValTemporaryResponse != null) {
            otpGenValTemporaryRequest.setMobile(otpGenValTemporaryResponse.getHiddenMobile());
            otpGenValTemporaryRequest.setCallingCode(mSelectedCountry.getCallingCode());
            otpGenValTemporaryRequest.setEmail(Functions.toStingEditText(edtEmail));
            otpGenValTemporaryRequest.setEmailTempleteID(0);
            otpGenValTemporaryRequest.setVerificationCode(OTP);
            otpGenValTemporaryRequest.setUniqueID(otpGenValTemporaryResponse.getUniqueID());
            showProgressDialog();
            otpGenValTemporaryAPI.otpGenValTemporary(otpGenValTemporaryRequest, submitotpGenValTemporaryResponseAPIListener);
        }
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
    }

    private void initApplyFont() {

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

    private void loadCountry() {
        countryPicker.fetchCountry();
        countryPicker.setCountryPickerListener(new CountryPickerView.CountryPickerListener() {
            @Override
            public void OnFailed(Throwable t) {
                Toast.makeText(SignupActivity.this, "Failed to load country.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnSelected(Country country) {

                mSelectedCountry = country;
                LogUtils.LOGD("Selected Country", country.getCountryName());

            }
        });
    }

    private void showOTPDialog() {

        if (otpDialog == null) {
            initOTPDiaLog();

        }

        otpDialog.show();
    }

    private void hideOTPDialog() {
        otpDialog.disMissDiaLog();
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

    private void checkValidPhoneNumber(String phoneNumber) {
        if (Functions.isPhoneNumberValid(phoneNumber)) {

            //check for valid phone number
            validateMobileEmailRequest.setCallingCode(mSelectedCountry.getCallingCode());
            validateMobileEmailRequest.setMobile(phoneNumber);
            validateMobileEmailRequest.setEmail("");
            showProgressDialog();
            validateMobileEmailAPI.validateMobileEmail(validateMobileEmailRequest, validateMobileResponseAPIListener);
        }

    }

    private void verifyEmailAndPhoneNumber() {
        passowordSubscription = RxTextView.afterTextChangeEvents(edtEmail)
                .skip(1)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {

                        String emailId = textViewAfterTextChangeEvent.view().getText().toString();
                        checkValidEmailId(emailId);

                        Log.d("edtEmail", textViewAfterTextChangeEvent.view().getText().toString());
                    }
                });

        phoneNumberSubscription = RxTextView.afterTextChangeEvents(countryPicker.getEditText())
                .skip(1)
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                        String phoneNumber = textViewAfterTextChangeEvent.view().getText().toString();
                        checkValidPhoneNumber(phoneNumber);
                        Log.d("password", textViewAfterTextChangeEvent.view().getText().toString());
                    }
                });
    }


    private void GenerateOTP() {
        otpGenValTemporaryRequest.setMobile(countryPicker.getPhoneNumber());
        otpGenValTemporaryRequest.setCallingCode(mSelectedCountry.getCallingCode());
        otpGenValTemporaryRequest.setEmail(Functions.toStingEditText(edtEmail));
        otpGenValTemporaryRequest.setEmailTempleteID(0);
        otpGenValTemporaryRequest.setVerificationCode("");
        otpGenValTemporaryRequest.setUniqueID("");

        showProgressDialog();
        otpGenValTemporaryAPI.otpGenValTemporary(otpGenValTemporaryRequest, otpGenValTemporaryResponseAPIListener);
    }

    private void doSignUp() {


        registrationRequest.setAddress(Functions.toStingEditText(edtAddress));
        registrationRequest.setCallingCode(mSelectedCountry.getCallingCode());
        registrationRequest.setCity(Functions.toStingEditText(edtCity));
        registrationRequest.setCountryID(mSelectedCountry.getCountryID());
        registrationRequest.setEmail(Functions.toStingEditText(edtEmail));
        registrationRequest.setFirstName(Functions.toStingEditText(edtFirstName));
        registrationRequest.setLastName(Functions.toStingEditText(edtLastName));
        registrationRequest.setMobile(countryPicker.getPhoneNumber());
        registrationRequest.setPassword(Functions.toStingEditText(edtPassword));
        registrationRequest.setRoleID(AppConstant.ClientRoleID);
        registrationRequest.setState(Functions.toStingEditText(edtState));
        registrationRequest.setZipCode(Functions.toStingEditText(edtZip));
        showProgressDialog();
        signUpAPI.signUp(registrationRequest, registrationResponseAPIListener);


    }

    APIListener<ValidateMobileEmailResponse> validateEmailResponseAPIListener = new APIListener<ValidateMobileEmailResponse>() {
        @Override
        public void onResponse(Response<ValidateMobileEmailResponse> response) {
            hidProgressDialog();
            try {
                if (response.isSuccessful()) {
                    if (!response.body().isResult()) {
                        isEmailValid = false;
                        Functions.showError(SignupActivity.this, response.body().getResponse().getResponseMsg(), false);
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
    APIListener<ValidateMobileEmailResponse> validateMobileResponseAPIListener = new APIListener<ValidateMobileEmailResponse>() {
        @Override
        public void onResponse(Response<ValidateMobileEmailResponse> response) {
            hidProgressDialog();
            try {
                if (response.isSuccessful()) {
                    if (!response.body().isResult()) {
                        isMobileValid = false;
                        Functions.showError(SignupActivity.this, response.body().getResponse().getResponseMsg(), false);
                    } else {
                        isMobileValid = true;
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
    APIListener<OTPGenValResponse> otpGenValTemporaryResponseAPIListener = new APIListener<OTPGenValResponse>() {
        @Override
        public void onResponse(Response<OTPGenValResponse> response) {
            hidProgressDialog();
            try {
                if (response.isSuccessful()) {
                    showOTPDialog();
                    otpGenValTemporaryResponse = response.body();
                    if (otpGenValTemporaryResponse != null && otpGenValTemporaryResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {

                        Toast.makeText(SignupActivity.this, otpGenValTemporaryResponse.getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignupActivity.this, otpGenValTemporaryResponse.getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
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

                    otpGenValTemporaryResponse = response.body();
                    if (otpGenValTemporaryResponse != null && otpGenValTemporaryResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        Toast.makeText(SignupActivity.this, otpGenValTemporaryResponse.getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
                        otpDialog.disMissDiaLog();
                        doSignUp();
                    } else {
                        Toast.makeText(SignupActivity.this, otpGenValTemporaryResponse.getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
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
    APIListener<RegistrationResponse> registrationResponseAPIListener = new APIListener<RegistrationResponse>() {
        @Override
        public void onResponse(Response<RegistrationResponse> response) {
            hidProgressDialog();
            try {

                if (response.isSuccessful()) {
                    RegistrationResponse registrationResponse = response.body();
                    if (registrationResponse != null) {

                        if (registrationResponse != null && registrationResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                            Toast.makeText(SignupActivity.this, registrationResponse.getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
                            finish();
                            Functions.fireIntent(SignupActivity.this, LoginActivity.class);

                        } else {
                            Functions.showError(SignupActivity.this, registrationResponse.getResponse().getResponseMsg(), false);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<RegistrationResponse> call, Throwable t) {
            hidProgressDialog();
        }
    };

    @OnClick(R.id.backToLogin)
    public void onClick() {
        finish();
    }

    @OnClick(R.id.btnSignUp)
    public void btnSignUpClick() {

        if (Functions.isEmpty(edtFirstName)) {
            Functions.showError(this, "Please Enter First Name.", false);
            return;
        }

        if (Functions.isEmpty(edtLastName)) {
            Functions.showError(this, "Please Enter Last Name.", false);
            return;
        }

        if (Functions.isEmpty(edtEmail)) {
            Functions.showError(this, "Please Enter Email.", false);
            return;
        }

        if (!Functions.emailValidation(Functions.toStingEditText(edtEmail))) {
            Functions.showError(this, "Invalid e-mail.", false);
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

        if (Functions.isEmpty(edtState)) {
            Functions.showError(this, "Please Enter State.", false);
            return;
        }

        if (Functions.isEmpty(edtCity)) {
            Functions.showError(this, "Please Enter City.", false);
            return;
        }


        if (Functions.isEmpty(edtPassword)) {
            Functions.showError(this, "Please Enter Password.", false);
            return;
        }

        if (passwordType == AppConstant.WEAK) {
            Functions.showError(this, "Your password is too weak.", false);
            return;
        }

        if (Functions.isEmpty(edtConfirmPassword)) {
            Functions.showError(this, "Please Re-Enter Password.", false);
            return;
        }

        if (!Functions.toStingEditText(edtPassword).equals(Functions.toStingEditText(edtConfirmPassword))) {
            Functions.showError(this, "Entered Password did not match.", false);
            return;
        }
        if (Functions.isEmpty(edtAddress)) {
            Functions.showError(this, "Please Enter Address.", false);
            return;
        }
        if (Functions.isEmpty(edtZip)) {
            Functions.showError(this, "Please Enter ZipCode.", false);
            return;
        }

        if (mSelectedCountry == null) {
            Functions.showError(this, "Please Select Country.", false);
            return;
        }

        if (Functions.isEmpty(countryPicker.getEditText())) {
            Functions.showError(this, "Please Enter Mobile Number.", false);
            return;
        }

        if (!termsConditionCheckBox.isChecked()) {
            Functions.showError(this, "Please Accept Terms & Conditions.", false);
            return;
        }


        if (!isEmailValid) {

            Functions.showError(this, "Email Already Exist", false);
            return;
        }
        if (!isMobileValid) {
            Functions.showError(this, "Mobile Number Already Exist", false);
            return;
        }

        GenerateOTP();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unbinder.unbind();
            unSubscribe(passowordSubscription);
            unSubscribe(phoneNumberSubscription);
            validateMobileEmailAPI.onDestory();
            otpGenValTemporaryAPI.onDestory();
            signUpAPI.onDestory();
            removeListener(registrationResponseAPIListener);
            removeListener(submitotpGenValTemporaryResponseAPIListener);
            removeListener(otpGenValTemporaryResponseAPIListener);
            removeListener(validateMobileResponseAPIListener);
            removeListener(validateEmailResponseAPIListener);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void unSubscribe(Subscription subscription) {

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void removeListener(APIListener<?> listener) {

        if (listener != null) {
            listener = null;
        }

    }
}
