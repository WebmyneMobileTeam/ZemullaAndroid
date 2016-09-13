package com.zemulla.android.app.topup.paypal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.payment.GetPayPal1Api;
import com.zemulla.android.app.api.payment.GetPayPal2Api;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.DecimalDigitsInputFilter;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.home.HomeActivity;
import com.zemulla.android.app.home.LogUtils;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.payment.PaypalPayment.PaypalPayment1Request;
import com.zemulla.android.app.model.payment.PaypalPayment.PaypalPayment1Response;
import com.zemulla.android.app.model.payment.PaypalPayment.PaypalPayment2Request;
import com.zemulla.android.app.model.payment.PaypalPayment.PaypalPayment2Response;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.widgets.TfEditText;
import com.zemulla.android.app.widgets.TfTextView;

import org.json.JSONException;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mbanje.kurt.fabbutton.FabButton;
import retrofit2.Call;
import retrofit2.Response;

public class PaypalActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTopupWayName)
    TfTextView txtTopupWayName;
    @BindView(R.id.edtAmount)
    TfEditText edtAmount;
    @BindView(R.id.lineatInitialViewTopup)
    LinearLayout lineatInitialViewTopup;
    @BindView(R.id.linearTrnsViewTopup)
    LinearLayout linearTrnsViewTopup;
    @BindView(R.id.frameRootTopup)
    FrameLayout frameRootTopup;
    @BindView(R.id.activity_topup_initial_transaction)
    LinearLayout activityTopupInitialTransaction;
    @BindView(R.id.paypalFabInit)
    FabButton paypalFabInit;
    @BindView(R.id.paypalResetFab)
    FabButton paypalResetFab;
    @BindView(R.id.paypalConfirmFab)
    FabButton paypalConfirmFab;
    @BindView(R.id.txtTopupAmount)
    TfTextView txtTopupAmount;
    @BindView(R.id.txtPayableAmount)
    TfTextView txtPayableAmount;
    @BindView(R.id.txtTransactionCharge)
    TfTextView txtTransactionCharge;
    @BindView(R.id.txtUSDRate)
    TfTextView txtUSDRate;
    @BindView(R.id.txtPayableUSD)
    TfTextView txtPayableUSD;

    @BindView(R.id.txtZemulaTransID)
    TfTextView txtZemulaTransID;
    @BindView(R.id.txtTransUSDRate)
    TfTextView txtTransUSDRate;
    @BindView(R.id.txtTransPayableUSD)
    TfTextView txtTransPayableUSD;
    @BindView(R.id.txtTransAmount)
    TfTextView txtTransAmount;
    @BindView(R.id.txtTransPayableAmount)
    TfTextView txtTransPayableAmount;
    @BindView(R.id.txtTransCharge)
    TfTextView txtTransCharge;
    @BindView(R.id.txtTransDate)
    TfTextView txtTransDate;
    @BindView(R.id.linearPaymentViewGroup)
    LinearLayout linearPaymentViewGroup;

    private FlipAnimation animation;
    Unbinder unbinder;
    private GetPayPal1Api payPal1Api;
    private GetPayPal2Api payPal2Api;
    private PaypalPayment1Request paypalPayment1Request;
    private PaypalPayment2Request paypalPayment2Request;
    private ProgressDialog progressDialog;
    PaypalPayment1Response payment1Response;
    private GetWalletDetailResponse walletResponse;
    private LoginResponse loginResponse;
    private PayPalConfiguration configuration;
    public static final int PAYPAL_REQUEST_CODE = 123;
    private double paymentAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);

        unbinder = ButterKnife.bind(this);

        init();
    }

    private void initPaypalConfig() {
        configuration = Functions.getPayPalConfig();
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        stopService(new Intent(this, PayPalService.class));

    }

    private void calculateAmount() {
        paypalFabInit.showProgress(true);

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                paypalFabInit.hideProgressOnComplete(true);
                paypalFabInit.onProgressCompleted();
                animation = new FlipAnimation(lineatInitialViewTopup, linearTrnsViewTopup);
                frameRootTopup.startAnimation(animation);

            }
        }.start();
    }

    private void init() {

        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);


        initPaypalConfig();

        initApi();

        initToolbar();

        edtAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter()});

        actionListener();
    }

    private void initApi() {
        payPal1Api = new GetPayPal1Api();
        payPal2Api = new GetPayPal2Api();
        paypalPayment1Request = new PaypalPayment1Request();
        paypalPayment2Request = new PaypalPayment2Request();
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

    APIListener<PaypalPayment1Response> paypalPayment1ApiListener = new APIListener<PaypalPayment1Response>() {
        @Override
        public void onResponse(Response<PaypalPayment1Response> response) {
            hideCircleProgress();

            try {
                if (response.isSuccessful() && response.body() != null) {
                    payment1Response = response.body();
                    if (payment1Response.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        txtPayableAmount.setText(String.format("%s %.2f", AppConstant.ZMW, payment1Response.getTotalPayableAmount()));
                        txtTopupAmount.setText(String.format("Topup Amount : %s %.2f", AppConstant.ZMW, payment1Response.getAmount()));
                        txtTransactionCharge.setText(String.format("Transaction Charge : %s %.2f", AppConstant.ZMW, payment1Response.getTotalCharge()));
                        txtUSDRate.setText(String.format("Conversion Rate : $1 = %s %.2f", AppConstant.ZMW, payment1Response.getUSDRate()));
                        txtPayableUSD.setText(String.format("Payable Amount : $ %.2f", payment1Response.getUSDAmount()));

                        paymentAmount = payment1Response.getUSDAmount();

                        animation = new FlipAnimation(lineatInitialViewTopup, linearTrnsViewTopup);
                        frameRootTopup.startAnimation(animation);

                    } else {
                        Functions.showError(PaypalActivity.this, payment1Response.getResponse().getResponseMsg(), false);
                    }
                } else {
                    Functions.showError(PaypalActivity.this, getString(R.string.try_agian), false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<PaypalPayment1Response> call, Throwable t) {
            hideCircleProgress();

        }
    };

    private void hideCircleProgress() {
        paypalFabInit.hideProgressOnComplete(true);
        paypalFabInit.onProgressCompleted();
        paypalFabInit.showProgress(false);
    }

    APIListener<PaypalPayment2Response> paypal2Listener = new APIListener<PaypalPayment2Response>() {
        @Override
        public void onResponse(Response<PaypalPayment2Response> response) {
            hidProgressDialog();

            try {

                if (response.isSuccessful() && response.body() != null) {

                    PaypalPayment2Response payment2Response = response.body();

                    if (payment2Response.getResponse().getResponseCode() == AppConstant.ResponseSuccess) {

                        LogUtils.LOGE("res", payment2Response.toString());
                        // TODO: 11-08-2016 transaction slip screen

                        setTransactionSlip(payment2Response);

                        Toast.makeText(PaypalActivity.this, "transaction slip screen", Toast.LENGTH_SHORT).show();

                    } else {
                        Functions.showError(PaypalActivity.this, payment2Response.getResponse().getResponseMsg(), false);
                    }
                } else {
                    Functions.showError(PaypalActivity.this, getString(R.string.try_agian), false);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Functions.showError(PaypalActivity.this, getString(R.string.try_agian), false);
            }
        }

        @Override
        public void onFailure(Call<PaypalPayment2Response> call, Throwable t) {
            hidProgressDialog();
            Functions.showError(PaypalActivity.this, getString(R.string.try_agian), false);
        }
    };

    private void setTransactionSlip(PaypalPayment2Response payment2Response) {
        txtZemulaTransID.setText(payment2Response.getZemullaTransID());
        txtTransUSDRate.setText(payment2Response.getUSDRate() + "");
        txtTransPayableUSD.setText(payment2Response.getUSDAmount() + "");
        txtTransAmount.setText(payment2Response.getAmount() + "");
        txtTransPayableAmount.setText(payment2Response.getTotalPayableAmount() + "");
        txtTransCharge.setText(payment2Response.getTotalCharge() + "");
        txtTransDate.setText(payment2Response.getTransactionDate());

        animation = new FlipAnimation(linearTrnsViewTopup, linearPaymentViewGroup);
        frameRootTopup.startAnimation(animation);
    }

    private void actionListener() {

        paypalFabInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Functions.isEmpty(edtAmount)) {
                    Functions.showError(PaypalActivity.this, "Please Enter Amount", false);
                    return;
                }
                Double amount = Double.valueOf(Functions.toStingEditText(edtAmount));
                if (amount <= 0.0) {
                    Functions.showError(PaypalActivity.this, "Please Enter Valid Amount", false);
                    return;
                }
                if (Functions.isFabAnimate(paypalFabInit)) {
                    return;
                }
                callPayPal1Api();
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
                doPaypalIntegration();
            }
        });

    }

    private void doPaypalIntegration() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "USD", "Simplified Coding Fee",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.e("paymentExample", paymentDetails);

                        // call PayPalPayment2AD

                        processApi(confirm);

                        /*//Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(this, ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount));*/

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.e("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    private void processApi(final PaymentConfirmation confirm) {
        showProgressDialog();

        try {
            paypalPayment2Request.setUserID(PrefUtils.getUserID(this));
            paypalPayment2Request.setZemullaTransID(payment1Response.getZemullaTransID());
            paypalPayment2Request.setId(String.valueOf(confirm.toJSONObject().getJSONObject("response").getString("id")));
            paypalPayment2Request.setState(confirm.toJSONObject().getJSONObject("response").getString("state"));
            paypalPayment2Request.setJSONData(confirm.toJSONObject().toString());
            LogUtils.LOGE("req", paypalPayment2Request.toString());

            payPal2Api.getPayPal1(paypalPayment2Request, paypal2Listener);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void callPayPal1Api() {
        paypalFabInit.showProgress(true);
        paypalPayment1Request.setAmount(Double.parseDouble(Functions.toStingEditText(edtAmount)));
        paypalPayment1Request.setUserID(PrefUtils.getUserID(this));

        payPal1Api.getPayPal1(paypalPayment1Request, paypalPayment1ApiListener);
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
                Functions.fireIntentWithClearFlag(PaypalActivity.this, HomeActivity.class);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });


    }
}
