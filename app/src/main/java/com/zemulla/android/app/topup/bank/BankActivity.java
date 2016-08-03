package com.zemulla.android.app.topup.bank;

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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbanje.kurt.fabbutton.FabButton;

public class BankActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTopupWayName)
    TextView txtTopupWayName;
    @BindView(R.id.btnProcessInitialTransaction)
    FabButton btnProcessInitialTransaction;
    @BindView(R.id.lineatInitialViewTopup)
    LinearLayout lineatInitialViewTopup;
    @BindView(R.id.spinnerRecipientBankList)
    AppCompatSpinner spinnerRecepientBankList;
    @BindView(R.id.btnProcessResetTransaction)
    FabButton btnProcessResetTransaction;
    @BindView(R.id.btnProcessConfirmTransaction)
    FabButton btnProcessConfirmTransaction;
    @BindView(R.id.linearTrnsViewTopup)
    LinearLayout linearTrnsViewTopup;
    @BindView(R.id.frameRootTopup)
    FrameLayout frameRootTopup;
    @BindView(R.id.activity_topup_initial_transaction)
    LinearLayout activityTopupInitialTransaction;
    @BindView(R.id.edtBranchName)
    EditText edtBranchName;
    @BindView(R.id.edtAccountName)
    EditText edtAccountName;
    @BindView(R.id.edtAccountNumber)
    EditText edtAccountNumber;
    @BindView(R.id.edtSwiftCode)
    EditText edtSwiftCode;
    @BindView(R.id.edtRemark)
    EditText edtRemark;
    @BindView(R.id.txtEnterAmount)
    TextView txtEnterAmount;
    @BindView(R.id.edtAmount)
    EditText edtAmount;
    @BindView(R.id.txtTransferAmount)
    TextView txtTransferAmount;
    @BindView(R.id.txtTransferAmountValue)
    TextView txtTransferAmountValue;
    @BindView(R.id.txtTransactionCharge)
    TextView txtTransactionCharge;
    @BindView(R.id.txtEnterBankDetails)
    TextView txtEnterBankDetails;

    private FlipAnimation animation;

    private ArrayList<String> banks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        initToolbar();
        initApplyFonts();
        btnProcessInitialTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Functions.isEmpty(edtAmount)) {
                    Toast.makeText(BankActivity.this, "Please Enter Amount", Toast.LENGTH_SHORT).show();
                } else {
                    btnProcessInitialTransaction.showProgress(true);

                    new CountDownTimer(3000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            btnProcessInitialTransaction.hideProgressOnComplete(true);
                            btnProcessInitialTransaction.onProgressCompleted();
                            animation = new FlipAnimation(lineatInitialViewTopup, linearTrnsViewTopup);
                            frameRootTopup.startAnimation(animation);

                        }
                    }.start();
                }
            }
        });

        btnProcessResetTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.reverse();
                frameRootTopup.startAnimation(animation);
            }
        });

        banks = new ArrayList<String>();
        banks.add(getResources().getString(R.string.select_recipient_bank_prompt));
        banks.add("Bank 1");
        banks.add("Bank 2");
        banks.add("Bank 3");
        banks.add("Bank 4");
        banks.add("Bank 5");
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(BankActivity.this, banks);
        spinnerRecepientBankList.setAdapter(customSpinnerAdapter);
        spinnerRecepientBankList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (!item.equals(getResources().getString(R.string.select_recipient_bank_prompt)))
                    Toast.makeText(parent.getContext(), item + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnProcessConfirmTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateBankDetails();
            }
        });
    }

    private void validateBankDetails() {
        if(spinnerRecepientBankList.getSelectedItem().toString().equals(getResources().getString(R.string.select_recipient_bank_prompt))) {
            Toast.makeText(BankActivity.this, "Please Select Recipient Bank", Toast.LENGTH_SHORT).show();
            return;
        } else if(Functions.isEmpty(edtBranchName)) {
            Toast.makeText(BankActivity.this, "Please Enter Branch Name", Toast.LENGTH_SHORT).show();
            return;
        } else if(Functions.isEmpty(edtAccountName)) {
            Toast.makeText(BankActivity.this, "Please Enter Account Name", Toast.LENGTH_SHORT).show();
            return;
        } else if(Functions.isEmpty(edtAccountNumber)) {
            Toast.makeText(BankActivity.this, "Please Enter Account Number", Toast.LENGTH_SHORT).show();
            return;
        } else if(Functions.isEmpty(edtSwiftCode)) {
            Toast.makeText(BankActivity.this, "Please Enter Switft Code", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(BankActivity.this, "Money Transfer Successful", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initApplyFonts() {
        txtTopupWayName.setTypeface(Functions.getLatoFont(this));
        txtEnterAmount.setTypeface(Functions.getLatoFont(this));
        txtTransactionCharge.setTypeface(Functions.getLatoFont(this));
        txtTransferAmount.setTypeface(Functions.getLatoFont(this));
        txtTransferAmountValue.setTypeface(Functions.getLatoFont(this));
        txtEnterBankDetails.setTypeface(Functions.getLatoFont(this));

        edtAccountName.setTypeface(Functions.getLatoFont(this));
        edtAccountNumber.setTypeface(Functions.getLatoFont(this));
        edtBranchName.setTypeface(Functions.getLatoFont(this));
        edtSwiftCode.setTypeface(Functions.getLatoFont(this));
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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

}
