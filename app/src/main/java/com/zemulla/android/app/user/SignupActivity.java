package com.zemulla.android.app.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.home.LogUtils;
import com.zemulla.android.app.model.country.Country;
import com.zemulla.android.app.widgets.TfButton;
import com.zemulla.android.app.widgets.TfEditText;
import com.zemulla.android.app.widgets.countrypicker.CountryPickerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        ButterKnife.bind(this);

        init();

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.signup);
        setSupportActionBar(toolbar);
        initApplyFont();

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


        //I agree to all Terms &amp; Conditions, Privacy Policy
        termsConditionTextView.setText(TextUtils.concat("I agree to all "
                , Functions.getTermsAndConditionAndPrivacyAndContecPolicy("Terms & Conditions", "", this, termsConditionTextView)
                , " , "
                , Functions.getTermsAndConditionAndPrivacyAndContecPolicy("Privacy Policy", "", this, termsConditionTextView)
        ));
    }

    private void initApplyFont() {

    }


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

        if (Functions.isEmpty(edtConfirmPassword)) {
            Functions.showError(this, "Please Re-Enter Password.", false);
            return;
        }


        if (!Functions.toStingEditText(edtPassword).equals(Functions.toStingEditText(edtConfirmPassword))) {
            Functions.showError(this, "Entered Password did not match.", false);
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
        }


    }


}
