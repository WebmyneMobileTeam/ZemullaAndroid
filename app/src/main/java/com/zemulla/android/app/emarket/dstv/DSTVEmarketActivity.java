package com.zemulla.android.app.emarket.dstv;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.kazang.DSTVPayment1API;
import com.zemulla.android.app.api.kazang.DSTVPayment2API;
import com.zemulla.android.app.api.kazang.DSTVPayment3API;
import com.zemulla.android.app.api.kazang.GetProductAPIProdMasterIDWiseAPI;
import com.zemulla.android.app.api.payment.GetFundTransferTransactionCalApi;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.KazangProductID;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.helper.ServiceDetails;
import com.zemulla.android.app.home.HomeActivity;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.kazang.dstvpayment1.DSTVPayment1Request;
import com.zemulla.android.app.model.kazang.dstvpayment1.DSTVPayment1Response;
import com.zemulla.android.app.model.kazang.dstvpayment2.DSTVPayment2Request;
import com.zemulla.android.app.model.kazang.dstvpayment2.DSTVPayment2Response;
import com.zemulla.android.app.model.kazang.dstvpayment3.DSTVPayment3Request;
import com.zemulla.android.app.model.kazang.dstvpayment3.DSTVPayment3Response;
import com.zemulla.android.app.model.kazang.getproductapiprodmasteridwise.DSTVProduct;
import com.zemulla.android.app.model.kazang.getproductapiprodmasteridwise.GetProductAPIProdMasterIDWiseResponse;
import com.zemulla.android.app.model.month.Month;
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

public class DSTVEmarketActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTopupWayName)
    TextView txtTopupWayName;
    @BindView(R.id.selectProductSpinner)
    AppCompatSpinner selectProductSpinner;
    @BindView(R.id.selectMonthSpinner)
    AppCompatSpinner selectMonthSpinner;
    @BindView(R.id.initFab)
    FabButton initFab;
    @BindView(R.id.lineatInitialViewTopup)
    LinearLayout lineatInitialViewTopup;
    @BindView(R.id.txtTopupAmount)
    TextView txtTopupAmount;
    @BindView(R.id.txtPayableAmount)
    TextView txtPayableAmount;
    @BindView(R.id.txtTransactionCharge)
    TextView txtTransactionCharge;
    @BindView(R.id.linearTrnsViewTopup)
    LinearLayout linearTrnsViewTopup;
    @BindView(R.id.frameRootTopup)
    FrameLayout frameRootTopup;
    @BindView(R.id.activity_topup_initial_transaction)
    LinearLayout activityTopupInitialTransaction;
    @BindView(R.id.edtCustomerNumber)
    TfEditText edtCustomerNumber;
    @BindView(R.id.rdsmartcard)
    RadioButton rdsmartcard;
    @BindView(R.id.rdcustomer)
    RadioButton rdcustomer;
    @BindView(R.id.totalAmount)
    TextView totalAmount;
    @BindView(R.id.customerDetails)
    TextView customerDetails;
    @BindView(R.id.cancelInfo)
    FabButton cancelInfo;
    @BindView(R.id.confirmInfo)
    FabButton confirmInfo;
    @BindView(R.id.customerDetailsHolder)
    LinearLayout customerDetailsHolder;
    @BindView(R.id.resetFab)
    FabButton resetFab;
    @BindView(R.id.confirmFab)
    FabButton confirmFab;
    private GetProductAPIProdMasterIDWiseAPI getProductAPIProdMasterIDWiseAPI;

    private GetWalletDetailResponse walletResponse;
    private LoginResponse loginResponse;

    private MonthAdapter monthAdapter;
    private DSTVProductAdapter dstvProductAdapter;
    private Unbinder unbinder;
    private Month selectedMonth;
    private DSTVProduct selectedDstvProduct;
    private ProgressDialog progressDialog;

    private DSTVPayment1API dstvPayment1API;
    private DSTVPayment1Request dstvPayment1Request;
    private DSTVPayment1Response dstvPayment1Response;

    private DSTVPayment2API dstvPayment2API;
    private DSTVPayment2Request dstvPayment2Request;
    private DSTVPayment2Response dstvPayment2Response;

    private DSTVPayment3API dstvPayment3API;
    private DSTVPayment3Request dstvPayment3Request;


    private GetFundTransferTransactionCalApi getFundTransferTransactionCalApi;
    private TopUpTransactionChargeCalculationRequest topUpTransactionChargeCalculationRequest;
    private FundTransferTransactionChargeCalculationResponse fundTransferTransactionChargeCalculationResponse;
    private OTPDialogAfterLogin otpDialogAfterLogin;


    private FlipAnimation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dstvemarket);
        unbinder = ButterKnife.bind(this);

        init();
    }

    private void init() {
        initObjects();
        initToolbar();
        initMonth();
        addListener();

    }


    private void initObjects() {

        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);

        dstvProductAdapter = new DSTVProductAdapter(this);
        selectProductSpinner.setAdapter(dstvProductAdapter);
        showProgressDialog();
        getProductAPIProdMasterIDWiseAPI = new GetProductAPIProdMasterIDWiseAPI();
        getProductAPIProdMasterIDWiseAPI.kazangProductProvider("7", getKazangProductProviderResponseAPIListener);

        dstvPayment1API = new DSTVPayment1API();
        dstvPayment1Request = new DSTVPayment1Request();

        dstvPayment2API = new DSTVPayment2API();
        dstvPayment2Request = new DSTVPayment2Request();

        dstvPayment3API = new DSTVPayment3API();
        dstvPayment3Request = new DSTVPayment3Request();

        getFundTransferTransactionCalApi = new GetFundTransferTransactionCalApi();
        topUpTransactionChargeCalculationRequest = new TopUpTransactionChargeCalculationRequest();


        otpDialogAfterLogin = new OTPDialogAfterLogin(new MaterialDialog.Builder(DSTVEmarketActivity.this));
        otpDialogAfterLogin.setOnSubmitListener(onSubmitListener);
    }

    private void initMonth() {
        monthAdapter = new MonthAdapter(this);
        monthAdapter.addAll(Month.monthList());
        selectMonthSpinner.setAdapter(monthAdapter);
        selectMonthSpinner.setOnItemSelectedListener(onItemSelectedListener);
        selectProductSpinner.setOnItemSelectedListener(onProductSelection);

    }


    private void addListener() {
        initFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                callDSTV1();
            }
        });

        cancelInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.reverse();
                frameRootTopup.startAnimation(animation);
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

        confirmInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calculateCharge();
            }
        });
    }

    private void callDSTV1() {

        if (selectedDstvProduct.getProduct_id().equalsIgnoreCase("0")) {
            Functions.showError(this, "Select DSTV Product", false);
            return;
        }
        if (selectedMonth.getId() == 0) {
            Functions.showError(this, "Select Month", false);
            return;
        }
        if (Functions.isEmpty(edtCustomerNumber)) {
            Functions.showError(this, "Invalid Number", false);
            return;
        }

        initFab.showProgress(true);
        dstvPayment1Request.setUserID(PrefUtils.getUserID(this));
        dstvPayment1Request.setMonths(String.valueOf(selectedMonth.getId()));
        dstvPayment1Request.setNumber(Functions.toStingEditText(edtCustomerNumber));
        if (rdsmartcard.isChecked()) {
            dstvPayment1Request.setNumber_type("SMARTCARD");
        } else {
            dstvPayment1Request.setNumber_type("CUSTOMER");
        }
        dstvPayment1Request.setProduct_id(KazangProductID.DSTVPayment.toString());
        dstvPayment1Request.setProducts(selectedDstvProduct.getProduct_id());
        dstvPayment1API.callDSTVPayment1(dstvPayment1Request, dstvPayment1ResponseAPIListener);
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
        checkVisibility();
    }

    private void checkVisibility() {
        finish();
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

    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedMonth = monthAdapter.getItem(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    AdapterView.OnItemSelectedListener onProductSelection = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedDstvProduct = dstvProductAdapter.getItem(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    APIListener<GetProductAPIProdMasterIDWiseResponse> getKazangProductProviderResponseAPIListener = new APIListener<GetProductAPIProdMasterIDWiseResponse>() {
        @Override
        public void onResponse(Response<GetProductAPIProdMasterIDWiseResponse> response) {

            hidProgressDialog();
            try {

                if (response.isSuccessful()) {
                    dstvProductAdapter.clear();
                    dstvProductAdapter.add(new DSTVProduct("0", "Select Product"));
                    dstvProductAdapter.addAll(response.body().getResponseData().getData());
                }

            } catch (Exception e) {
            }

        }

        @Override
        public void onFailure(Call<GetProductAPIProdMasterIDWiseResponse> call, Throwable t) {
            hidProgressDialog();
        }
    };


    public void calculateCharge() {


        try {
            topUpTransactionChargeCalculationRequest.setAmount(dstvPayment1Response.getAmount());
            topUpTransactionChargeCalculationRequest.setServiceDetailsID(ServiceDetails.DSTVPayment.getId());
            getFundTransferTransactionCalApi.getTopupCharge(topUpTransactionChargeCalculationRequest, fundTransferTransactionChargeCalculationResponseAPIListener);
            showProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    APIListener<DSTVPayment1Response> dstvPayment1ResponseAPIListener = new APIListener<DSTVPayment1Response>() {
        @Override
        public void onResponse(Response<DSTVPayment1Response> response) {

            animation = new FlipAnimation(lineatInitialViewTopup, customerDetailsHolder);
            initFab.showProgress(false);
            try {
                dstvPayment1Response = response.body();
                totalAmount.setText(String.format("Total Amount : %s", String.valueOf(dstvPayment1Response.getAmount())));
                customerDetails.setText(dstvPayment1Response.getConfirmation_message());
                frameRootTopup.startAnimation(animation);
            } catch (Exception e) {

            }

        }

        @Override
        public void onFailure(Call<DSTVPayment1Response> call, Throwable t) {
            initFab.showProgress(true);
        }
    };

    APIListener<FundTransferTransactionChargeCalculationResponse> fundTransferTransactionChargeCalculationResponseAPIListener = new APIListener<FundTransferTransactionChargeCalculationResponse>() {
        @Override
        public void onResponse(Response<FundTransferTransactionChargeCalculationResponse> response) {

            hidProgressDialog();
            animation = new FlipAnimation(customerDetailsHolder, linearTrnsViewTopup);
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
                        Functions.showError(DSTVEmarketActivity.this, fundTransferTransactionChargeCalculationResponse.getResponse().getResponseMsg(), false);
                    }
                } else {
                    Functions.showError(DSTVEmarketActivity.this, "Something went wrong. Please try again.", false);
                }
            } catch (Exception e) {
                Functions.showError(DSTVEmarketActivity.this, "Something went wrong. Please try again.", false);
            }

        }

        @Override
        public void onFailure(Call<FundTransferTransactionChargeCalculationResponse> call, Throwable t) {
            hidProgressDialog();
        }
    };

    OTPDialogAfterLogin.OnSubmitListener onSubmitListener = new OTPDialogAfterLogin.OnSubmitListener() {
        @Override
        public void onSubmit(String OTP) {

            showProgressDialog();
            callDSTV2(OTP);

        }

        @Override
        public void OTPReceived() {
            hidProgressDialog();
        }
    };

    private void callDSTV2(String OTP) {

        dstvPayment2Request.setAmount(fundTransferTransactionChargeCalculationResponse.getAmount());
        dstvPayment2Request.setServiceDetailID(ServiceDetails.DSTVPayment.getId());
        dstvPayment2Request.setTotalCharge(fundTransferTransactionChargeCalculationResponse.getTotalCharge());
        dstvPayment2Request.setTotalPayableAmount(fundTransferTransactionChargeCalculationResponse.getTotalPayableAmount());
        dstvPayment2Request.setUserID(PrefUtils.getUserID(this));
        dstvPayment2Request.setVerificationCode(OTP);
        dstvPayment2Request.setZemullaTransactinoID(dstvPayment1Response.getZemullaTransactinoID());
        dstvPayment2Request.setConfirmation_number(dstvPayment1Response.getConfirmation_number());
        dstvPayment2Request.setProduct_id(KazangProductID.DSTVPayment.toString());

        dstvPayment2API.dstv2(dstvPayment2Request, dstvPayment2ResponseAPIListener);


    }

    APIListener<DSTVPayment2Response> dstvPayment2ResponseAPIListener = new APIListener<DSTVPayment2Response>() {
        @Override
        public void onResponse(Response<DSTVPayment2Response> response) {
            hidProgressDialog();
            try {
                if (response.isSuccessful()) {
                    dstvPayment2Response = response.body();
                    if (dstvPayment2Response.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        if (dstvPayment2Response.getResponse_code().equalsIgnoreCase(String.valueOf(AppConstant.ResponseFailed))) {
                            callDSTV3();
                        } else {
                            Functions.showError(DSTVEmarketActivity.this, dstvPayment2Response.getResponse_message(), false);
                        }
                    } else {
                        Functions.showError(DSTVEmarketActivity.this, dstvPayment2Response.getResponse().getResponseMsg(), false);
                    }
                }
            } catch (Exception e) {

            }

        }

        @Override
        public void onFailure(Call<DSTVPayment2Response> call, Throwable t) {
            hidProgressDialog();
        }
    };

    public void callDSTV3() {

        showProgressDialog();
        dstvPayment3Request.setUserID(PrefUtils.getUserID(this));
        dstvPayment3Request.setProduct_id(KazangProductID.DSTVPayment.toString());
        dstvPayment3Request.setConfirmation_number(dstvPayment2Response.getConfirmation_number());
        dstvPayment3Request.setZemullaTransactinoID(dstvPayment2Response.getZemullaTransactinoID());
        dstvPayment3API.dstv3(dstvPayment3Request, dstvPayment3ResponseAPIListener);
    }


    APIListener<DSTVPayment3Response> dstvPayment3ResponseAPIListener = new APIListener<DSTVPayment3Response>() {
        @Override
        public void onResponse(Response<DSTVPayment3Response> response) {

            hidProgressDialog();
            if (response.isSuccessful()) {

                DSTVPayment3Response dstvPayment3Response = response.body();
                if (dstvPayment3Response.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                    if (dstvPayment3Response.getResponse_code().equalsIgnoreCase(String.valueOf(AppConstant.ResponseFailed))) {
                        Functions.showSuccessMsg(DSTVEmarketActivity.this, dstvPayment3Response.getResponse().getResponseMsg(), true, HomeActivity.class);
                    } else {
                        Functions.showError(DSTVEmarketActivity.this, dstvPayment3Response.getResponse_message(), false);
                    }
                } else {
                    otpDialogAfterLogin.dismiss();
                    //todo remove this static response code 3
                    if (dstvPayment3Response.getResponse().getResponseCode() == 3) {
                        Functions.showError(DSTVEmarketActivity.this, dstvPayment3Response.getResponse().getResponseMsg(), true, HomeActivity.class);
                    } else {
                        Functions.showError(DSTVEmarketActivity.this, dstvPayment3Response.getResponse().getResponseMsg(), false);
                    }
                }
            }

        }

        @Override
        public void onFailure(Call<DSTVPayment3Response> call, Throwable t) {
            hidProgressDialog();
        }
    };
}

