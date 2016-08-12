package com.zemulla.android.app.topup.mtn;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputFilter;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.account.OTPGenValAPI;
import com.zemulla.android.app.api.payment.GetTopupTransactionApi;
import com.zemulla.android.app.api.zwallet.DynamicTextAPI;
import com.zemulla.android.app.api.zwallet.TopupAPIWalletApi;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.constant.IntentConstant;
import com.zemulla.android.app.helper.DecimalDigitsInputFilter;
import com.zemulla.android.app.helper.DynamicMasterId;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.home.LogUtils;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.account.optgenval.OTPGenValRequest;
import com.zemulla.android.app.model.account.optgenval.OTPGenValResponse;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationRequest;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationResponse;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.model.zwallet.DynamicTextResponse;
import com.zemulla.android.app.model.zwallet.topupwallet.TopUpRequest;
import com.zemulla.android.app.model.zwallet.topupwallet.TopUpResponse;
import com.zemulla.android.app.widgets.OTPDialog;
import com.zemulla.android.app.widgets.TfTextView;

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
    TfTextView txtTopupWayName;
    @BindView(R.id.btnProcessInitialTransaction)
    FabButton btnProcessInitialTransaction;
    @BindView(R.id.lineatInitialViewTopup)
    RelativeLayout lineatInitialViewTopup;
    @BindView(R.id.btnProcessResetTransaction)
    FabButton btnProcessResetTransaction;
    @BindView(R.id.btnProcessConfirmTransaction)
    FabButton btnProcessConfirmTransaction;
    @BindView(R.id.linearTrnsViewTopup)
    LinearLayout linearTrnsViewTopup;
    @BindView(R.id.frameRootTopup)
    RelativeLayout frameRootTopup;
    @BindView(R.id.activity_topup_initial_transaction)
    LinearLayout activityTopupInitialTransaction;
    @BindView(R.id.edtAmount)
    EditText edtAmount;
    @BindView(R.id.edtNumber)
    EditText edtNumber;
    @BindView(R.id.edtNationdID)
    EditText edtNationdID;
    @BindView(R.id.txtTopupAmount)
    TextView txtTopupAmount;
    @BindView(R.id.txtPayableAmount)
    TextView txtPayableAmount;
    @BindView(R.id.txtTransactionCharge)
    TextView txtTransactionCharge;
    @BindView(R.id.txtDynamic)
    TfTextView txtDynamic;
    @BindView(R.id.edtPin)
    EditText edtPin;

    private FlipAnimation animation;
    Unbinder unbinder;
    private GetTopupTransactionApi transactionApi;
    private TopUpTransactionChargeCalculationRequest request;
    private TopUpTransactionChargeCalculationResponse topUpResponse;
    private GetWalletDetailResponse walletResponse;
    private LoginResponse loginResponse;
    private OTPGenValAPI otpGenValAPI;
    private OTPGenValRequest otpGenValRequest;


    private ProgressDialog progressDialog;

    private APIListener<OTPGenValResponse> otpApiListener;
    private TopupAPIWalletApi topupAPIWalletApi;
    private TopUpRequest topUpWalletRequest;
    private APIListener<TopUpResponse> topupWalletApiListener;
    private OTPDialog otpDialog;
    private DynamicTextAPI dynamicTextAPI;

    private int masterID, serviceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtn);

        unbinder = ButterKnife.bind(this);
        getIntentData();

        transactionApi = new GetTopupTransactionApi();
        request = new TopUpTransactionChargeCalculationRequest();
        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);
        otpGenValAPI = new OTPGenValAPI();
        otpGenValRequest = new OTPGenValRequest();
        topupAPIWalletApi = new TopupAPIWalletApi();
        dynamicTextAPI = new DynamicTextAPI();

        init();
    }

    private void getIntentData() {
        masterID = getIntent().getIntExtra(IntentConstant.INTENT_EXTRA_MASTER_ID, 0);
        serviceID = getIntent().getIntExtra(IntentConstant.INTENT_EXTRA_SERVICE_DETAILS, 0);

        if (masterID == DynamicMasterId.Airtel.getId()) {
            txtTopupWayName.setText("Airtel Money");

        } else if (masterID == DynamicMasterId.MTN.getId()) {
            txtTopupWayName.setText("MTN");

        } else {
            txtTopupWayName.setText("Zoona Cash");
            edtPin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void init() {

        initToolbar();

        txtDynamic.setMovementMethod(new ScrollingMovementMethod());

        actionListener();

        animation = new FlipAnimation(lineatInitialViewTopup, linearTrnsViewTopup);

        edtAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter()});

        getDynamicTextApi();
    }

    private void getDynamicTextApi() {
        dynamicTextAPI.getDynamicText(masterID, new APIListener<DynamicTextResponse>() {
            @Override
            public void onResponse(Response<DynamicTextResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        txtDynamic.setText(Html.fromHtml(response.body().getDText()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    private void actionListener() {

        topupWalletApiListener = new APIListener<TopUpResponse>() {
            @Override
            public void onResponse(Response<TopUpResponse> response) {
                hidProgressDialog();

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                            otpDialog.disMissDiaLog();
                            Functions.showSuccessMsg(MtnActivity.this, response.body().getResponse().getResponseMsg(), true);

                        } else {
                            Functions.showError(MtnActivity.this, response.body().getResponse().getResponseMsg(), false);
                        }
                    } else {
                        Functions.showError(MtnActivity.this, "Something went wrong. Please try again.", false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Functions.showError(MtnActivity.this, "Something went wrong. Please try again.", false);
                }
            }

            @Override
            public void onFailure(Call<TopUpResponse> call, Throwable t) {
                hidProgressDialog();
                Functions.showError(MtnActivity.this, "Something went wrong. Please try again.", false);
            }
        };

        otpApiListener = new APIListener<OTPGenValResponse>() {
            @Override
            public void onResponse(Response<OTPGenValResponse> response) {
                btnProcessConfirmTransaction.showProgress(false);

                try {
                    if (response.isSuccessful() && response.body() != null) {
                        //Todo remove this OTPResponseSuccess oon release time
                        if (response.body().getResponse().getResponseCode() == AppConstant.OTPResponseSuccess) {
                            openOTPDialog();
                        } else {
                            Functions.showError(MtnActivity.this, response.body().getResponse().getResponseMsg(), false);
                        }
                    } else {
                        Functions.showError(MtnActivity.this, "Something went wrong. Please try again.", false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Functions.showError(MtnActivity.this, "Something went wrong. Please try again.", false);
                }
            }

            @Override
            public void onFailure(Call<OTPGenValResponse> call, Throwable t) {
                btnProcessConfirmTransaction.showProgress(false);
            }
        };


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
                    if (masterID == DynamicMasterId.Zoona.getId()) {
                        if (Functions.isEmpty(edtPin)) {
                            Functions.showError(MtnActivity.this, "Please Enter PIN", false);
                            return;
                        }
                        if (Functions.getLength(edtPin) < 4) {
                            Functions.showError(MtnActivity.this, "Enter 4 Digit PIN.", false);
                            return;
                        }

                    }
                    generateOTPApi();
                }
            }
        });
    }

    private void callTopupWalletApi(String OTP) {
        showProgressDialog();

        topUpWalletRequest = new TopUpRequest();
        topUpWalletRequest.setServiceDetailID(serviceID);
        topUpWalletRequest.setUserID(PrefUtils.getUserID(this));
        topUpWalletRequest.setAmount(topUpResponse.getAmount());
        topUpWalletRequest.setNationalID(Functions.toStingEditText(edtNationdID));
        topUpWalletRequest.setMobileNo(Functions.toStingEditText(edtNumber));
        topUpWalletRequest.setTotalCharge(topUpResponse.getTotalCharge());
        topUpWalletRequest.setTotalPayableAmount(topUpResponse.getTopUpAmount());
        topUpWalletRequest.setVerificationCode(OTP);

        if (masterID == DynamicMasterId.Zoona.getId()) {
            topUpWalletRequest.setPIN(Functions.toStingEditText(edtPin));
        }

        LogUtils.LOGE("req", topUpWalletRequest.toString());

        topupAPIWalletApi.getTopupCharge(topUpWalletRequest, topupWalletApiListener);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
    }


    private void showProgressDialog() {
        if (progressDialog == null) {
            initProgressDialog();
        }
        progressDialog.show();
    }

    private void hidProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    private void openOTPDialog() {
        otpDialog = new OTPDialog(MtnActivity.this, new OTPDialog.onSubmitListener() {
            @Override
            public void onSubmit(String OTP) {
                callTopupWalletApi(OTP);
            }

            @Override
            public void onResend() {
                generateOTPApi();
            }

            @Override
            public void ChangeEmail() {

            }


        });
        otpDialog.show();
        otpDialog.setDisplayText(false, loginResponse.getMobile(), "");
    }

    private void generateOTPApi() {
        btnProcessConfirmTransaction.showProgress(true);

        otpGenValRequest.setCallingCode(loginResponse.getCallingCode());
        otpGenValRequest.setMobile(loginResponse.getMobile());
        otpGenValRequest.setUserID(PrefUtils.getUserID(this));

        otpGenValAPI.otpGenVal(otpGenValRequest, otpApiListener);

    }

    private void callApi() {
        btnProcessInitialTransaction.showProgress(true);

        request.setServiceDetailsID(serviceID);
        request.setAmount(Double.parseDouble(Functions.toStingEditText(edtAmount)));

        transactionApi.getTopupCharge(request, topupApiListener);

    }

    APIListener<TopUpTransactionChargeCalculationResponse> topupApiListener = new APIListener<TopUpTransactionChargeCalculationResponse>() {
        @Override
        public void onResponse(Response<TopUpTransactionChargeCalculationResponse> response) {
            animation = new FlipAnimation(lineatInitialViewTopup, linearTrnsViewTopup);
            btnProcessInitialTransaction.showProgress(false);

            try {
                if (response.isSuccessful() && response.body() != null) {
                    topUpResponse = response.body();
                    if (topUpResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        txtPayableAmount.setText(String.format("%s %.2f", AppConstant.ZMW, topUpResponse.getTopUpAmount()));
                        txtTopupAmount.setText(String.format("Topup Amount : %s %.2f", AppConstant.ZMW, topUpResponse.getAmount()));
                        txtTransactionCharge.setText(String.format("Transaction Charge : %s %.2f", AppConstant.ZMW, topUpResponse.getTotalCharge()));

                        frameRootTopup.startAnimation(animation);

                    } else {
                        Functions.showError(MtnActivity.this, topUpResponse.getResponse().getResponseMsg(), false);
                    }
                } else {
                    Functions.showError(MtnActivity.this, "Something went wrong. Please try again.", false);
                }
            } catch (Exception e) {
                Functions.showError(MtnActivity.this, "Something went wrong. Please try again.", false);
            }

        }

        @Override
        public void onFailure(Call<TopUpTransactionChargeCalculationResponse> call, Throwable t) {
            btnProcessInitialTransaction.showProgress(false);
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
