package com.zemulla.android.app.user;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.account.ForgotPasswordAPI;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.home.LogUtils;
import com.zemulla.android.app.model.country.Country;
import com.zemulla.android.app.model.forgotpassword.ForgotPasswordRequest;
import com.zemulla.android.app.model.forgotpassword.ForgotPasswordResponse;
import com.zemulla.android.app.widgets.countrypicker.CountryPickerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.forgot_password);
        setSupportActionBar(toolbar);
        initApplyFont();
        forgotPasswordAPI = new ForgotPasswordAPI();
        forgotPasswordRequest = new ForgotPasswordRequest();
        Functions.initProgressDialog(this, progressDialog);
        countryPicker.fetchCountry();
        countryPicker.setCountryPickerListener(new CountryPickerView.CountryPickerListener() {
            @Override
            public void OnFailed(Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this, "Failed to load country.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnSelected(Country country) {

                mSelectedCountry = country;
                LogUtils.LOGD("Selected Country", country.getCountryName());

            }
        });
    }

    private void initApplyFont() {

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
        }
        showProgressDialog();
        forgotPasswordAPI.forgotPassword(forgotPasswordRequest, new APIListener<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Response<ForgotPasswordResponse> response) {

                hidProgressDialog();
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                hidProgressDialog();
            }
        });


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
}
