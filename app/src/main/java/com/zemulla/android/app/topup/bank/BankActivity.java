package com.zemulla.android.app.topup.bank;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.account.OTPGenValAPI;
import com.zemulla.android.app.api.payment.GetTopupTransactionApi;
import com.zemulla.android.app.api.zwallet.GetRecepientBankDetailsAPI;
import com.zemulla.android.app.api.zwallet.TopUpWalletBankTransferApi;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.DecimalDigitsInputFilter;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.helper.RetrofitErrorHelper;
import com.zemulla.android.app.helper.ServiceDetails;
import com.zemulla.android.app.home.HomeActivity;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.account.optgenval.OTPGenValRequest;
import com.zemulla.android.app.model.account.optgenval.OTPGenValResponse;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationRequest;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationResponse;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.model.zwallet.RecepientBank;
import com.zemulla.android.app.model.zwallet.RecepientBankListResponse;
import com.zemulla.android.app.model.zwallet.topupwalletbanktransfer.TopUpWalletBankTransferRequest;
import com.zemulla.android.app.model.zwallet.topupwalletbanktransfer.TopUpWalletBankTransferResponse;
import com.zemulla.android.app.topup.TopupActivity;
import com.zemulla.android.app.widgets.CustomSpinnerAdapter;
import com.zemulla.android.app.widgets.OTPDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbanje.kurt.fabbutton.FabButton;
import retrofit2.Call;
import retrofit2.Response;

public class BankActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTopupWayName)
    TextView txtTopupWayName;
    @BindView(R.id.btnProcessInitialTransaction)
    FabButton btnProcessInitialTransaction;
    @BindView(R.id.lineatInitialViewTopup)
    LinearLayout lineatInitialViewTopup;
    @BindView(R.id.spinnerRecipientBankList)
    AppCompatSpinner spinnerRecepientBankList;
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
    @BindView(R.id.edtBranchName)
    EditText edtBranchName;
    @BindView(R.id.edtAccountName)
    EditText edtAccountName;
    @BindView(R.id.edtAccountNumber)
    EditText edtAccountNumber;
    @BindView(R.id.edtSwiftCode)
    EditText edtSwiftCode;
    @BindView(R.id.edtRemark)
    EditText edtRemark;
    @BindView(R.id.txtEnterAmount)
    TextView txtEnterAmount;
    @BindView(R.id.edtAmount)
    EditText edtAmount;
    @BindView(R.id.txtTransactionCharge)
    TextView txtTransactionCharge;
    @BindView(R.id.txtEnterBankDetails)
    TextView txtEnterBankDetails;
    @BindView(R.id.txtTopupAmount)
    TextView txtTopupAmount;
    @BindView(R.id.txtPayableAmount)
    TextView txtPayableAmount;
    private FlipAnimation animation;
    //private ArrayList<String> banks;

    private OTPDialog otpDialog;
    private ProgressDialog progressDialog;

    private OTPGenValAPI otpGenValAPI;
    private GetTopupTransactionApi transactionApi;

    private TopUpTransactionChargeCalculationRequest request;
    private OTPGenValRequest otpGenValRequest;

    private LoginResponse loginResponse;
    private GetWalletDetailResponse walletResponse;
    private TopUpTransactionChargeCalculationResponse topUpResponse;
    private GetRecepientBankDetailsAPI getRecepientBankDetailsAPI;
    private List<RecepientBank> getSupportedBankDetailses;
    private CustomSpinnerAdapter customSpinnerAdapter;
    private RecepientBank recepientBank;
    private TopUpWalletBankTransferRequest topUpWalletBankTransferRequest;
    private TopUpWalletBankTransferApi topUpWalletBankTransferApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        ButterKnife.bind(this);
        edtAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter()});
        getSupportedBankDetailses = new ArrayList<>();
        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);
        otpGenValAPI = new OTPGenValAPI();
        topUpWalletBankTransferRequest = new TopUpWalletBankTransferRequest();
        topUpWalletBankTransferApi = new TopUpWalletBankTransferApi();
        otpGenValRequest = new OTPGenValRequest();
        transactionApi = new GetTopupTransactionApi();
        request = new TopUpTransactionChargeCalculationRequest();
        getRecepientBankDetailsAPI = new GetRecepientBankDetailsAPI();
        init();


    }

    private void init() {
        initToolbar();
        initApplyFonts();
        initOPTDialog();
        btnProcessInitialTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Functions.isEmpty(edtAmount)) {
                    Functions.showError(BankActivity.this, "Please Enter Amount", false);
                    return;
                }
                try {
                    Double amount = Double.valueOf(Functions.toStingEditText(edtAmount));
                    if (amount <= 0.0) {
                        Functions.showError(BankActivity.this, getString(R.string.invalid_amout), false);

                        return;
                    }
                } catch (NumberFormatException e) {
                    Functions.showError(BankActivity.this, getString(R.string.invalid_amout), false);
                    return;
                }
                if (Functions.isFabAnimate(btnProcessInitialTransaction)) {
                    return;
                }
                rateAPI();

            }

        });

        btnProcessResetTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.reverse();
                frameRootTopup.startAnimation(animation);
            }
        });

//        banks = new ArrayList<String>();
//        banks.add(getResources().getString(R.string.select_recipient_bank_prompt));
//        banks.add("Bank 1");
//        banks.add("Bank 2");
//        banks.add("Bank 3");
//        banks.add("Bank 4");
//        banks.add("Bank 5");
        customSpinnerAdapter = new CustomSpinnerAdapter(BankActivity.this, getSupportedBankDetailses);
        spinnerRecepientBankList.setAdapter(customSpinnerAdapter);
        spinnerRecepientBankList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recepientBank = (RecepientBank) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnProcessConfirmTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Functions.isFabAnimate(btnProcessConfirmTransaction)) {
                    return;
                }
                validateBankDetails();
            }
        });
    }

    private void initOPTDialog() {
        otpDialog = new OTPDialog(BankActivity.this, new OTPDialog.onSubmitListener() {
            @Override
            public void onSubmit(String OTP) {

                showProgressDialog();
                topUpBankTransacation(OTP);
            }

            @Override
            public void onResend() {
                generateOTPApi();
            }

            @Override
            public void ChangeEmail() {

            }


        });
    }

    private void rateAPI() {
        btnProcessInitialTransaction.showProgress(true);
        request.setServiceDetailsID(ServiceDetails.TopUpByAdmin.getId());
        request.setAmount(Double.parseDouble(Functions.toStingEditText(edtAmount)));

        transactionApi.getTopupCharge(request, topupApiListener);

    }

    APIListener<TopUpTransactionChargeCalculationResponse> topupApiListener = new APIListener<TopUpTransactionChargeCalculationResponse>() {
        @Override
        public void onResponse(Response<TopUpTransactionChargeCalculationResponse> response) {


            btnProcessInitialTransaction.showProgress(false);


            try {
                if (response.isSuccessful() && response.body() != null) {
                    topUpResponse = response.body();
                    if (topUpResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        if (Double.parseDouble(Functions.toStingEditText(edtAmount)) < topUpResponse.getTotalCharge()) {
                            Functions.showError(BankActivity.this, String.format("Amount should be more than %.2f ZWM", topUpResponse.getTotalCharge()), false);
                            return;
                        }
                        txtPayableAmount.setText(String.format("%s %.2f", AppConstant.ZMW, topUpResponse.getTopUpAmount()));
                        txtTopupAmount.setText(String.format("Topup Amount : %s %.2f", AppConstant.ZMW, topUpResponse.getAmount()));
                        txtTransactionCharge.setText(String.format("Transaction Charge : %s %.2f", AppConstant.ZMW, topUpResponse.getTotalCharge()));
                        animation = new FlipAnimation(lineatInitialViewTopup, linearTrnsViewTopup);
                        frameRootTopup.startAnimation(animation);
                        showProgressDialog();
                        getRecepientBankDetailsAPI.getSupportedBankDetailsAPI(getSupportedBankDetailsAPIListener);

                    } else {
                        Functions.showError(BankActivity.this, topUpResponse.getResponse().getResponseMsg(), false);
                    }
                } else {
                    Functions.showError(BankActivity.this, getResources().getString(R.string.unable), false);
                }
            } catch (Exception e) {
                Functions.showError(BankActivity.this, getResources().getString(R.string.unable), false);
            }

        }


        @Override
        public void onFailure(Call<TopUpTransactionChargeCalculationResponse> call, Throwable t) {
            btnProcessInitialTransaction.showProgress(false);
            RetrofitErrorHelper.showErrorMsg(t, BankActivity.this);
        }
    };

    APIListener<RecepientBankListResponse> getSupportedBankDetailsAPIListener = new APIListener<RecepientBankListResponse>() {
        @Override
        public void onResponse(Response<RecepientBankListResponse> response) {
            hidProgressDialog();
            if (response.isSuccessful() && response.body() != null) {
                if (response.body().getResponseCode() == AppConstant.ResponseSuccess) {
                    customSpinnerAdapter.setAsr(response.body().getResponseData().getData());
                } else {
                    Toast.makeText(BankActivity.this, response.body().getResponseMsg(), Toast.LENGTH_SHORT).show();
                }
            }

        }

        @Override
        public void onFailure(Call<RecepientBankListResponse> call, Throwable t) {
            hidProgressDialog();
        }
    };

    private void validateBankDetails() {
        if (recepientBank.getBankID() == -1) {
            Functions.showError(BankActivity.this, "Please Select Recipient Bank", false);
            return;
        } else if (Functions.isEmpty(edtBranchName)) {
            Functions.showError(BankActivity.this, "Please Enter Branch Name", false);
            return;
        } else if (Functions.isEmpty(edtAccountName)) {
            Functions.showError(BankActivity.this, "Please Enter Account Name", false);
            return;
        } else if (Functions.isEmpty(edtAccountNumber)) {
            Functions.showError(BankActivity.this, "Please Enter Account Number", false);
            return;
        } else if (Functions.isEmpty(edtSwiftCode)) {
            Functions.showError(BankActivity.this, "Please Enter Switft Code", false);
            return;
        } else if (Functions.isEmpty(edtRemark)) {
            Functions.showError(BankActivity.this, "Please Enter Remarks Code", false);
            return;
        } else {
            generateOTPApi();
        }
    }

    private void initApplyFonts() {
        txtTopupWayName.setTypeface(Functions.getLatoFont(this));
        txtEnterAmount.setTypeface(Functions.getLatoFont(this));
        txtTransactionCharge.setTypeface(Functions.getLatoFont(this));
        txtEnterBankDetails.setTypeface(Functions.getLatoFont(this));

        edtAccountName.setTypeface(Functions.getLatoFont(this));
        edtAccountNumber.setTypeface(Functions.getLatoFont(this));
        edtBranchName.setTypeface(Functions.getLatoFont(this));
        edtSwiftCode.setTypeface(Functions.getLatoFont(this));
    }

    private void initToolbar() {
        if (toolbar != null) {
            try {
                Functions.setToolbarWallet(toolbar, walletResponse, loginResponse);
            } catch (Exception e) {
                Log.d("error", "Exception");
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
        otpDialog.show();
        otpDialog.setDisplayText(false, loginResponse.getMobile(), "");
    }

    private void topUpBankTransacation(String OTP) {


//        AccountName = "";
        topUpWalletBankTransferRequest.setAccountName(Functions.toStingEditText(edtAccountName));
//        AccountNumber = "";
        topUpWalletBankTransferRequest.setAccountNumber(Functions.toStingEditText(edtAccountNumber));
//        BankDetailID = 0;
        //topUpWalletBankTransferRequest.setBankDetailID(recepientBank.getBankDetailID());
//        BankID = 0;
        topUpWalletBankTransferRequest.setBankID(recepientBank.getBankID());
//        BranchName = "";
        topUpWalletBankTransferRequest.setBranchName(Functions.toStingEditText(edtBranchName));
//        RejectReason = "";
        topUpWalletBankTransferRequest.setRejectReason("");
//        RequestedAmount = 0;
        topUpWalletBankTransferRequest.setRequestedAmount(Double.parseDouble(Functions.toStingEditText(edtAmount)));
//        ServiceDetailID = 0;
        topUpWalletBankTransferRequest.setServiceDetailID(ServiceDetails.TopUpByAdmin.getId());
//        SwiftCode = "";
        topUpWalletBankTransferRequest.setSwiftCode(Functions.toStingEditText(edtSwiftCode));
//        TopUpAmount = 0;
        topUpWalletBankTransferRequest.setTopUpAmount(topUpResponse.getTopUpAmount());
//        TopUpWalletID = 0;
        topUpWalletBankTransferRequest.setTopUpWalletID(0);
//        TransactionCharge = 0;
        topUpWalletBankTransferRequest.setTransactionCharge(topUpResponse.getTotalCharge());
//        UserID = 0;
        topUpWalletBankTransferRequest.setUserID(PrefUtils.getUserID(this));
//        UserRemark = "";
        topUpWalletBankTransferRequest.setUserRemark(Functions.toStingEditText(edtRemark));
//        VerificationCode = "";
        topUpWalletBankTransferRequest.setVerificationCode(OTP);
//        ZemullaTransactionID = "";
        topUpWalletBankTransferRequest.setZemullaTransactionID("");

        topUpWalletBankTransferApi.getTopUpWalletBankTransferApi(topUpWalletBankTransferRequest, topUpWalletBankTransferResponseAPIListener);

    }


    APIListener<TopUpWalletBankTransferResponse> topUpWalletBankTransferResponseAPIListener = new APIListener<TopUpWalletBankTransferResponse>() {
        @Override
        public void onResponse(Response<TopUpWalletBankTransferResponse> response) {

            hidProgressDialog();
            if (response.isSuccessful() && response.body() != null) {

                if (response.body().getResponseCode() == AppConstant.ResponseSuccess) {
                    otpDialog.disMissDiaLog();
                    Functions.showSuccessMsg(BankActivity.this, response.body().getResponseMsg(), true, HomeActivity.class);

                } else {
                    Toast.makeText(BankActivity.this, response.body().getResponseMsg(), Toast.LENGTH_SHORT).show();

                }
            }

        }

        @Override
        public void onFailure(Call<TopUpWalletBankTransferResponse> call, Throwable t) {
            hidProgressDialog();
            RetrofitErrorHelper.showErrorMsg(t, BankActivity.this);
        }
    };

    private void generateOTPApi() {
        btnProcessConfirmTransaction.showProgress(true);

        otpGenValRequest.setCallingCode(loginResponse.getCallingCode());
        otpGenValRequest.setMobile(loginResponse.getMobile());
        otpGenValRequest.setUserID(PrefUtils.getUserID(this));
        otpGenValAPI.otpGenVal(otpGenValRequest, otpApiListener);

    }

    APIListener<OTPGenValResponse> otpApiListener = new APIListener<OTPGenValResponse>() {
        @Override
        public void onResponse(Response<OTPGenValResponse> response) {
            btnProcessConfirmTransaction.showProgress(false);
            try {
                if (response.isSuccessful() && response.body() != null) {
                    //Todo remove this OTPResponseSuccess oon release time
                    if (response.body().getResponse().getResponseCode() == AppConstant.OTPResponseSuccess) {
                        if (!otpDialog.isShowing()) {
                            openOTPDialog();
                            Toast.makeText(BankActivity.this, response.body().getResponse().getResponseMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Functions.showError(BankActivity.this, response.body().getResponse().getResponseMsg(), false);
                    }
                } else {
                    Functions.showError(BankActivity.this, getResources().getString(R.string.unable), false);
                }
            } catch (Exception e) {
                Log.d("error", "Exception");
                Functions.showError(BankActivity.this, getResources().getString(R.string.unable), false);
            }

        }

        @Override
        public void onFailure(Call<OTPGenValResponse> call, Throwable t) {
            btnProcessConfirmTransaction.showProgress(false);
            RetrofitErrorHelper.showErrorMsg(t, BankActivity.this);
        }
    };

    @Override
    public void onBackPressed() {
        checkVisibility();
    }

    private void checkVisibility() {
        if (lineatInitialViewTopup.isShown()) {
            Functions.fireIntentWithClearFlagWithWithPendingTransition(BankActivity.this, TopupActivity.class);
        } else {
            animation.reverse();
            frameRootTopup.startAnimation(animation);
        }
    }
}
