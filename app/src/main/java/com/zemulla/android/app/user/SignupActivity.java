package com.zemulla.android.app.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zemulla.android.app.CountryPickerWidget.IntlPhoneInput;
import com.zemulla.android.app.R;
import com.zemulla.android.app.widgets.TfButton;
import com.zemulla.android.app.widgets.TfEditText;

import butterknife.BindView;
import butterknife.ButterKnife;


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
    @BindView(R.id.etMobile)
    IntlPhoneInput etMobile;
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
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initApplyFont();
    }

    private void initApplyFont() {

    }


}
