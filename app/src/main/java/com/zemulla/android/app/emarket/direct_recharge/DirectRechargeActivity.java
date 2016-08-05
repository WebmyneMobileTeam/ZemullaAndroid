package com.zemulla.android.app.emarket.direct_recharge;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.widgets.CustomSpinnerAdapter;
import com.zemulla.android.app.widgets.OTPDialog;
import com.zemulla.android.app.widgets.TfEditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mbanje.kurt.fabbutton.FabButton;

public class DirectRechargeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTopupWayName)
    TextView txtTopupWayName;
    @BindView(R.id.edtNumber)
    EditText edtNumber;
    @BindView(R.id.spinnerProduct)
    AppCompatSpinner spinnerProduct;
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

    private FlipAnimation animation;
    Unbinder unbinder;
    private ArrayList<String> products;
    private int productPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_recharge);
        unbinder = ButterKnife.bind(this);

        init();
    }

    private void init() {
        initToolbar();

        initProducts();

        actionListener();
    }

    private void initProducts() {
        products = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            products.add("Product " + i);
        }
        CustomSpinnerAdapter providerAdapter = new CustomSpinnerAdapter(DirectRechargeActivity.this, products);
        spinnerProduct.setAdapter(providerAdapter);
        spinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                productPosition = position;
                String item = parent.getItemAtPosition(position).toString();
                if (!item.equals(getResources().getString(R.string.select_provider_prompt)))
                    Toast.makeText(parent.getContext(), item + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void actionListener() {
        initFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Functions.isEmpty(edtNumber)) {
                    Functions.showError(DirectRechargeActivity.this, "Enter Number", false);
                } else if (Functions.getLength(edtNumber) < 10) {
                    Functions.showError(DirectRechargeActivity.this, "Enter Number", false);
                } else if (productPosition == 0) {
                    Functions.showError(DirectRechargeActivity.this, "Select Product", false);
                } else if (Functions.isEmpty(edtAmount)) {
                    Functions.showError(DirectRechargeActivity.this, "Enter Amount", false);
                } else {
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
                new OTPDialog(DirectRechargeActivity.this, new OTPDialog.onSubmitListener() {
                    @Override
                    public void onSubmit() {
                        Toast.makeText(DirectRechargeActivity.this, "Submit", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
    }

    private void calculateAmount() {
        initFab.showProgress(true);

        new CountDownTimer(2000, 1000) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
