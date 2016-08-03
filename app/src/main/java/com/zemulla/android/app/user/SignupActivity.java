package com.zemulla.android.app.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Toast;

import com.zemulla.android.app.R;
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
    }

    private void initApplyFont() {

    }


    @OnClick(R.id.backToLogin)
    public void onClick() {
        finish();
    }
}
