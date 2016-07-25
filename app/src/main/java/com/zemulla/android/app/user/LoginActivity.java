package com.zemulla.android.app.user;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zemulla.android.app.CountryPickerWidget.IntlPhoneInput;
import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.AdvancedSpannableString;
import com.zemulla.android.app.helper.Functions;


public class LoginActivity extends AppCompatActivity {
    private ImageView imgLogo;
    private TextView txtLogin,txtBottom,txtForgotPass;
    private EditText etPassword;
    private IntlPhoneInput etMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        init();
    }



    private void init() {
        etMobile = (IntlPhoneInput)findViewById(R.id.etMobile);
        etMobile.setDefault();

        etMobile.setNumber(etMobile.getNumber()+"");

        etPassword= (EditText)findViewById(R.id.etPassword);
        txtLogin = (TextView)findViewById(R.id.txtLogin);
        imgLogo = (ImageView)findViewById(R.id.imgLogo);
        txtBottom= (TextView)findViewById(R.id.txtBottom);
        txtForgotPass= (TextView)findViewById(R.id.txtForgotPass);
        txtLogin.setTypeface(Functions.getRegularTypeFace(LoginActivity.this), Typeface.BOLD);

        AdvancedSpannableString span = new AdvancedSpannableString(getResources().getString(R.string.signup_msg));
        span.setBold("Signup");
        span.setUnderLine("Signup");
        span.setColor(ContextCompat.getColor(this,R.color.yellow),"Signup");
        span.setColor(ContextCompat.getColor(this,R.color.colorDark),"Don't have an account?");
/*
        span.setClickableSpanTo("Signup");
        span.setSpanClickListener(new AdvancedSpannableString.OnClickableSpanListner() {
            @Override
            public void onSpanClick() {
                Toast.makeText(LoginActivity.this, "Krishna", Toast.LENGTH_SHORT).show();
            }
        });*/
        txtBottom.setText(span);

        initApplyFont();
    }

    private void initApplyFont() {
        etMobile.addFont();
        etPassword.setTypeface(Functions.getRegularTypeFace(LoginActivity.this));
        txtBottom.setTypeface(Functions.getRegularTypeFace(LoginActivity.this));
        txtForgotPass.setTypeface(Functions.getRegularTypeFace(LoginActivity.this), Typeface.BOLD);
    }
}
