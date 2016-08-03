package com.zemulla.android.app.user;

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
import com.zemulla.android.app.helper.AdvancedSpannableString;
import com.zemulla.android.app.home.HomeActivity;
import com.zemulla.android.app.home.LogUtils;
import com.zemulla.android.app.model.country.Country;
import com.zemulla.android.app.widgets.countrypicker.CountryPickerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.imgLogo)
    AppCompatImageView imgLogo;
    @BindView(R.id.countryPicker)
    CountryPickerView countryPicker;

    @BindView(R.id.headerView)
    TextView headerView;
    @BindView(R.id.edtFirstName)
    EditText edtFirstName;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.txtForgotPass)
    Button txtForgotPass;
    @BindView(R.id.txtBottom)
    TextView txtBottom;


    Country mSelectedCountry = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        init();

    }

    private void init() {

        setSignUpText();

        countryPicker.fetchCountry();
        countryPicker.setCountryPickerListener(new CountryPickerView.CountryPickerListener() {
            @Override
            public void OnFailed(Throwable t) {
                Toast.makeText(LoginActivity.this, "Failed to load country.", Toast.LENGTH_LONG).show();
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
        Intent txtForgotPassActivity = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(txtForgotPassActivity);

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
}
