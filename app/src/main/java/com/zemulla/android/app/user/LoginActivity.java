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
import android.widget.TextView;
import android.widget.Toast;

import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.account.LoginAPI;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.AdvancedSpannableString;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.home.HomeActivity;
import com.zemulla.android.app.home.LogUtils;
import com.zemulla.android.app.model.country.Country;
import com.zemulla.android.app.model.login.LoginRequest;
import com.zemulla.android.app.model.login.LoginResponse;
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

    @BindView(R.id.headerView)
    TextView headerView;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.txtForgotPass)
    Button txtForgotPass;
    @BindView(R.id.txtBottom)
    TextView txtBottom;
    Country mSelectedCountry = null;
    Unbinder unbinder;
    ProgressDialog progressDialog;
    LoginAPI loginAPI;
    LoginRequest loginRequest;

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
        setSignUpText();
        initProgressDialog();
        showProgressDialog();

        countryPicker.fetchCountry();
        countryPicker.setCountryPickerListener(new CountryPickerView.CountryPickerListener() {
            @Override
            public void OnFailed(Throwable t) {
                Toast.makeText(LoginActivity.this, "Failed to load country.", Toast.LENGTH_LONG).show();
                hidProgressDialog();
            }

            @Override
            public void OnSelected(Country country) {
                mSelectedCountry = country;
                hidProgressDialog();
                LogUtils.LOGD("Selected Country", country.getCountryName());

            }
        });

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

        Intent txtForgotPassActivity = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(txtForgotPassActivity);
        finish();
        //doLogin();


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
                        LoginResponse loginResponse = response.body();
                        if (loginResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                            Intent txtForgotPassActivity = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(txtForgotPassActivity);
                        } else {
                            Functions.showError(LoginActivity.this, loginResponse.getResponse().getResponseMsg(), false);
                        }

                    }
                } finally {

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                hidProgressDialog();
            }
        });
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
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
