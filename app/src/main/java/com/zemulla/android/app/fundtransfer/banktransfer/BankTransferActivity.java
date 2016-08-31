package com.zemulla.android.app.fundtransfer.banktransfer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.payment.GetFundTransferTransactionCalApi;
import com.zemulla.android.app.api.user.GetUserDetailIDWiseADAPI;
import com.zemulla.android.app.api.zwallet.SendMoneyBantTransferAPI;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.DecimalDigitsInputFilter;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.helper.ServiceDetails;
import com.zemulla.android.app.home.HomeActivity;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.FundTransferTransactionChargeCalculationResponse;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationRequest;
import com.zemulla.android.app.model.user.getuserdetailidwise.GetUserDetailIDWise;
import com.zemulla.android.app.model.user.getuserdetailidwise.GetUserDetailIDWiseResponse;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.model.zwallet.sendmoneybanttransfer.SendMoneyBantTransferRequest;
import com.zemulla.android.app.model.zwallet.sendmoneybanttransfer.SendMoneyBantTransferResponse;
import com.zemulla.android.app.user.UserProfileActivity;
import com.zemulla.android.app.widgets.OTPDialogAfterLogin;
import com.zemulla.android.app.widgets.TfTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mbanje.kurt.fabbutton.FabButton;
import retrofit2.Call;
import retrofit2.Response;

public class BankTransferActivity extends AppCompatActivity {

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
    @BindView(R.id.noDetailsFoundTextView)
    Button noDetailsFoundTextView;
    @BindView(R.id.bankNameEditText)
    TfTextView bankNameEditText;
    @BindView(R.id.branchNameTextView)
    TfTextView branchNameTextView;
    @BindView(R.id.accountName)
    TfTextView accountName;
    @BindView(R.id.accountNumber)
    TfTextView accountNumber;
    @BindView(R.id.swiftCode)
    TfTextView swiftCode;
    @BindView(R.id.bankDetailsHolder)
    LinearLayout bankDetailsHolder;
    @BindView(R.id.txtTopupAmount)
    TextView txtTopupAmount;
    @BindView(R.id.txtPayableAmount)
    TextView txtPayableAmount;
    @BindView(R.id.txtTransactionCharge)
    TextView txtTransactionCharge;
    @BindView(R.id.edtRemark)
    EditText edtRemark;
    private ProgressDialog progressDialog;
    private FlipAnimation animation;
    private GetWalletDetailResponse walletResponse;
    private LoginResponse loginResponse;
    private GetUserDetailIDWiseADAPI getUserDetailIDWiseADAPI;
    private SendMoneyBantTransferRequest sendMoneyBantTransferRequest;
    private SendMoneyBantTransferAPI sendMoneyBantTransferAPI;
    private GetUserDetailIDWiseResponse getUserDetailIDWiseResponse;
    Unbinder unbinder;
    private TopUpTransactionChargeCalculationRequest topUpTransactionChargeCalculationRequest;
    private FundTransferTransactionChargeCalculationResponse fundTransferTransactionChargeCalculationResponse;
    private boolean isBankDetailsFiledup;
    private GetFundTransferTransactionCalApi getFundTransferTransactionCalApi;
    private OTPDialogAfterLogin otpDialogAfterLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_transfer);
        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        init();
        edtAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter()});
    }

    private void init() {

        sendMoneyBantTransferAPI = new SendMoneyBantTransferAPI();
        sendMoneyBantTransferRequest = new SendMoneyBantTransferRequest();
        getUserDetailIDWiseADAPI = new GetUserDetailIDWiseADAPI();
        getFundTransferTransactionCalApi = new GetFundTransferTransactionCalApi();
        topUpTransactionChargeCalculationRequest = new TopUpTransactionChargeCalculationRequest();
        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);
        otpDialogAfterLogin = new OTPDialogAfterLogin(new MaterialDialog.Builder(BankTransferActivity.this));
        otpDialogAfterLogin.setOnSubmitListener(onSubmitListener);
        initToolbar();
        actionListener();
        showProgressDialog();
        getUserDetailIDWiseADAPI.getUserDetailIDWiseAD(String.valueOf(PrefUtils.getUserID(this)), getWalletDetailResponseAPIListener);
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

    private void sendWalletToAccount(String otp) {

        sendMoneyBantTransferRequest.setVerificationCode(otp);
        if (Functions.isEmpty(edtRemark)) {
            sendMoneyBantTransferRequest.setRemark("");
        }
        sendMoneyBantTransferRequest.setAmount(Double.parseDouble(Functions.toStingEditText(edtAmount)));
        sendMoneyBantTransferRequest.setRemark(Functions.toStingEditText(edtRemark));
        sendMoneyBantTransferRequest.setTotalCharge(fundTransferTransactionChargeCalculationResponse.getTotalCharge());
        sendMoneyBantTransferRequest.setTotalPayableAmount(fundTransferTransactionChargeCalculationResponse.getTotalPayableAmount());
        sendMoneyBantTransferRequest.setUserID(PrefUtils.getUserID(this));
        sendMoneyBantTransferAPI.getTopUpWalletBankTransferApi(sendMoneyBantTransferRequest, sendMoneyBantTransferResponseAPIListener);
    }


    APIListener<SendMoneyBantTransferResponse> sendMoneyBantTransferResponseAPIListener = new APIListener<SendMoneyBantTransferResponse>() {
        @Override
        public void onResponse(Response<SendMoneyBantTransferResponse> response) {
            try {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        otpDialogAfterLogin.dismiss();
                        Functions.showSuccessMsg(BankTransferActivity.this, response.body().getResponse().getResponseMsg(), true, HomeActivity.class);
                    } else {
                        Functions.showError(BankTransferActivity.this, response.body().getResponse().getResponseMsg(), false);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<SendMoneyBantTransferResponse> call, Throwable t) {

        }
    };

    APIListener<GetUserDetailIDWiseResponse> getWalletDetailResponseAPIListener = new APIListener<GetUserDetailIDWiseResponse>() {
        @Override
        public void onResponse(Response<GetUserDetailIDWiseResponse> response) {
            hidProgressDialog();
            try {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        getUserDetailIDWiseResponse = response.body();
                        setDetails();
                    } else {
                        Functions.showError(BankTransferActivity.this, getUserDetailIDWiseResponse.getResponse().getResponseMsg(), false);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetUserDetailIDWiseResponse> call, Throwable t) {
            hidProgressDialog();
        }
    };

    private void setDetails() {
        try {
            GetUserDetailIDWise data = getUserDetailIDWiseResponse.getData();
            if (data != null && (TextUtils.isEmpty(data.getBankName()))) {
                isBankDetailsFiledup = false;
                bankDetailsHolder.setVisibility(View.GONE);
                noDetailsFoundTextView.setVisibility(View.VISIBLE);
                noDetailsFoundTextView.setText("No Bank Details found.To add Bank Details Click Here");
            } else {
                isBankDetailsFiledup = true;
                bankDetailsHolder.setVisibility(View.VISIBLE);
                noDetailsFoundTextView.setVisibility(View.GONE);
                bankNameEditText.setText(String.format("Bank Name : %s", data.getBranchName()));
                branchNameTextView.setText(String.format("Branch Name : %s", data.getBranchName()));
                accountName.setText(String.format("Account Name : %s", data.getAccountName()));
                accountNumber.setText(String.format("Account Number : %s", data.getAccountNumber()));
                swiftCode.setText(String.format("Swift Code : %s", data.getSwiftCode()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void actionListener() {
        checkRateFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isBankDetailsFiledup) {
                    Functions.showError(BankTransferActivity.this, "Please Add Bank Details.", false);
                    return;
                }
                if (Functions.isEmpty(edtAmount)) {
                    Functions.showError(BankTransferActivity.this, "Please Enter Amount", false);
                    return;
                }
                if (Double.parseDouble(Functions.toStingEditText(edtAmount)) > walletResponse.getEffectiveBalance()) {
                    Functions.showError(BankTransferActivity.this, "Enter Valid Amount", false);
                    return;
                }
                if (Functions.isFabAnimate(checkRateFab)) {
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
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    private void calculateAmount() {
        checkRateFab.showProgress(true);
        topUpTransactionChargeCalculationRequest.setAmount(Double.parseDouble(Functions.toStingEditText(edtAmount)));
        topUpTransactionChargeCalculationRequest.setServiceDetailsID(ServiceDetails.WithdrawalByAdmin.getId());
        getFundTransferTransactionCalApi.getTopupCharge(topUpTransactionChargeCalculationRequest, fundTransferTransactionChargeCalculationResponseAPIListener);


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
                        Functions.showError(BankTransferActivity.this, fundTransferTransactionChargeCalculationResponse.getResponse().getResponseMsg(), false);
                    }
                } else {
                    Functions.showError(BankTransferActivity.this, "Something went wrong. Please try again.", false);
                }
            } catch (Exception e) {
                Functions.showError(BankTransferActivity.this, "Something went wrong. Please try again.", false);
            }

        }

        @Override
        public void onFailure(Call<FundTransferTransactionChargeCalculationResponse> call, Throwable t) {

        }
    };

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.noDetailsFoundTextView)
    public void onClick() {
        Functions.fireIntent(this, UserProfileActivity.class);
        finish();
    }
}
