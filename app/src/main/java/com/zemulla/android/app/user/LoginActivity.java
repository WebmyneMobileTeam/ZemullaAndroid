package com.zemulla.android.app.user;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zemulla.android.app.CountryPickerWidget.IntlPhoneInput;
import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.AdvancedSpannableString;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.home.HomeActivity;
import com.zemulla.android.app.widgets.TfButton;
import com.zemulla.android.app.widgets.TfEditText;
import com.zemulla.android.app.widgets.TfTextView;


public class LoginActivity extends AppCompatActivity {
    private ImageView imgLogo;
    private TfTextView txtBottom, txtForgotPass;
    private TfEditText edtPassword;
    private TfButton btnLogin;
    private IntlPhoneInput edtMobile;
    private RelativeLayout mainRelative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    private void init() {
        mainRelative = (RelativeLayout) findViewById(R.id.mainRelative);
        edtMobile = (IntlPhoneInput) findViewById(R.id.edtMobile);
        edtMobile.setDefault();

        edtMobile.setNumber(edtMobile.getNumber() + "");

        edtPassword = (TfEditText) findViewById(R.id.edtPassword);
        btnLogin = (TfButton) findViewById(R.id.btnLogin);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        txtBottom = (TfTextView) findViewById(R.id.txtBottom);
        txtForgotPass = (TfTextView) findViewById(R.id.txtForgotPass);
        btnLogin.setTypeface(Functions.getRegularTypeFace(LoginActivity.this), Typeface.BOLD);

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
        initApplyFont();
    }

    private void initApplyFont() {
        edtMobile.addFont();
        edtPassword.setTypeface(Functions.getRegularTypeFace(LoginActivity.this));
        txtBottom.setTypeface(Functions.getRegularTypeFace(LoginActivity.this));
        txtForgotPass.setTypeface(Functions.getRegularTypeFace(LoginActivity.this), Typeface.BOLD);
    }

    public void loginClick(View view) {
        Intent iHomeActivity = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(iHomeActivity);
        finish();

    }
}
