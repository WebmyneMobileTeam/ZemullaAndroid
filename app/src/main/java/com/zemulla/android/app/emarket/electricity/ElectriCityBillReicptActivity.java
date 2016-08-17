package com.zemulla.android.app.emarket.electricity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.home.HomeActivity;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.kazang.kazangtestelectricity.KazangElectricityResponse;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.widgets.TfTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ElectriCityBillReicptActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtMeterNo)
    TfTextView txtMeterNo;
    @BindView(R.id.txtAmount)
    TfTextView txtAmount;
    @BindView(R.id.txtTotalPaidAmount)
    TfTextView txtTotalPaidAmount;
    @BindView(R.id.txtCharge)
    TfTextView txtCharge;
    @BindView(R.id.txtTransactionDate)
    TfTextView txtTransactionDate;
    @BindView(R.id.txtReciptNo)
    TfTextView txtReciptNo;
    @BindView(R.id.txtReferenceNumber)
    TfTextView txtReferenceNumber;
    @BindView(R.id.txtTransactionID)
    TfTextView txtTransactionID;
    @BindView(R.id.txtRemainingAmount)
    TfTextView txtRemainingAmount;
    @BindView(R.id.txtOutStandingCharge)
    TfTextView txtOutStandingCharge;
    @BindView(R.id.txtDescription)
    TfTextView txtDescription;
    private LoginResponse loginResponse;
    private GetWalletDetailResponse walletResponse;
    private KazangElectricityResponse kazangElectricityResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electri_city_bill_reicpt);
        ButterKnife.bind(this);
        getDataFromIntent();
        init();
        setDetails();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        kazangElectricityResponse = (KazangElectricityResponse) intent.getSerializableExtra(Intent.EXTRA_REFERRER);

    }

    private void init() {


        initObject();
        initToolbar();
    }


    private void initObject() {
        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);
    }


    private void initToolbar() {

        try {
            Functions.setToolbarWallet(toolbar, walletResponse, loginResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntentWithClearFlag(ElectriCityBillReicptActivity.this, HomeActivity.class);
            }
        });

    }

    private void setDetails() {

        txtMeterNo.setText(Functions.setEmptyString(kazangElectricityResponse.getMeterNumber()));
        txtAmount.setText(String.valueOf(kazangElectricityResponse.getAmount()));

        txtTotalPaidAmount.setText(String.valueOf(kazangElectricityResponse.getTotalPayableAmount()));
        txtCharge.setText(String.valueOf(kazangElectricityResponse.getTotalCharge()));

        txtTransactionDate.setText(Functions.setEmptyString(kazangElectricityResponse.getTransactionDate()));
        txtReciptNo.setText(Functions.setEmptyString(kazangElectricityResponse.getReceipt()));

        txtReferenceNumber.setText(Functions.setEmptyString(kazangElectricityResponse.getExternal_reference_number()));
        txtTransactionID.setText(Functions.setEmptyString(kazangElectricityResponse.getZemullaTransactionID()));

        txtRemainingAmount.setText(Functions.setEmptyString(kazangElectricityResponse.getRemaining_qouta()));
        txtOutStandingCharge.setText(Functions.setEmptyString(kazangElectricityResponse.getOutstanding_charges()));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.fireIntentWithClearFlag(this, HomeActivity.class);
    }
}
