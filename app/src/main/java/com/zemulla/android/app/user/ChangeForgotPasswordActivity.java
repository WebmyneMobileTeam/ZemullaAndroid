package com.zemulla.android.app.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.account.ResetPasswordAPI;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.model.resetpassword.ResetPasswordRequest;
import com.zemulla.android.app.model.resetpassword.ResetPasswordResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Response;

public class ChangeForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.newPasswordEditText)
    EditText newPasswordEditText;
    @BindView(R.id.confirmPasswordEditText)
    EditText confirmPasswordEditText;
    @BindView(R.id.submitButton)
    Button submitButton;
    private Unbinder unbinder;
    private String token = "";
    private ResetPasswordRequest resetPasswordRequest;
    private ResetPasswordAPI resetPasswordAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_forgot_password);
        unbinder = ButterKnife.bind(this);

        init();
    }

    private void init() {
        initToolbar();
        getDataFromIntent();
        resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordAPI = new ResetPasswordAPI();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        token = intent.getStringExtra(Intent.EXTRA_TEXT);
    }

    private void initToolbar() {
        if (toolbar != null) {
            toolbar.setTitle("Reset Password");
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
        resetPasswordAPI.onDestory();
        Functions.removeListener(resetPasswordResponseAPIListener);
        resetPasswordAPI.onDestory();
    }

    @OnClick(R.id.submitButton)
    public void onClick() {


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

        changePassword();

    }

    private void changePassword() {

        resetPasswordRequest.setPassword(Functions.toStingEditText(confirmPasswordEditText));
        resetPasswordRequest.setToken(token);
        resetPasswordAPI.resetPassword(resetPasswordRequest, resetPasswordResponseAPIListener);
    }


    APIListener<ResetPasswordResponse> resetPasswordResponseAPIListener = new APIListener<ResetPasswordResponse>() {
        @Override
        public void onResponse(Response<ResetPasswordResponse> response) {

            try {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getResponseCode() == AppConstant.ResponseSuccess) {
                        Functions.fireIntent(ChangeForgotPasswordActivity.this, LoginActivity.class);
                        finish();

                    }
                    Toast.makeText(ChangeForgotPasswordActivity.this, response.body().getResponseMsg(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {

        }
    };


}
