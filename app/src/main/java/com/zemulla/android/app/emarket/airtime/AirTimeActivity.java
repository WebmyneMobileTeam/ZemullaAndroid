package com.zemulla.android.app.emarket.airtime;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.kazang.GetKazangProductPlanAPI;
import com.zemulla.android.app.api.kazang.GetKazangProductProviderAPI;
import com.zemulla.android.app.api.kazang.KazangAirTimeAPI;
import com.zemulla.android.app.api.payment.GetFundTransferTransactionCalApi;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.KazangProductProvider;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.helper.ServiceDetails;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.kazang.getkazangproductplan.GetKazangProductPlanRequest;
import com.zemulla.android.app.model.kazang.getkazangproductplan.GetKazangProductPlanResponse;
import com.zemulla.android.app.model.kazang.getkazangproductplan.ProductPlan;
import com.zemulla.android.app.model.kazang.getkazangproductprovider.GetKazangProductProviderResponse;
import com.zemulla.android.app.model.kazang.kazangairtime.KazangAirtimeRequest;
import com.zemulla.android.app.model.kazang.kazangairtime.KazangAirtimeResponse;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.FundTransferTransactionChargeCalculationResponse;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationRequest;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.widgets.OTPDialogAfterLogin;
import com.zemulla.android.app.widgets.TfEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mbanje.kurt.fabbutton.FabButton;
import retrofit2.Call;
import retrofit2.Response;

public class AirTimeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTopupWayName)
    TextView txtTopupWayName;
    @BindView(R.id.spinnerProvider)
    AppCompatSpinner spinnerProvider;
    @BindView(R.id.spinnerPlan)
    AppCompatSpinner spinnerPlan;
    @BindView(R.id.paypalFabInit)
    FabButton paypalFabInit;
    @BindView(R.id.lineatInitialViewTopup)
    LinearLayout lineatInitialViewTopup;
    @BindView(R.id.paypalResetFab)
    FabButton paypalResetFab;
    @BindView(R.id.paypalConfirmFab)
    FabButton paypalConfirmFab;
    @BindView(R.id.linearTrnsViewTopup)
    LinearLayout linearTrnsViewTopup;
    @BindView(R.id.frameRootTopup)
    FrameLayout frameRootTopup;
    @BindView(R.id.activity_topup_initial_transaction)
    LinearLayout activityTopupInitialTransaction;
    @BindView(R.id.rechargeAmount)
    TfEditText rechargeAmount;
    @BindView(R.id.txtTopupAmount)
    TextView txtTopupAmount;
    @BindView(R.id.txtPayableAmount)
    TextView txtPayableAmount;
    @BindView(R.id.txtTransactionCharge)
    TextView txtTransactionCharge;
    private Unbinder unbinder;

    private FlipAnimation animation;
    private ProgressDialog progressDialog;
    private GetWalletDetailResponse walletResponse;
    private LoginResponse loginResponse;
    private GetKazangProductProviderAPI getKazangProductProviderAPI;
    private GetKazangProductPlanRequest getKazangProductPlanRequest;
    private GetKazangProductPlanAPI getKazangProductPlanAPI;
    private KazangProductProviderAdapter kazangProductProviderAdapter;
    private KazangProductPlanAdapter kazangProductPlanAdapter;
    private String selectedProvider = "";
    private ProductPlan mSelectedProductPlan;
    private GetFundTransferTransactionCalApi getFundTransferTransactionCalApi;
    private TopUpTransactionChargeCalculationRequest topUpTransactionChargeCalculationRequest;
    private FundTransferTransactionChargeCalculationResponse fundTransferTransactionChargeCalculationResponse;
    private OTPDialogAfterLogin otpDialogAfterLogin;
    private KazangAirtimeRequest kazangAirtimeRequest;
    private KazangAirTimeAPI kazangAirTimeAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_time);
        unbinder = ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void init() {

        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);

        getKazangProductProviderAPI = new GetKazangProductProviderAPI();
        getKazangProductPlanAPI = new GetKazangProductPlanAPI();

        kazangProductProviderAdapter = new KazangProductProviderAdapter(this);

        getKazangProductPlanAPI = new GetKazangProductPlanAPI();
        getKazangProductPlanRequest = new GetKazangProductPlanRequest();
        kazangProductPlanAdapter = new KazangProductPlanAdapter(this);
        spinnerPlan.setAdapter(kazangProductPlanAdapter);
        spinnerPlan.setOnItemSelectedListener(onPlanOnItemSelectedListener);


        getFundTransferTransactionCalApi = new GetFundTransferTransactionCalApi();
        topUpTransactionChargeCalculationRequest = new TopUpTransactionChargeCalculationRequest();

        otpDialogAfterLogin = new OTPDialogAfterLogin(new MaterialDialog.Builder(AirTimeActivity.this));
        otpDialogAfterLogin.setOnSubmitListener(onSubmitListener);
        kazangAirtimeRequest = new KazangAirtimeRequest();
        kazangAirTimeAPI = new KazangAirTimeAPI();
        initToolbar();
        fetchSpinnerData();
        actionListener();
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

    private void fetchSpinnerData() {
        spinnerProvider.setAdapter(kazangProductProviderAdapter);
        showProgressDialog();
        getKazangProductProviderAPI.kazangProductProvider(KazangProductProvider.AirTime.toString(), getKazangProductProviderResponseAPIListener);
        spinnerProvider.setOnItemSelectedListener(onProviderOnItemSelectedListener);
    }

    AdapterView.OnItemSelectedListener onProviderOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedProvider = kazangProductProviderAdapter.getItem(position);
            if (kazangProductProviderAdapter.getItem(position).equalsIgnoreCase(getString(R.string.select_provider_prompt))) {
                kazangProductPlanAdapter.clear();
                kazangProductPlanAdapter.add(new ProductPlan());

            } else {
                showProgressDialog();
                fetchPlanData(kazangProductProviderAdapter.getItem(position));
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    AdapterView.OnItemSelectedListener onPlanOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            try {
                mSelectedProductPlan = kazangProductPlanAdapter.getItem(position);
                rechargeAmount.setText(mSelectedProductPlan.getProduct_value());
            } catch (Exception e) {
                e.printStackTrace();
            }

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


    OTPDialogAfterLogin.OnSubmitListener onSubmitListener = new OTPDialogAfterLogin.OnSubmitListener() {
        @Override
        public void onSubmit(String OTP) {
            airTimeCalculation(OTP);

        }

        @Override
        public void OTPReceived() {
            hidProgressDialog();
        }
    };

    private void airTimeCalculation(String otp) {

        kazangAirtimeRequest.setAmount(fundTransferTransactionChargeCalculationResponse.getAmount());
        kazangAirtimeRequest.setServiceDetailID(ServiceDetails.AirtimeByVoucher.getId());
        kazangAirtimeRequest.setTotalCharge(fundTransferTransactionChargeCalculationResponse.getTotalCharge());
        kazangAirtimeRequest.setTotalPayableAmount(fundTransferTransactionChargeCalculationResponse.getTotalPayableAmount());
        kazangAirtimeRequest.setUserID(PrefUtils.getUserID(this));
        kazangAirtimeRequest.setVerificationCode(otp);
        kazangAirtimeRequest.setProduct_id(mSelectedProductPlan.getProduct_id());
        kazangAirTimeAPI.kazangAirTime(kazangAirtimeRequest, airtimeResponseAPIListener);
        showProgressDialog();
    }

    APIListener<KazangAirtimeResponse> airtimeResponseAPIListener = new APIListener<KazangAirtimeResponse>() {
        @Override
        public void onResponse(Response<KazangAirtimeResponse> response) {
            hidProgressDialog();
            if (response.isSuccessful()) {
                if (response.body().getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                    otpDialogAfterLogin.dismiss();
                    Functions.showSuccessMsg(AirTimeActivity.this, response.body().getResponse().getResponseMsg(), true);
                } else {
                    Functions.showError(AirTimeActivity.this, response.body().getResponse().getResponseMsg(), false);
                }
            }

        }

        @Override
        public void onFailure(Call<KazangAirtimeResponse> call, Throwable t) {
            hidProgressDialog();
        }
    };

    private void fetchPlanData(String item) {

        getKazangProductPlanRequest.setProduct_category(KazangProductProvider.AirTime.toString());
        getKazangProductPlanRequest.setProduct_provider(item);
        getKazangProductPlanAPI.getKazangProductPlan(getKazangProductPlanRequest, kazangProductPlanResponseAPIListener);
    }

    APIListener<GetKazangProductPlanResponse> kazangProductPlanResponseAPIListener = new APIListener<GetKazangProductPlanResponse>() {
        @Override
        public void onResponse(Response<GetKazangProductPlanResponse> response) {

            hidProgressDialog();
            try {
                if (response.isSuccessful()) {
                    kazangProductPlanAdapter.clear();
                    mSelectedProductPlan = new ProductPlan();
                    kazangProductPlanAdapter.add(mSelectedProductPlan);
                    kazangProductPlanAdapter.addAll(response.body().getResponseData().getData());
                    spinnerPlan.setSelection(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetKazangProductPlanResponse> call, Throwable t) {
            hidProgressDialog();
        }
    };

    private void actionListener() {
        paypalFabInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedProvider.equalsIgnoreCase(getString(R.string.select_provider_prompt))) {
                    Functions.showError(AirTimeActivity.this, "Please Select Provider", false);
                } else if (mSelectedProductPlan.getProduct_id().equalsIgnoreCase("0")) {
                    Functions.showError(AirTimeActivity.this, "Please Select Plan", false);
                } else {
                    calculateAmount();
                }
            }
        });

        paypalResetFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.reverse();
                frameRootTopup.startAnimation(animation);
            }
        });

        paypalConfirmFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showProgressDialog();
                otpDialogAfterLogin.generateOPT();

            }
        });
    }

    private void calculateAmount() {

        paypalFabInit.showProgress(true);
        topUpTransactionChargeCalculationRequest.setAmount(Double.parseDouble(Functions.toStingEditText(rechargeAmount)));
        topUpTransactionChargeCalculationRequest.setServiceDetailsID(ServiceDetails.AirtimeByVoucher.getId());
        getFundTransferTransactionCalApi.getTopupCharge(topUpTransactionChargeCalculationRequest, fundTransferTransactionChargeCalculationResponseAPIListener);
        // Toast.makeText(this, "" + serviceID, Toast.LENGTH_LONG).show();


    }

    APIListener<FundTransferTransactionChargeCalculationResponse> fundTransferTransactionChargeCalculationResponseAPIListener = new APIListener<FundTransferTransactionChargeCalculationResponse>() {
        @Override
        public void onResponse(Response<FundTransferTransactionChargeCalculationResponse> response) {

            animation = new FlipAnimation(lineatInitialViewTopup, linearTrnsViewTopup);
            paypalFabInit.showProgress(false);

            try {
                if (response.isSuccessful() && response.body() != null) {
                    fundTransferTransactionChargeCalculationResponse = response.body();
                    if (fundTransferTransactionChargeCalculationResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        txtPayableAmount.setText(String.format("%s %.2f", AppConstant.ZMW, fundTransferTransactionChargeCalculationResponse.getTotalPayableAmount()));
                        txtTopupAmount.setText(String.format("Topup Amount : %s %.2f", AppConstant.ZMW, fundTransferTransactionChargeCalculationResponse.getAmount()));
                        txtTransactionCharge.setText(String.format("Transaction Charge : %s %.2f", AppConstant.ZMW, fundTransferTransactionChargeCalculationResponse.getTotalCharge()));

                        frameRootTopup.startAnimation(animation);

                    } else {
                        Functions.showError(AirTimeActivity.this, fundTransferTransactionChargeCalculationResponse.getResponse().getResponseMsg(), false);
                    }
                } else {
                    Functions.showError(AirTimeActivity.this, "Something went wrong. Please try again.", false);
                }
            } catch (Exception e) {
                Functions.showError(AirTimeActivity.this, "Something went wrong. Please try again.", false);
            }

        }

        @Override
        public void onFailure(Call<FundTransferTransactionChargeCalculationResponse> call, Throwable t) {

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
}
