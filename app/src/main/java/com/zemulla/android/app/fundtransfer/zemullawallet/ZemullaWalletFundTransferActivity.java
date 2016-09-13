package com.zemulla.android.app.fundtransfer.zemullawallet;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;
import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.payment.GetFundTransferTransactionCalApi;
import com.zemulla.android.app.api.zwallet.IsValidSendWalletAPI;
import com.zemulla.android.app.api.zwallet.SendMoneyW2WAPI;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.DecimalDigitsInputFilter;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.helper.RetrofitErrorHelper;
import com.zemulla.android.app.helper.ServiceDetails;
import com.zemulla.android.app.home.HomeActivity;
import com.zemulla.android.app.home.LogUtils;
import com.zemulla.android.app.model.account.country.Country;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.FundTransferTransactionChargeCalculationResponse;
import com.zemulla.android.app.model.payment.TopUpTransactionChargeCalculation.TopUpTransactionChargeCalculationRequest;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.model.zwallet.isvalidsendwallet.IsValidSendWalletRequest;
import com.zemulla.android.app.model.zwallet.isvalidsendwallet.IsValidSendWalletResponse;
import com.zemulla.android.app.model.zwallet.sendmoneyw2wad.SendMoneyW2WRequest;
import com.zemulla.android.app.model.zwallet.sendmoneyw2wad.SendMoneyW2WResponse;
import com.zemulla.android.app.widgets.OTPDialogAfterLogin;
import com.zemulla.android.app.widgets.countrypicker.CountryPickerView;

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

public class ZemullaWalletFundTransferActivity extends AppCompatActivity {

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
    @BindView(R.id.countryPicker)
    CountryPickerView countryPicker;
    @BindView(R.id.txtTopupAmount)
    TextView txtTopupAmount;
    @BindView(R.id.txtPayableAmount)
    TextView txtPayableAmount;
    @BindView(R.id.txtTransactionCharge)
    TextView txtTransactionCharge;
    private ProgressDialog progressDialog;
    private FlipAnimation animation;
    private Subscription phoneNumberSubscription;
    Country mSelectedCountry = null;
    Unbinder unbinder;
    private IsValidSendWalletRequest isValidSendWalletRequest;
    private IsValidSendWalletAPI isValidSendWalletAPI;
    private boolean isMobileValid;
    private TopUpTransactionChargeCalculationRequest topUpTransactionChargeCalculationRequest;
    private FundTransferTransactionChargeCalculationResponse fundTransferTransactionChargeCalculationResponse;
    private GetFundTransferTransactionCalApi getFundTransferTransactionCalApi;
    private OTPDialogAfterLogin otpDialogAfterLogin;
    private GetWalletDetailResponse walletResponse;
    private LoginResponse loginResponse;
    private SendMoneyW2WRequest sendMoneyW2WRequest;
    private SendMoneyW2WAPI sendMoneyW2WAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zemulla_wallet_fund_transfer);
        unbinder = ButterKnife.bind(this);
        init();
        edtAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter()});

    }

    private void init() {
        topUpTransactionChargeCalculationRequest = new TopUpTransactionChargeCalculationRequest();
        getFundTransferTransactionCalApi = new GetFundTransferTransactionCalApi();
        isValidSendWalletRequest = new IsValidSendWalletRequest();
        isValidSendWalletAPI = new IsValidSendWalletAPI();
        sendMoneyW2WRequest = new SendMoneyW2WRequest();
        sendMoneyW2WAPI = new SendMoneyW2WAPI();
        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);
        otpDialogAfterLogin = new OTPDialogAfterLogin(new MaterialDialog.Builder(ZemullaWalletFundTransferActivity.this));
        otpDialogAfterLogin.setOnSubmitListener(onSubmitListener);
        initToolbar();
        actionListener();

        showProgressDialog();
        countryPicker.fetchCountry();
        countryPicker.setCountryPickerListener(countryPickerListener);

        phoneNumberSubscription = RxTextView.afterTextChangeEvents(countryPicker.getEditText())
                .skip(1)
                .debounce(AppConstant.DebounceTime, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                        String phoneNumber = textViewAfterTextChangeEvent.view().getText().toString();
                        if (phoneNumber.trim().length() == 10) {
                            checkValidPhoneNumber(phoneNumber);
                        }
                        Log.d("password", textViewAfterTextChangeEvent.view().getText().toString());
                    }
                });

    }

    private void checkValidPhoneNumber(String phoneNumber) {
        isValidSendWalletRequest.setUserID(PrefUtils.getUserID(this));
        isValidSendWalletRequest.setMobile(phoneNumber);
        isValidSendWalletRequest.setCallingCode(mSelectedCountry.getCallingCode());
        showProgressDialog();
        isValidSendWalletAPI.IsValidSendWallet(isValidSendWalletRequest, isValidSendWalletResponseAPIListener);

    }


    APIListener<IsValidSendWalletResponse> isValidSendWalletResponseAPIListener = new APIListener<IsValidSendWalletResponse>() {
        @Override
        public void onResponse(Response<IsValidSendWalletResponse> response) {
            hidProgressDialog();
            try {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getResponseCode() == AppConstant.ResponseSuccess) {
                        isMobileValid = true;
                        Toast.makeText(ZemullaWalletFundTransferActivity.this, response.body().getResponseMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        isMobileValid = false;
                        Functions.showError(ZemullaWalletFundTransferActivity.this, response.body().getResponseMsg(), false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<IsValidSendWalletResponse> call, Throwable t) {

            hidProgressDialog();
        }
    };

    CountryPickerView.CountryPickerListener countryPickerListener = new CountryPickerView.CountryPickerListener() {
        @Override
        public void OnFailed(Throwable t) {
            Toast.makeText(ZemullaWalletFundTransferActivity.this, "Failed to load country.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void OnSelected(Country country) {
            hidProgressDialog();
            mSelectedCountry = country;
            String phoneNumber = countryPicker.getPhoneNumber();
            if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.trim().length() == 10) {
                checkValidPhoneNumber(phoneNumber);
            }
            LogUtils.LOGD("Selected Country", country.getCountryName());
        }
    };

    private void actionListener() {
        checkRateFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mSelectedCountry == null) {
                    Functions.showError(ZemullaWalletFundTransferActivity.this, "Please Select Country", false);
                    return;
                }
                if (Functions.isEmpty(countryPicker.getEditText())) {
                    Functions.showError(ZemullaWalletFundTransferActivity.this, "Please Enter Phone Number", false);
                    return;
                }
                if (countryPicker.getPhoneNumber().length() < 10) {
                    Functions.showError(ZemullaWalletFundTransferActivity.this, "Invalid Phone Number", false);
                    return;
                }

                if (!isMobileValid) {
                    Functions.showError(ZemullaWalletFundTransferActivity.this, "Mobile Number Not Found", false);
                    return;
                }

                if (Double.parseDouble(Functions.toStingEditText(edtAmount)) > walletResponse.getEffectiveBalance()) {
                    Functions.showError(ZemullaWalletFundTransferActivity.this, "Enter Valid Amount", false);
                    return;
                }

                if (Functions.isFabAnimate(checkRateFab)) {
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
        topUpTransactionChargeCalculationRequest.setServiceDetailsID(ServiceDetails.WalletToWallet.getId());
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
                        Functions.showError(ZemullaWalletFundTransferActivity.this, fundTransferTransactionChargeCalculationResponse.getResponse().getResponseMsg(), false);
                    }
                } else {
                    Functions.showError(ZemullaWalletFundTransferActivity.this, "Something went wrong. Please try again.", false);
                }
            } catch (Exception e) {
                Functions.showError(ZemullaWalletFundTransferActivity.this, "Something went wrong. Please try again.", false);
            }

        }

        @Override
        public void onFailure(Call<FundTransferTransactionChargeCalculationResponse> call, Throwable t) {
            checkRateFab.showProgress(false);
            RetrofitErrorHelper.showErrorMsg(t, ZemullaWalletFundTransferActivity.this);
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


        sendMoneyW2WRequest.setAmount(Double.parseDouble(Functions.toStingEditText(edtAmount)));
        sendMoneyW2WRequest.setCallingCode(mSelectedCountry.getCallingCode());
        sendMoneyW2WRequest.setMobile(countryPicker.getPhoneNumber());
        sendMoneyW2WRequest.setTotalCharge(fundTransferTransactionChargeCalculationResponse.getTotalCharge());
        sendMoneyW2WRequest.setTotalPayableAmount(fundTransferTransactionChargeCalculationResponse.getTotalPayableAmount());
        sendMoneyW2WRequest.setUserID(PrefUtils.getUserID(this));
        sendMoneyW2WRequest.setVerificationCode(otp);

        showProgressDialog();
        sendMoneyW2WAPI.SendMoneyW2W(sendMoneyW2WRequest, sendMoneyW2WResponseAPIListener);

    }

    APIListener<SendMoneyW2WResponse> sendMoneyW2WResponseAPIListener = new APIListener<SendMoneyW2WResponse>() {
        @Override
        public void onResponse(Response<SendMoneyW2WResponse> response) {

            try {
                hidProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getResponse().getResponseCode() == AppConstant.ResponseSuccess) {
                        otpDialogAfterLogin.dismiss();
                        Functions.showSuccessMsg(ZemullaWalletFundTransferActivity.this, response.body().getResponse().getResponseMsg(), true, HomeActivity.class);
                    } else {
                        Functions.showError(ZemullaWalletFundTransferActivity.this, response.body().getResponse().getResponseMsg(), false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<SendMoneyW2WResponse> call, Throwable t) {
            hidProgressDialog();
            RetrofitErrorHelper.showErrorMsg(t, ZemullaWalletFundTransferActivity.this);
        }
    };


}
