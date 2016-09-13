package com.zemulla.android.app.emarket.electricity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;
import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.kazang.KazangElectricityAPI;
import com.zemulla.android.app.api.kazang.KazangTestElectricityAPI;
import com.zemulla.android.app.api.payment.GetFundTransferTransactionCalApi;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.DecimalDigitsInputFilter;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.KazangProductID;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.helper.ServiceDetails;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.kazang.kazangtestelectricity.KazangElectricityRequest;
import com.zemulla.android.app.model.kazang.kazangtestelectricity.KazangElectricityResponse;
import com.zemulla.android.app.model.kazang.kazangtestelectricity.KazangTestElectricityResponse;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.FundTransferTransactionChargeCalculationResponse;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationRequest;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.widgets.TfEditText;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mbanje.kurt.fabbutton.FabButton;
import retrofit2.Call;
import retrofit2.Response;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ElectricityActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTopupWayName)
    TextView txtTopupWayName;
    @BindView(R.id.edtMeterNumber)
    TfEditText edtMeterNumber;
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
    private GetWalletDetailResponse walletResponse;
    private LoginResponse loginResponse;
    private Unbinder unbinder;
    private FlipAnimation animation;
    private GetFundTransferTransactionCalApi getFundTransferTransactionCalApi;
    private TopUpTransactionChargeCalculationRequest topUpTransactionChargeCalculationRequest;
    private FundTransferTransactionChargeCalculationResponse fundTransferTransactionChargeCalculationResponse;
    private Subscription meterNumberSubscription;
    private KazangTestElectricityAPI kazangTestElectricityAPI;
    private ProgressDialog progressDialog;
    private KazangElectricityAPI kazangElectricityAPI;
    private KazangElectricityRequest kazangElectricityRequest;
    private boolean isValidMeterNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity);
        unbinder = ButterKnife.bind(this);
        edtAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter()});
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void init() {


        initObjects();
        initToolBar();
        actionListener();

        meterNumberSubscription = RxTextView.afterTextChangeEvents(edtMeterNumber)
                .skip(1)
                .debounce(AppConstant.DebounceTime, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {

                        String meterNumber = textViewAfterTextChangeEvent.view().getText().toString();
                        checkForValidMeterNumber(meterNumber);

                        Log.d("edtEmail", textViewAfterTextChangeEvent.view().getText().toString());
                    }
                });


    }

    private void checkForValidMeterNumber(String meterNumber) {

        if (!TextUtils.isEmpty(meterNumber) && meterNumber.trim().length() > 10) {
            showProgressDialog();
            kazangTestElectricityAPI.kazangDirectRechargeAPI(meterNumber, kazangTestElectricityResponseAPIListener);
        }


    }

    private void initObjects() {
        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);
        getFundTransferTransactionCalApi = new GetFundTransferTransactionCalApi();
        topUpTransactionChargeCalculationRequest = new TopUpTransactionChargeCalculationRequest();
        kazangTestElectricityAPI = new KazangTestElectricityAPI();
        kazangElectricityRequest = new KazangElectricityRequest();
        kazangElectricityAPI = new KazangElectricityAPI();
    }

    private void actionListener() {
        initFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Functions.isEmpty(edtAmount)) {
                    Functions.showError(ElectricityActivity.this, "Please Enter Amount", false);
                    return;
                }
                if (Double.parseDouble(Functions.toStingEditText(edtAmount)) > walletResponse.getEffectiveBalance()) {
                    Functions.showError(ElectricityActivity.this, "Enter Valid Amount", false);
                    return;
                }
                if (Functions.isEmpty(edtMeterNumber)) {
                    Functions.showError(ElectricityActivity.this, "Please Enter Meter Number", false);
                    return;
                }
                if (!isValidMeterNumber) {
                    Functions.showError(ElectricityActivity.this, "Enter Valid Meter Number", false);
                    return;
                } else {
                    if (Functions.isFabAnimate(initFab)) {
                        return;
                    }
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
                if (Functions.isFabAnimate(confirmFab)) {
                    return;
                }
                getMeterInfo();
            }
        });
    }

    private void calculateAmount() {
        initFab.showProgress(true);
        topUpTransactionChargeCalculationRequest.setAmount(Double.parseDouble(Functions.toStingEditText(edtAmount)));
        topUpTransactionChargeCalculationRequest.setServiceDetailsID(ServiceDetails.KazangDirectRecharge.getId());
        getFundTransferTransactionCalApi.getTopupCharge(topUpTransactionChargeCalculationRequest, fundTransferTransactionChargeCalculationResponseAPIListener);

    }

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
                        txtTopupAmount.setText(String.format("Billing Amount : %s %.2f", AppConstant.ZMW, fundTransferTransactionChargeCalculationResponse.getAmount()));
                        txtTransactionCharge.setText(String.format("Transaction Charge : %s %.2f", AppConstant.ZMW, fundTransferTransactionChargeCalculationResponse.getTotalCharge()));
                        frameRootTopup.startAnimation(animation);

                    } else {
                        Functions.showError(ElectricityActivity.this, fundTransferTransactionChargeCalculationResponse.getResponse().getResponseMsg(), false);
                    }
                } else {
                    Functions.showError(ElectricityActivity.this, "Something went wrong. Please try again.", false);
                }
            } catch (Exception e) {
                Functions.showError(ElectricityActivity.this, "Something went wrong. Please try again.", false);
            }

        }

        @Override
        public void onFailure(Call<FundTransferTransactionChargeCalculationResponse> call, Throwable t) {

        }
    };

    private void initToolBar() {
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
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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

    APIListener<KazangTestElectricityResponse> kazangTestElectricityResponseAPIListener = new APIListener<KazangTestElectricityResponse>() {
        @Override
        public void onResponse(Response<KazangTestElectricityResponse> response) {

            hidProgressDialog();
            if (response.isSuccessful()) {
                KazangTestElectricityResponse kazangTestElectricityResponse = response.body();
                if (kazangTestElectricityResponse.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                    if (kazangTestElectricityResponse.getResponse_code().equalsIgnoreCase(String.valueOf(AppConstant.ResponseSuccess))) {
                        Functions.showError(ElectricityActivity.this, response.body().getResponse_message(), false);
                        isValidMeterNumber = false;
                    } else {
                        isValidMeterNumber = true;
                    }
                } else {
                    isValidMeterNumber = false;
                }
            }
        }

        @Override
        public void onFailure(Call<KazangTestElectricityResponse> call, Throwable t) {
            hidProgressDialog();
        }
    };

    private void getMeterInfo() {
        // call api for get meter info from meter number
        confirmFab.showProgress(true);
        //MeterNumber,TotalCharge,Amount,TotalPayableAmount,UserID,UserID,product_id
        kazangElectricityRequest.setMeterNumber(Functions.toStingEditText(edtMeterNumber));
        kazangElectricityRequest.setTotalCharge(fundTransferTransactionChargeCalculationResponse.getTotalCharge());
        kazangElectricityRequest.setAmount(fundTransferTransactionChargeCalculationResponse.getAmount());
        kazangElectricityRequest.setTotalPayableAmount(fundTransferTransactionChargeCalculationResponse.getTotalPayableAmount());
        kazangElectricityRequest.setUserID(PrefUtils.getUserID(this));
        kazangElectricityRequest.setServiceDetailID(ServiceDetails.KazangElectricity.getId());
        kazangElectricityRequest.setProduct_id(KazangProductID.BuyElectricity.toString());
        kazangElectricityAPI.kazangDirectRechargeAPI(kazangElectricityRequest, kazangElectricityResponseAPIListener);
    }

    APIListener<KazangElectricityResponse> kazangElectricityResponseAPIListener = new APIListener<KazangElectricityResponse>() {
        @Override
        public void onResponse(Response<KazangElectricityResponse> response) {

            if (response.isSuccessful()) {
                confirmFab.showProgress(false);
                Intent intent = new Intent(ElectricityActivity.this, ConfirmationActivity.class);
                intent.putExtra(Intent.EXTRA_REFERRER, kazangElectricityRequest);
                intent.putExtra(Intent.EXTRA_REFERRER_NAME, response.body());
                Functions.fireIntent(ElectricityActivity.this, intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }

        @Override
        public void onFailure(Call<KazangElectricityResponse> call, Throwable t) {
            confirmFab.hideProgressOnComplete(true);
            confirmFab.onProgressCompleted();
            Log.d("test", "test", t);
        }
    };
}
