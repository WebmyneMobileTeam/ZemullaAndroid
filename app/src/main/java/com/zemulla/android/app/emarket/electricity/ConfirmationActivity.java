package com.zemulla.android.app.emarket.electricity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.FlipAnimation;
import com.zemulla.android.app.widgets.OTPDialog;
import com.zemulla.android.app.widgets.TfTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mbanje.kurt.fabbutton.FabButton;

public class ConfirmationActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTopupWayName)
    TextView txtTopupWayName;
    @BindView(R.id.edtMeterNumber)
    TfTextView edtMeterNumber;
    @BindView(R.id.initFab)
    FabButton initFab;
    @BindView(R.id.lineatInitialViewTopup)
    LinearLayout lineatInitialViewTopup;
    @BindView(R.id.frameRootTopup)
    FrameLayout frameRootTopup;
    @BindView(R.id.activity_topup_initial_transaction)
    LinearLayout activityTopupInitialTransaction;

    private Unbinder unbinder;
    private FlipAnimation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
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

        actionListener();
    }

    private void actionListener() {
        initFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call Api for requesting OTP
                new OTPDialog(ConfirmationActivity.this, new OTPDialog.onSubmitListener() {
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

    @Override
    public void onBackPressed() {
        finish();
    }
}
