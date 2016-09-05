package com.zemulla.android.app.topup.cyber;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.payment.GetFundTransferTransactionCalApi;
import com.zemulla.android.app.api.payment.PaymentAPI;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.constant.IntentConstant;
import com.zemulla.android.app.emarket.dstv.MonthAdapter;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.helper.RetrofitErrorHelper;
import com.zemulla.android.app.helper.ServiceDetails;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.month.Month;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.FundTransferTransactionChargeCalculationResponse;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationRequest;
import com.zemulla.android.app.model.payment.cybersourcepayment1.CybersourcePayment1Request;
import com.zemulla.android.app.model.payment.cybersourcepayment1.CybersourcePayment1Response;
import com.zemulla.android.app.model.payment.generatesignaturecs.GenerateSignatureCSRequest;
import com.zemulla.android.app.model.payment.generatesignaturecs.GenerateSignatureCSResponse;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbanje.kurt.fabbutton.FabButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CyberSourceActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTopupWayName)
    TextView txtTopupWayName;
    @BindView(R.id.edtAmount)
    EditText edtAmount;
    @BindView(R.id.btnProcessInitialTransaction)
    FabButton btnProcessInitialTransaction;
    @BindView(R.id.lineatInitialViewTopup)
    LinearLayout lineatInitialViewTopup;
    @BindView(R.id.txtTopupAmount)
    TextView txtTopupAmount;
    @BindView(R.id.txtPayableAmount)
    TextView txtPayableAmount;
    @BindView(R.id.txtTransactionCharge)
    TextView txtTransactionCharge;
    @BindView(R.id.resetFab)
    FabButton resetFab;
    @BindView(R.id.confirmFab)
    FabButton confirmFab;
    @BindView(R.id.linearTrnsViewTopup)
    LinearLayout linearTrnsViewTopup;
    @BindView(R.id.frameRootTopup)
    FrameLayout frameRootTopup;
    @BindView(R.id.activity_topup_initial_transaction)
    LinearLayout activityTopupInitialTransaction;

    @BindView(R.id.edtcardNumber)
    EditText edtcardNumber;
    @BindView(R.id.selectMonthSpinner)
    AppCompatSpinner selectMonthSpinner;
    @BindView(R.id.yearSpinner)
    AppCompatSpinner yearSpinner;
    @BindView(R.id.rdVisa)
    RadioButton rdVisa;
    @BindView(R.id.rdmasterCard)
    RadioButton rdmasterCard;
    private FlipAnimation animation;
    private ProgressDialog progressDialog;

    private GetWalletDetailResponse walletResponse;
    private LoginResponse loginResponse;

    private GetFundTransferTransactionCalApi getFundTransferTransactionCalApi;
    private TopUpTransactionChargeCalculationRequest topUpTransactionChargeCalculationRequest;
    private FundTransferTransactionChargeCalculationResponse fundTransferTransactionChargeCalculationResponse;
    //private OTPDialogAfterLogin otpDialogAfterLogin;
    private MonthAdapter monthAdapter;
    private Month selectedMonth;
    private String selectedYear = "";
    private ArrayAdapter<String> stringArrayAdapter;
    private PaymentAPI paymentAPI;
    private GenerateSignatureCSRequest generateSignatureCSRequest;
    private GenerateSignatureCSResponse generateSignatureCSResponse;
    private CybersourcePayment1Request cybersourcePayment1Request;

    public String cardType;
    public String cardNumber;
    public String cardDate;
    public String month;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyber_source);
        ButterKnife.bind(this);

        init();
    }

    private void init() {

        initObjects();
        initToolbar();
        initMonth();
        initYear();
        actionListener();
    }


    private void initObjects() {
        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);

        getFundTransferTransactionCalApi = new GetFundTransferTransactionCalApi();
        topUpTransactionChargeCalculationRequest = new TopUpTransactionChargeCalculationRequest();
        paymentAPI = ZemullaApplication.getRetrofit().create(PaymentAPI.class);

        generateSignatureCSRequest = new GenerateSignatureCSRequest();
        cybersourcePayment1Request = new CybersourcePayment1Request();
    }

    private void actionListener() {
        btnProcessInitialTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Functions.isEmpty(edtAmount)) {
                    Functions.showError(CyberSourceActivity.this, "Please Enter Amount", false);
                    return;
                }

                if (Functions.isEmpty(edtcardNumber)) {
                    Functions.showError(CyberSourceActivity.this, "Please Enter Card Number", false);
                    return;
                }

                if (Functions.toStingEditText(edtcardNumber).length() < 16) {
                    Functions.showError(CyberSourceActivity.this, "Please Enter Valid Card Number", false);
                    return;
                }

                if (selectedMonth.getId() == 0) {
                    Functions.showError(CyberSourceActivity.this, "Select Month", false);
                    return;
                }


                if (selectedYear.equalsIgnoreCase("year")) {
                    Functions.showError(CyberSourceActivity.this, "Select Year", false);
                    return;
                }
                if (Functions.isFabAnimate(btnProcessInitialTransaction)) {
                    return;
                }

                if (rdVisa.isChecked()) {
                    cardType = "001";
                } else {
                    cardType = "002";
                }

                if (selectedMonth.getId() < 10) {
                    month = "0" + selectedMonth.getId();
                } else {
                    month = String.valueOf(selectedMonth.getId());
                }
                cardDate = String.format("%s-%s", month, selectedYear);
                cardNumber = Functions.toStingEditText(edtcardNumber);
                calculateAmount();

            }
        });

        resetFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.reverse();
                frameRootTopup.startAnimation(animation);
            }
        });

        confirmFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Functions.isFabAnimate(confirmFab)) {
                    return;
                }
                confirmFab.showProgress(true);
                generateSignatureCSRequest.setAmount(String.valueOf(fundTransferTransactionChargeCalculationResponse.getAmount()));
                generateSignatureCSRequest.setUserID(4);
                Call<GenerateSignatureCSResponse> generateSignatureCSResponseCall = paymentAPI.generateSignatureCS(generateSignatureCSRequest);
                generateSignatureCSResponseCall.enqueue(generateSignatureCSResponseCallback);

            }
        });
    }


    Callback<GenerateSignatureCSResponse> generateSignatureCSResponseCallback = new Callback<GenerateSignatureCSResponse>() {
        @Override
        public void onResponse(Call<GenerateSignatureCSResponse> call, Response<GenerateSignatureCSResponse> response) {
            try {
                if (response.isSuccessful()) {
                    if (response.body().getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        generateSignatureCSResponse = response.body();

                        cybersourcePayment1Request.setUserID(PrefUtils.getUserID(CyberSourceActivity.this));
                        cybersourcePayment1Request.setAmount(fundTransferTransactionChargeCalculationResponse.getAmount());
                        cybersourcePayment1Request.setServiceDetailsID(ServiceDetails.CyberSource.getId());
                        cybersourcePayment1Request.setTotalCharge(fundTransferTransactionChargeCalculationResponse.getTotalCharge());
                        cybersourcePayment1Request.setTotalPayableAmount(fundTransferTransactionChargeCalculationResponse.getTotalPayableAmount());

                        Call<CybersourcePayment1Response> cybersourcePayment1ResponseCall = paymentAPI.cybersourcePayment1Response(cybersourcePayment1Request);
                        cybersourcePayment1ResponseCall.enqueue(cybersourcePayment1ResponseCallback);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenerateSignatureCSResponse> call, Throwable t) {
            confirmFab.showProgress(false);
            RetrofitErrorHelper.showErrorMsg(t, CyberSourceActivity.this);
        }
    };


    Callback<CybersourcePayment1Response> cybersourcePayment1ResponseCallback = new Callback<CybersourcePayment1Response>() {
        @Override
        public void onResponse(Call<CybersourcePayment1Response> call, Response<CybersourcePayment1Response> response) {
            confirmFab.showProgress(false);
            if (response.isSuccessful()) {
                if (response.body().getResponse().getResponseCode() == AppConstant.ResponseSuccess) {

                    Intent intent = new Intent(CyberSourceActivity.this, CyberSourceWebViewActivity.class);
                    intent.putExtra(Intent.EXTRA_REFERRER, generateSignatureCSResponse);
                    intent.putExtra(IntentConstant.EXTRA_AMOUNT, String.valueOf(fundTransferTransactionChargeCalculationResponse.getTotalPayableAmount()));
                    intent.putExtra(IntentConstant.EXTRA_CARD_NUMER, cardNumber);
                    intent.putExtra(IntentConstant.EXTRA_CARD_DATE, cardDate);
                    intent.putExtra(IntentConstant.EXTRA_CARD_TYPE, cardType);
                    intent.putExtra(Intent.EXTRA_REFERRER, generateSignatureCSResponse);
                    Functions.fireIntent(CyberSourceActivity.this, intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }
            }
        }

        @Override
        public void onFailure(Call<CybersourcePayment1Response> call, Throwable t) {
            confirmFab.showProgress(false);
            RetrofitErrorHelper.showErrorMsg(t, CyberSourceActivity.this);
        }
    };

    private void calculateAmount() {
        btnProcessInitialTransaction.showProgress(true);
        try {
            topUpTransactionChargeCalculationRequest.setAmount(Double.parseDouble(Functions.toStingEditText(edtAmount)));
            topUpTransactionChargeCalculationRequest.setServiceDetailsID(ServiceDetails.CyberSource.getId());
            getFundTransferTransactionCalApi.getTopupCharge(topUpTransactionChargeCalculationRequest, fundTransferTransactionChargeCalculationResponseAPIListener);
            showProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    APIListener<FundTransferTransactionChargeCalculationResponse> fundTransferTransactionChargeCalculationResponseAPIListener = new APIListener<FundTransferTransactionChargeCalculationResponse>() {
        @Override
        public void onResponse(Response<FundTransferTransactionChargeCalculationResponse> response) {

            hidProgressDialog();
            animation = new FlipAnimation(lineatInitialViewTopup, linearTrnsViewTopup);
            btnProcessInitialTransaction.showProgress(false);

            try {
                if (response.isSuccessful() && response.body() != null) {
                    fundTransferTransactionChargeCalculationResponse = response.body();
                    if (fundTransferTransactionChargeCalculationResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        txtPayableAmount.setText(String.format("%s %.2f", AppConstant.ZMW, fundTransferTransactionChargeCalculationResponse.getTotalPayableAmount()));
                        txtTopupAmount.setText(String.format("Topup Amount : %s %.2f", AppConstant.ZMW, fundTransferTransactionChargeCalculationResponse.getAmount()));
                        txtTransactionCharge.setText(String.format("Transaction Charge : %s %.2f", AppConstant.ZMW, fundTransferTransactionChargeCalculationResponse.getTotalCharge()));

                        frameRootTopup.startAnimation(animation);

                    } else {
                        Functions.showError(CyberSourceActivity.this, fundTransferTransactionChargeCalculationResponse.getResponse().getResponseMsg(), false);
                    }
                } else {
                    Functions.showError(CyberSourceActivity.this, "Something went wrong. Please try again.", false);
                }
            } catch (Exception e) {
                Functions.showError(CyberSourceActivity.this, "Something went wrong. Please try again.", false);
            }

        }

        @Override
        public void onFailure(Call<FundTransferTransactionChargeCalculationResponse> call, Throwable t) {
            hidProgressDialog();
        }
    };


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

    private void checkVisibility() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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

    private void initMonth() {
        monthAdapter = new MonthAdapter(this);
        monthAdapter.addAll(Month.monthList());
        selectMonthSpinner.setAdapter(monthAdapter);
        selectMonthSpinner.setOnItemSelectedListener(onItemSelectedListener);

    }

    private void initYear() {
        List<String> strings = new ArrayList<>();
        int year = 2016;
        int totalYear = 30;
        strings.add("Year");
        strings.add("2016");
        for (int i = 0; i < totalYear; i++) {
            strings.add(String.valueOf(year = year + 1));
        }
        stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strings);
        yearSpinner.setAdapter(stringArrayAdapter);
        yearSpinner.setOnItemSelectedListener(onYearSelected);
    }


    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedMonth = monthAdapter.getItem(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    AdapterView.OnItemSelectedListener onYearSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedYear = stringArrayAdapter.getItem(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


}
