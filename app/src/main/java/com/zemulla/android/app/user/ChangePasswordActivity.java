package com.zemulla.android.app.user;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PasswordTracker;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.model.base.Response;
import com.zemulla.android.app.user.api.ChangePasswordApi;
import com.zemulla.android.app.user.model.ChangePasswordRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

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
    @BindView(R.id.txtPasswordType)
    TextView txtPasswordType;
    private APIListener<Response> apiListener;
    private ProgressDialog progressDialog;
    private ChangePasswordRequest request;
    private ChangePasswordApi passwordApi;
    private int passwordType = 0;
    private PasswordTracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        unbinder = ButterKnife.bind(this);
        request = new ChangePasswordRequest();
        passwordApi = new ChangePasswordApi();

        init();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
    }

    private void init() {
        initProgressDialog();

        initToolbar();

        actionListsner();
    }

    private void actionListsner() {
        apiListener = new APIListener<Response>() {
            @Override
            public void onResponse(retrofit2.Response<Response> response) {
                hideProgressDialog();
                try {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getResponseCode() == AppConstant.ResponseSuccess) {
                                Functions.showError(ChangePasswordActivity.this, "Your Password Change Successfully.", true);
                            } else {
                                Functions.showError(ChangePasswordActivity.this, response.body().getResponseMsg(), false);
                            }
                        } else {
                            Functions.showError(ChangePasswordActivity.this, "Something went wrong. Please try again.", false);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        };

        newPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Functions.getLength(newPasswordEditText) == 0) {
                    txtPasswordType.setVisibility(View.GONE);

                } else {
                    txtPasswordType.setVisibility(View.VISIBLE);
                    tracker = Functions.getPasswordStr(Functions.toStingEditText(newPasswordEditText));
                    txtPasswordType.setText(tracker.getText());
                    txtPasswordType.setTextColor(tracker.getColor());
                    passwordType = tracker.getPasswordType();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    private void showProgressDialog() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
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
            return;
        }

        if (passwordType == AppConstant.WEAK) {
            Functions.showError(this, "Your password is too weak.", false);
            return;
        }

        doChangePassword();
        // call change password webservice

    }

    private void doChangePassword() {
        showProgressDialog();

        request.setOldPassword(Functions.toStingEditText(oldPasswordEditText));
        request.setNewPassword(Functions.toStingEditText(newPasswordEditText));
        request.setUserID(PrefUtils.getUserID(this));

        passwordApi.changePassword(request, apiListener);
    }
}
