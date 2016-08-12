package com.zemulla.android.app.fundtransfer.mtn;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.payment.GetFundTransferTransactionCalApi;
import com.zemulla.android.app.api.zwallet.SendMoneyToAPIWalletAPI;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.constant.IntentConstant;
import com.zemulla.android.app.helper.DecimalDigitsInputFilter;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.FundTransferTransactionChargeCalculationResponse;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationRequest;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.model.zwallet.sendmoneytoapiwallet.SendMoneyToAPIWalletRequest;
import com.zemulla.android.app.model.zwallet.sendmoneytoapiwallet.SendMoneyToAPIWalletResponse;
import com.zemulla.android.app.widgets.OTPDialogAfterLogin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mbanje.kurt.fabbutton.FabButton;
import retrofit2.Call;
import retrofit2.Response;

public class MTNFundTransferActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtFundTransferName)
    TextView txtFundTransferName;
    @BindView(R.id.edtAmount)
    EditText edtAmount;
    @BindView(R.id.checkRateFab)
    FabButton checkRateFab;
    @BindView(R.id.fundTransferHolder)
    LinearLayout fundTransferHolder;
    @BindView(R.id.edtNumber)
    EditText edtNumber;
    @BindView(R.id.resetAmountFabButton)
    FabButton resetAmountFabButton;
    @BindView(R.id.confitmAmountFabButton)
    FabButton confitmAmountFabButton;
    @BindView(R.id.rateHolder)
    LinearLayout rateHolder;
    @BindView(R.id.frameRootTopup)
    FrameLayout frameRootTopup;
    @BindView(R.id.activity_topup_initial_transaction)
    LinearLayout activityTopupInitialTransaction;
    @BindView(R.id.txtTopupAmount)
    TextView txtTopupAmount;
    @BindView(R.id.txtPayableAmount)
    TextView txtPayableAmount;
    @BindView(R.id.txtTransactionCharge)
    TextView txtTransactionCharge;
    private FlipAnimation animation;
    private GetWalletDetailResponse walletResponse;
    private LoginResponse loginResponse;
    private OTPDialogAfterLogin otpDialogAfterLogin;
    private GetFundTransferTransactionCalApi getFundTransferTransactionCalApi;
    private TopUpTransactionChargeCalculationRequest topUpTransactionChargeCalculationRequest;
    private FundTransferTransactionChargeCalculationResponse fundTransferTransactionChargeCalculationResponse;
    Unbinder unbinder;
    private ProgressDialog progressDialog;

    private SendMoneyToAPIWalletAPI sendMoneyToAPIWalletAPI;
    private SendMoneyToAPIWalletRequest sendMoneyToAPIWalletRequest;
    private int serviceID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtnfund_transfer);
        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        getDataFromIntent();
        init();
        edtAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter()});
    }

    private void getDataFromIntent() {
        serviceID = getIntent().getIntExtra(IntentConstant.INTENT_EXTRA_SERVICE_DETAILS, 0);
    }

    private void init() {
        sendMoneyToAPIWalletAPI = new SendMoneyToAPIWalletAPI();
        sendMoneyToAPIWalletRequest = new SendMoneyToAPIWalletRequest();
        getFundTransferTransactionCalApi = new GetFundTransferTransactionCalApi();
        topUpTransactionChargeCalculationRequest = new TopUpTransactionChargeCalculationRequest();
        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);
        initToolbar();
        actionListener();
        otpDialogAfterLogin = new OTPDialogAfterLogin(new MaterialDialog.Builder(MTNFundTransferActivity.this));
        otpDialogAfterLogin.setOnSubmitListener(onSubmitListener);

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


    OTPDialogAfterLogin.OnSubmitListener onSubmitListener = new OTPDialogAfterLogin.OnSubmitListener() {
        @Override
        public void onSubmit(String OTP) {

            sendWalletToAccount(OTP);

        }

        @Override
        public void OTPReceived() {
            hidProgressDialog();
        }
    };

    private void sendWalletToAccount(String OTP) {
        sendMoneyToAPIWalletRequest.setAmount(Double.parseDouble(Functions.toStingEditText(edtAmount)));
        sendMoneyToAPIWalletRequest.setMobileNo(Functions.toStingEditText(edtNumber));
        sendMoneyToAPIWalletRequest.setTotalCharge(fundTransferTransactionChargeCalculationResponse.getTotalCharge());
        sendMoneyToAPIWalletRequest.setTotalPayableAmount(fundTransferTransactionChargeCalculationResponse.getTotalPayableAmount());
        sendMoneyToAPIWalletRequest.setUserID(PrefUtils.getUserID(this));
        sendMoneyToAPIWalletRequest.setServiceDetailID(serviceID);
        sendMoneyToAPIWalletRequest.setVerificationCode(OTP);

        sendMoneyToAPIWalletAPI.getSendMoneyToAPIWallet(sendMoneyToAPIWalletRequest, sendMoneyToAPIWalletResponseAPIListener);

    }


    APIListener<SendMoneyToAPIWalletResponse> sendMoneyToAPIWalletResponseAPIListener = new APIListener<SendMoneyToAPIWalletResponse>() {
        @Override
        public void onResponse(Response<SendMoneyToAPIWalletResponse> response) {

            try {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        otpDialogAfterLogin.dismiss();
                        Functions.showSuccessMsg(MTNFundTransferActivity.this, response.body().getResponse().getResponseMsg(), true);
                    } else {
                        Functions.showError(MTNFundTransferActivity.this, response.body().getResponse().getResponseMsg(), false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<SendMoneyToAPIWalletResponse> call, Throwable t) {

        }
    };

    private void actionListener() {
        checkRateFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Functions.isEmpty(edtAmount)) {
                    Functions.showError(MTNFundTransferActivity.this, "Please Enter Amount", false);
                    return;
                }
                if (Double.parseDouble(Functions.toStingEditText(edtAmount)) > walletResponse.getEffectiveBalance()) {
                    Functions.showError(MTNFundTransferActivity.this, "Enter Valid Amount", false);
                    return;
                }
                calculateAmount();

            }
        });

        resetAmountFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.reverse();
                frameRootTopup.startAnimation(animation);
            }
        });

        confitmAmountFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Functions.isEmpty(edtNumber)) {
                    Functions.showError(MTNFundTransferActivity.this, "Please Enter Number", false);
                } else if (Functions.getLength(edtNumber) < 10) {
                    Functions.showError(MTNFundTransferActivity.this, "Please Enter Valid Number", false);
                }
                showProgressDialog();
                otpDialogAfterLogin.generateOPT();
            }
        });
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
                finish();
            }
        });
    }

    private void calculateAmount() {
        checkRateFab.showProgress(true);
        topUpTransactionChargeCalculationRequest.setAmount(Double.parseDouble(Functions.toStingEditText(edtAmount)));
        topUpTransactionChargeCalculationRequest.setServiceDetailsID(serviceID);
        getFundTransferTransactionCalApi.getTopupCharge(topUpTransactionChargeCalculationRequest, fundTransferTransactionChargeCalculationResponseAPIListener);
        // Toast.makeText(this, "" + serviceID, Toast.LENGTH_LONG).show();
    }


    APIListener<FundTransferTransactionChargeCalculationResponse> fundTransferTransactionChargeCalculationResponseAPIListener = new APIListener<FundTransferTransactionChargeCalculationResponse>() {
        @Override
        public void onResponse(Response<FundTransferTransactionChargeCalculationResponse> response) {

            animation = new FlipAnimation(fundTransferHolder, rateHolder);
            checkRateFab.showProgress(false);

            try {
                if (response.isSuccessful() && response.body() != null) {
                    fundTransferTransactionChargeCalculationResponse = response.body();
                    if (fundTransferTransactionChargeCalculationResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        txtPayableAmount.setText(String.format("%s %.2f", AppConstant.ZMW, fundTransferTransactionChargeCalculationResponse.getTotalPayableAmount()));
                        txtTopupAmount.setText(String.format("Topup Amount : %s %.2f", AppConstant.ZMW, fundTransferTransactionChargeCalculationResponse.getAmount()));
                        txtTransactionCharge.setText(String.format("Transaction Charge : %s %.2f", AppConstant.ZMW, fundTransferTransactionChargeCalculationResponse.getTotalCharge()));

                        frameRootTopup.startAnimation(animation);

                    } else {
                        Functions.showError(MTNFundTransferActivity.this, fundTransferTransactionChargeCalculationResponse.getResponse().getResponseMsg(), false);
                    }
                } else {
                    Functions.showError(MTNFundTransferActivity.this, "Something went wrong. Please try again.", false);
                }
            } catch (Exception e) {
                Functions.showError(MTNFundTransferActivity.this, "Something went wrong. Please try again.", false);
            }

        }

        @Override
        public void onFailure(Call<FundTransferTransactionChargeCalculationResponse> call, Throwable t) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
