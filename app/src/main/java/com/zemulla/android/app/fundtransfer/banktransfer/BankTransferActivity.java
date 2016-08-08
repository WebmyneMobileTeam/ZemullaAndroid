package com.zemulla.android.app.fundtransfer.banktransfer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.widgets.OTPDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mbanje.kurt.fabbutton.FabButton;

public class BankTransferActivity extends AppCompatActivity {

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

    private FlipAnimation animation;
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_transfer);
        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        initToolbar();

        actionListener();


    }

    private void actionListener() {
        checkRateFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Functions.isEmpty(edtAmount)) {
                    Functions.showError(BankTransferActivity.this, "Please Enter Amount", false);
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
                new OTPDialog(BankTransferActivity.this, new OTPDialog.onSubmitListener() {
                    @Override
                    public void onSubmit(String OTP) {

                    }

                    @Override
                    public void onResend() {

                    }

                    @Override
                    public void ChangeEmail() {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
