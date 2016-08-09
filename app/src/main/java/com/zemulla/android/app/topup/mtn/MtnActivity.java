package com.zemulla.android.app.topup.mtn;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.payment.GetTopupTransactionApi;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.helper.ServiceDetails;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationRequest;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationResponse;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.widgets.OTPDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mbanje.kurt.fabbutton.FabButton;
import retrofit2.Call;
import retrofit2.Response;

public class MtnActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTopupWayName)
    TextView txtTopupWayName;
    @BindView(R.id.btnProcessInitialTransaction)
    FabButton btnProcessInitialTransaction;
    @BindView(R.id.lineatInitialViewTopup)
    LinearLayout lineatInitialViewTopup;
    @BindView(R.id.btnProcessResetTransaction)
    FabButton btnProcessResetTransaction;
    @BindView(R.id.btnProcessConfirmTransaction)
    FabButton btnProcessConfirmTransaction;
    @BindView(R.id.linearTrnsViewTopup)
    LinearLayout linearTrnsViewTopup;
    @BindView(R.id.frameRootTopup)
    FrameLayout frameRootTopup;
    @BindView(R.id.activity_topup_initial_transaction)
    LinearLayout activityTopupInitialTransaction;
    @BindView(R.id.edtAmount)
    EditText edtAmount;
    @BindView(R.id.edtNumber)
    EditText edtNumber;
    @BindView(R.id.edtNationdID)
    EditText edtNationdID;

    private FlipAnimation animation;
    Unbinder unbinder;
    private GetTopupTransactionApi transactionApi;
    private TopUpTransactionChargeCalculationRequest request;
    private TopUpTransactionChargeCalculationResponse topUpResponse;
    private GetWalletDetailResponse walletResponse;
    private LoginResponse loginResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtn);

        unbinder = ButterKnife.bind(this);
        transactionApi = new GetTopupTransactionApi();
        request = new TopUpTransactionChargeCalculationRequest();
        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void init() {
        initToolbar();

        actionListener();

        animation = new FlipAnimation(lineatInitialViewTopup, linearTrnsViewTopup);
    }

    private void actionListener() {


        btnProcessInitialTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // validations in if..else then method

                if (Functions.isEmpty(edtAmount)) {
                    Functions.showError(MtnActivity.this, "Please Enter Amount", false);

                } else if (Double.parseDouble(Functions.toStingEditText(edtAmount)) > walletResponse.getEffectiveBalance()) {
                    Functions.showError(MtnActivity.this, "Enter Valid Amount", false);

                } else {
                    callApi();
                }
            }
        });

        btnProcessResetTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.reverse();
                frameRootTopup.startAnimation(animation);
            }
        });

        btnProcessConfirmTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // validations in if..else then method

                if (Functions.isEmpty(edtNumber)) {
                    Functions.showError(MtnActivity.this, "Please Enter Number", false);
                } else if (Functions.getLength(edtNumber) < 10) {
                    Functions.showError(MtnActivity.this, "Please Enter Valid Number", false);
                } else if (Functions.isEmpty(edtNationdID)) {
                    Functions.showError(MtnActivity.this, "Please Enter National ID", false);
                } else {
                    new OTPDialog(MtnActivity.this, new OTPDialog.onSubmitListener() {
                        @Override
                        public void onSubmit(String OTP) {

                        }

                        @Override
                        public void onResend() {

                        }

                        @Override
                        public void ChangeEmail() {

                        }


                    }).show();
                }
            }
        });
    }

    private void callApi() {
        btnProcessInitialTransaction.showProgress(true);

        request.setServiceDetailsID(ServiceDetails.MTNCredit.getId());
        request.setAmount(Double.parseDouble(Functions.toStingEditText(edtAmount)));

        transactionApi.getTopupCharge(request, topupApiListener);

    }

    APIListener<TopUpTransactionChargeCalculationResponse> topupApiListener = new APIListener<TopUpTransactionChargeCalculationResponse>() {
        @Override
        public void onResponse(Response<TopUpTransactionChargeCalculationResponse> response) {
            btnProcessInitialTransaction.hideProgressOnComplete(true);
            btnProcessInitialTransaction.onProgressCompleted();
            try {
                if (response.isSuccessful() && response.body() != null) {
                    topUpResponse = response.body();
                    if (topUpResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        frameRootTopup.startAnimation(animation);

                    } else {
                        Functions.showError(MtnActivity.this, topUpResponse.getResponse().getResponseMsg(), false);
                    }
                }
            } catch (Exception e) {

            }

        }

        @Override
        public void onFailure(Call<TopUpTransactionChargeCalculationResponse> call, Throwable t) {

        }
    };

    private void calculateAmount() {
        btnProcessInitialTransaction.showProgress(true);

        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                btnProcessInitialTransaction.hideProgressOnComplete(true);
                btnProcessInitialTransaction.onProgressCompleted();
                animation = new FlipAnimation(lineatInitialViewTopup, linearTrnsViewTopup);
                frameRootTopup.startAnimation(animation);

            }
        }.start();
    }

    private void initToolbar() {
        if (toolbar != null) {
            try {
                Functions.setToolbarWallet(toolbar, walletResponse, loginResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkVisibility();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        checkVisibility();
    }

    private void checkVisibility() {
        if (lineatInitialViewTopup.isShown()) {
            finish();
        } else {
            animation.reverse();
            frameRootTopup.startAnimation(animation);
        }
    }
}
