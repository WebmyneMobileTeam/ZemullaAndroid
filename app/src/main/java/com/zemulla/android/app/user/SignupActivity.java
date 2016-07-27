package com.zemulla.android.app.user;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zemulla.android.app.CountryPickerWidget.IntlPhoneInput;
import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.AdvancedSpannableString;
import com.zemulla.android.app.helper.Functions;


public class SignupActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        init();

    }


    private void init() {

        initApplyFont();
    }

    private void initApplyFont() {

    }


}
