package com.zemulla.android.app.emarket.electricity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.widgets.TfEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mbanje.kurt.fabbutton.FabButton;

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

    private Unbinder unbinder;
    private FlipAnimation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity);
        unbinder = ButterKnife.bind(this);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void init() {
        initToolBar();

        actionListener();
    }

    private void actionListener() {
        initFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Functions.isEmpty(edtMeterNumber)) {
                    Functions.showError(ElectricityActivity.this, "Enter Meter Number", false);

                } else {
                    if (edtAmount.isShown()) {
                        if (Functions.isEmpty(edtAmount)) {
                            Functions.showError(ElectricityActivity.this, "Enter Amount", false);

                        } else {
                            // here also call api for meter number is correct or not
                            // user can change meter number here also
                            // if true then calculate api call else error
                            calculateAmount();
                        }
                    } else {
                        // call Api for checking meter number is correct or not
                        // if correct then visible amount edittext else error
                        checkMeterNumber();
                    }

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
                getMeterInfo();
            }
        });
    }

    private void getMeterInfo() {
        // call api for get meter info from meter number
        confirmFab.showProgress(true);

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                confirmFab.hideProgressOnComplete(true);
                confirmFab.onProgressCompleted();
                Functions.fireIntent(ElectricityActivity.this, ConfirmationActivity.class);
            }
        }.start();
    }

    private void calculateAmount() {
        initFab.showProgress(true);

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                initFab.hideProgressOnComplete(true);
                initFab.onProgressCompleted();
                animation = new FlipAnimation(lineatInitialViewTopup, linearTrnsViewTopup);
                frameRootTopup.startAnimation(animation);

            }
        }.start();

    }

    private void checkMeterNumber() {
        initFab.showProgress(true);

        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                initFab.hideProgressOnComplete(true);
                initFab.onProgressCompleted();
                edtAmount.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void initToolBar() {
        if (toolbar != null) {
            toolbar.setTitle("Dhruvil Patel");
            toolbar.setSubtitle("Effective Balance : ZMW 1222.5");
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
