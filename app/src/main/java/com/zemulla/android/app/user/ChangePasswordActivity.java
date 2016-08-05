package com.zemulla.android.app.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.oldPasswordEditText)
    EditText oldPasswordEditText;
    @BindView(R.id.newPasswordEditText)
    EditText newPasswordEditText;
    @BindView(R.id.confirmPasswordEditText)
    EditText confirmPasswordEditText;
    @BindView(R.id.submitButton)
    Button submitButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        unbinder = ButterKnife.bind(this);

        init();
    }

    private void init() {
        initToolbar();
    }

    private void initToolbar() {
        if (toolbar != null) {
            toolbar.setTitle("Dhruvil Patel");
            toolbar.setSubtitle("Effective Balance : ZMW 1222.5");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.submitButton)
    public void onClick() {


        if (Functions.isEmpty(oldPasswordEditText)) {
            Functions.showError(this, "Please Enter Old password.", false);
            return;
        }

        if (Functions.isEmpty(newPasswordEditText)) {
            Functions.showError(this, "Please Enter New password.", false);
            return;
        }

        if (Functions.isEmpty(confirmPasswordEditText)) {
            Functions.showError(this, "Please Enter Confirm password.", false);
            return;
        }

        if (!Functions.toStingEditText(newPasswordEditText).equals(Functions.toStingEditText(confirmPasswordEditText))) {
            Functions.showError(this, "Please Enter password does not match.", false);
        }


        // call change password webservice

    }
}
