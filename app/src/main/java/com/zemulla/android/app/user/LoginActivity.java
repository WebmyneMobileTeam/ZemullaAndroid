package com.zemulla.android.app.user;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zemulla.android.app.CountryPickerWidget.IntlPhoneInput;
import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.AdvancedSpannableString;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.home.HomeActivity;


public class LoginActivity extends AppCompatActivity {
    private ImageView imgLogo;
    private TextView txtLogin,txtBottom,txtForgotPass;
    private EditText etPassword;
    private IntlPhoneInput etMobile;
    private RelativeLayout mainRelative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }




    private void init() {
        mainRelative = (RelativeLayout)findViewById(R.id.mainRelative);
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

        span.setClickableSpanTo("Signup");

        span.setSpanClickListener(new AdvancedSpannableString.OnClickableSpanListner() {
            @Override
            public void onSpanClick() {
                Intent iHomeActivity = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(iHomeActivity);
            }
        });
        txtBottom.setText(span);
        txtBottom.setMovementMethod(LinkMovementMethod.getInstance());
        initApplyFont();
    }

    private void initApplyFont() {
        etMobile.addFont();
        etPassword.setTypeface(Functions.getRegularTypeFace(LoginActivity.this));
        txtBottom.setTypeface(Functions.getRegularTypeFace(LoginActivity.this));
        txtForgotPass.setTypeface(Functions.getRegularTypeFace(LoginActivity.this), Typeface.BOLD);
    }

    public void loginClick(View view) {

        Intent iHomeActivity = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(iHomeActivity);
        finish();

    }
}
