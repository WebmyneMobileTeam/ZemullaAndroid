package com.zemulla.android.app.fundtransfer.zemullawallet;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.home.LogUtils;
import com.zemulla.android.app.model.country.Country;
import com.zemulla.android.app.widgets.OTPDialog;
import com.zemulla.android.app.widgets.countrypicker.CountryPickerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mbanje.kurt.fabbutton.FabButton;

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

    private FlipAnimation animation;

    Country mSelectedCountry = null;
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zemulla_wallet_fund_transfer);
        unbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        initToolbar();

        actionListener();

        countryPicker.fetchCountry();
        countryPicker.setCountryPickerListener(new CountryPickerView.CountryPickerListener() {
            @Override
            public void OnFailed(Throwable t) {
                Toast.makeText(ZemullaWalletFundTransferActivity.this, "Failed to load country.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnSelected(Country country) {

                mSelectedCountry = country;
                LogUtils.LOGD("Selected Country", country.getCountryName());

            }
        });

    }

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


                if (Functions.isEmpty(edtAmount)) {
                    Functions.showError(ZemullaWalletFundTransferActivity.this, "Please Enter Amount", false);
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

                new OTPDialog(ZemullaWalletFundTransferActivity.this, new OTPDialog.onSubmitListener() {
                    @Override
                    public void onSubmit(String OTP) {

                    }

                    @Override
                    public void onResend() {

                    }

                }).show();

            }
        });
    }

    private void initToolbar() {
        if (toolbar != null) {
            toolbar.setTitle("Dhruvil Patel");
            toolbar.setSubtitle("Effective Balance : ZMW 1222.5");
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

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                checkRateFab.hideProgressOnComplete(true);
                checkRateFab.onProgressCompleted();
                animation = new FlipAnimation(fundTransferHolder, rateHolder);
                frameRootTopup.startAnimation(animation);

            }
        }.start();
    }

}
