package com.zemulla.android.app.emarket.airtime;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.widgets.CustomSpinnerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mbanje.kurt.fabbutton.FabButton;

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

    private Unbinder unbinder;
    private ArrayList<String> provider;
    private ArrayList<String> plans;
    private int providerPosition = 0, planPosition = 0;
    private FlipAnimation animation;

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
        initToolbar();

        fetchSpinnerData();

        actionListener();
    }

    private void fetchSpinnerData() {
        provider = new ArrayList<>();
        provider.add(getResources().getString(R.string.select_provider_prompt));
        provider.add("Airtel");
        provider.add("Vodafone");
        provider.add("Idea");
        provider.add("BSNL");
        provider.add("Tata Docomo");
        provider.add("Telenor");
        CustomSpinnerAdapter providerAdapter = new CustomSpinnerAdapter(AirTimeActivity.this, provider);
        spinnerProvider.setAdapter(providerAdapter);
        spinnerProvider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                providerPosition = position;
                String item = parent.getItemAtPosition(position).toString();
                if (!item.equals(getResources().getString(R.string.select_provider_prompt)))
                    Toast.makeText(parent.getContext(), item + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        plans = new ArrayList<>();
        plans.add(getResources().getString(R.string.select_plan_prompt));
        plans.add("Recharge Amount of ZMW 10.0");
        plans.add("Recharge Amount of ZMW 20.0");
        plans.add("Recharge Amount of ZMW 50.0");
        plans.add("Recharge Amount of ZMW 100.0");
        CustomSpinnerAdapter plansAdapter = new CustomSpinnerAdapter(AirTimeActivity.this, plans);
        spinnerPlan.setAdapter(plansAdapter);
        spinnerPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                planPosition = position;
                String item = parent.getItemAtPosition(position).toString();
                if (!item.equals(getResources().getString(R.string.select_plan_prompt)))
                    Toast.makeText(parent.getContext(), item + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void actionListener() {
        paypalFabInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if..else validations then success

                if (providerPosition == 0) {
                    Functions.showError(AirTimeActivity.this, "Please Select Provider", false);
                } else if (planPosition == 0) {
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
                boolean wrapInScrollView = true;

                MaterialDialog.Builder otpDialog = new MaterialDialog.Builder(AirTimeActivity.this).title("Enter OTP")
                        .cancelable(false)
                        .canceledOnTouchOutside(false)
                        .typeface(Functions.getLatoFont(AirTimeActivity.this), Functions.getLatoFont(AirTimeActivity.this))
                        .customView(R.layout.dialog_otp, wrapInScrollView)
                        .positiveText(android.R.string.ok)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                Toast.makeText(AirTimeActivity.this, "Proceed", Toast.LENGTH_SHORT).show();
                            }
                        });
                otpDialog.show();
                initDialog(otpDialog);

               /* MaterialDialog otpDialog = new MaterialDialog.Builder(AirTimeActivity.this)
                        .title("Enter OTP")
                        .cancelable(false)
                        .canceledOnTouchOutside(false)
                        .typeface(Functions.getLatoFont(AirTimeActivity.this), Functions.getLatoFont(AirTimeActivity.this))
                        .customView(R.layout.dialog_otp, wrapInScrollView)
                        .positiveText(android.R.string.ok)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                Toast.makeText(AirTimeActivity.this, "Proceed", Toast.LENGTH_SHORT).show();
                            }
                        });
                otpDialog.show();*/
            }
        });
    }

    private void initDialog(MaterialDialog.Builder otpDialog) {

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
