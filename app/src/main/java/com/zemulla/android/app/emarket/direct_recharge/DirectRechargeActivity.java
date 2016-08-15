package com.zemulla.android.app.emarket.direct_recharge;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.kazang.GetKazangProductProviderAPI;
import com.zemulla.android.app.api.kazang.KazangDirectRechargeAPI;
import com.zemulla.android.app.api.payment.GetFundTransferTransactionCalApi;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.emarket.airtime.KazangProductProviderAdapter;
import com.zemulla.android.app.helper.DecimalDigitsInputFilter;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.KazangProductProvider;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.helper.ServiceDetails;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.kazang.getkazangproductprovider.GetKazangProductProviderResponse;
import com.zemulla.android.app.model.kazang.kazangdirectrecharge.KazangDirectRechargeRequest;
import com.zemulla.android.app.model.kazang.kazangdirectrecharge.KazangDirectRechargeResponse;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.FundTransferTransactionChargeCalculationResponse;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationRequest;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.widgets.OTPDialogAfterLogin;
import com.zemulla.android.app.widgets.TfEditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mbanje.kurt.fabbutton.FabButton;
import retrofit2.Call;
import retrofit2.Response;

public class DirectRechargeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTopupWayName)
    TextView txtTopupWayName;
    @BindView(R.id.edtNumber)
    EditText edtNumber;
    @BindView(R.id.spinnerProduct)
    AppCompatSpinner spinnerProduct;
    @BindView(R.id.edtAmount)
    TfEditText edtAmount;
    @BindView(R.id.initFab)
    FabButton initFab;
    @BindView(R.id.lineatInitialViewTopup)
    LinearLayout lineatInitialViewTopup;
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
    @BindView(R.id.txtTopupAmount)
    TextView txtTopupAmount;
    @BindView(R.id.txtPayableAmount)
    TextView txtPayableAmount;
    @BindView(R.id.txtTransactionCharge)
    TextView txtTransactionCharge;
    private FlipAnimation animation;
    Unbinder unbinder;
    private ArrayList<String> products;
    private int productPosition = 0;
    private KazangProductProviderAdapter kazangProductProviderAdapter;
    private GetKazangProductProviderAPI getKazangProductProviderAPI;
    private ProgressDialog progressDialog;
    private String selectedProvider = "";
    private OTPDialogAfterLogin otpDialogAfterLogin;
    private GetFundTransferTransactionCalApi getFundTransferTransactionCalApi;
    private TopUpTransactionChargeCalculationRequest topUpTransactionChargeCalculationRequest;
    private FundTransferTransactionChargeCalculationResponse fundTransferTransactionChargeCalculationResponse;
    private GetWalletDetailResponse walletResponse;
    private LoginResponse loginResponse;
    private KazangDirectRechargeAPI kazangDirectRechargeAPI;
    private KazangDirectRechargeRequest kazangDirectRechargeRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_recharge);
        unbinder = ButterKnife.bind(this);
        edtAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter()});
        init();
    }

    private void init() {

        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);
        kazangProductProviderAdapter = new KazangProductProviderAdapter(this);
        getKazangProductProviderAPI = new GetKazangProductProviderAPI();
        spinnerProduct.setAdapter(kazangProductProviderAdapter);
        showProgressDialog();
        getKazangProductProviderAPI.kazangProductProvider(KazangProductProvider.DirectRecharge.toString(), getKazangProductProviderResponseAPIListener);
        spinnerProduct.setOnItemSelectedListener(onProviderOnItemSelectedListener);
        otpDialogAfterLogin = new OTPDialogAfterLogin(new MaterialDialog.Builder(DirectRechargeActivity.this));
        otpDialogAfterLogin.setOnSubmitListener(onSubmitListener);
        getFundTransferTransactionCalApi = new GetFundTransferTransactionCalApi();
        topUpTransactionChargeCalculationRequest = new TopUpTransactionChargeCalculationRequest();
        kazangDirectRechargeRequest = new KazangDirectRechargeRequest();
        kazangDirectRechargeAPI = new KazangDirectRechargeAPI();
        initToolbar();
        initProducts();
        actionListener();
    }

    private void initProducts() {
        products = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            products.add("Product " + i);
        }
    }

    private void actionListener() {
        initFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Functions.isEmpty(edtNumber)) {
                    Functions.showError(DirectRechargeActivity.this, "Enter Number", false);
                } else if (Functions.getLength(edtNumber) < 10) {
                    Functions.showError(DirectRechargeActivity.this, "Enter Number", false);
                } else if (selectedProvider.equalsIgnoreCase(getString(R.string.select_provider_prompt))) {
                    Functions.showError(DirectRechargeActivity.this, "Select Product", false);
                } else if (Functions.isEmpty(edtAmount)) {
                    Functions.showError(DirectRechargeActivity.this, "Enter Amount", false);
                } else if (Double.parseDouble(Functions.toStingEditText(edtAmount)) > walletResponse.getEffectiveBalance()) {
                    Functions.showError(DirectRechargeActivity.this, "Enter Valid Amount", false);
                } else {
                    calculateAmount();
                }
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
                showProgressDialog();
                otpDialogAfterLogin.generateOPT();

            }
        });
    }

    private void calculateAmount() {
        initFab.showProgress(true);
        topUpTransactionChargeCalculationRequest.setAmount(Double.parseDouble(Functions.toStingEditText(edtAmount)));
        topUpTransactionChargeCalculationRequest.setServiceDetailsID(ServiceDetails.KazangDirectRecharge.getId());
        getFundTransferTransactionCalApi.getTopupCharge(topUpTransactionChargeCalculationRequest, fundTransferTransactionChargeCalculationResponseAPIListener);

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

    private void checkVisibility() {
        if (lineatInitialViewTopup.isShown()) {
            finish();
        } else {
            animation.reverse();
            frameRootTopup.startAnimation(animation);
        }
    }

    @Override
    public void onBackPressed() {
        checkVisibility();
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


    AdapterView.OnItemSelectedListener onProviderOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedProvider = kazangProductProviderAdapter.getItem(position);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    APIListener<GetKazangProductProviderResponse> getKazangProductProviderResponseAPIListener = new APIListener<GetKazangProductProviderResponse>() {
        @Override
        public void onResponse(Response<GetKazangProductProviderResponse> response) {

            hidProgressDialog();
            try {
                if (response.isSuccessful()) {
                    kazangProductProviderAdapter.clear();
                    kazangProductProviderAdapter.add(getString(R.string.select_provider_prompt));
                    kazangProductProviderAdapter.addAll(response.body().getProductProvider());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetKazangProductProviderResponse> call, Throwable t) {
            hidProgressDialog();
        }
    };


    APIListener<FundTransferTransactionChargeCalculationResponse> fundTransferTransactionChargeCalculationResponseAPIListener = new APIListener<FundTransferTransactionChargeCalculationResponse>() {
        @Override
        public void onResponse(Response<FundTransferTransactionChargeCalculationResponse> response) {

            animation = new FlipAnimation(lineatInitialViewTopup, linearTrnsViewTopup);
            initFab.showProgress(false);
            try {
                if (response.isSuccessful() && response.body() != null) {
                    fundTransferTransactionChargeCalculationResponse = response.body();
                    if (fundTransferTransactionChargeCalculationResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        txtPayableAmount.setText(String.format("%s %.2f", AppConstant.ZMW, fundTransferTransactionChargeCalculationResponse.getTotalPayableAmount()));
                        txtTopupAmount.setText(String.format("Topup Amount : %s %.2f", AppConstant.ZMW, fundTransferTransactionChargeCalculationResponse.getAmount()));
                        txtTransactionCharge.setText(String.format("Transaction Charge : %s %.2f", AppConstant.ZMW, fundTransferTransactionChargeCalculationResponse.getTotalCharge()));

                        frameRootTopup.startAnimation(animation);

                    } else {
                        Functions.showError(DirectRechargeActivity.this, fundTransferTransactionChargeCalculationResponse.getResponse().getResponseMsg(), false);
                    }
                } else {
                    Functions.showError(DirectRechargeActivity.this, "Something went wrong. Please try again.", false);
                }
            } catch (Exception e) {
                Functions.showError(DirectRechargeActivity.this, "Something went wrong. Please try again.", false);
            }

        }

        @Override
        public void onFailure(Call<FundTransferTransactionChargeCalculationResponse> call, Throwable t) {

        }
    };


    OTPDialogAfterLogin.OnSubmitListener onSubmitListener = new OTPDialogAfterLogin.OnSubmitListener() {
        @Override
        public void onSubmit(String OTP) {
            directRecharge(OTP);

        }

        @Override
        public void OTPReceived() {
            hidProgressDialog();
        }
    };

    private void directRecharge(String OTP) {

        kazangDirectRechargeRequest.setAmount(fundTransferTransactionChargeCalculationResponse.getAmount());
        kazangDirectRechargeRequest.setServiceDetailID(ServiceDetails.KazangDirectRecharge.getId());
        kazangDirectRechargeRequest.setTotalCharge(fundTransferTransactionChargeCalculationResponse.getTotalCharge());
        kazangDirectRechargeRequest.setTotalPayableAmount(fundTransferTransactionChargeCalculationResponse.getTotalPayableAmount());
        kazangDirectRechargeRequest.setUserID(PrefUtils.getUserID(this));
        kazangDirectRechargeRequest.setVerificationCode(OTP);
        kazangDirectRechargeRequest.setMsisdn("");
        kazangDirectRechargeRequest.setProduct_id(selectedProvider);
        kazangDirectRechargeAPI.kazangDirectRechargeAPI(kazangDirectRechargeRequest, kazangDirectRechargeResponseAPIListener);

        showProgressDialog();
    }

    APIListener<KazangDirectRechargeResponse> kazangDirectRechargeResponseAPIListener = new APIListener<KazangDirectRechargeResponse>() {
        @Override
        public void onResponse(Response<KazangDirectRechargeResponse> response) {
            hidProgressDialog();
            if (response.isSuccessful()) {
                if (response.body().getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                    otpDialogAfterLogin.dismiss();
                    Functions.showSuccessMsg(DirectRechargeActivity.this, response.body().getResponse().getResponseMsg(), true);
                } else {
                    Functions.showError(DirectRechargeActivity.this, response.body().getResponse().getResponseMsg(), false);
                }
            }
        }

        @Override
        public void onFailure(Call<KazangDirectRechargeResponse> call, Throwable t) {

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
