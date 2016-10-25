package com.zemulla.android.app.emarket.airtime;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.home.HomeActivity;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.kazang.kazangairtime.KazangAirtimeResponse;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AirtimePinActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtTopupWayName)
    TextView txtTopupWayName;
    @BindView(R.id.pinTextView)
    TextView pinTextView;
    @BindView(R.id.detailsTextView)
    TextView detailsTextView;
    @BindView(R.id.rechargeAmoTextView)
    TextView rechargeAmoTextView;
    @BindView(R.id.packageTextView)
    TextView packageTextView;
    @BindView(R.id.transactionIDTextView)
    TextView transactionIDTextView;
    @BindView(R.id.lineatInitialViewTopup)
    LinearLayout lineatInitialViewTopup;
    @BindView(R.id.activity_topup_initial_transaction)
    LinearLayout activityTopupInitialTransaction;
    @BindView(R.id.frameRootTopup)
    ScrollView frameRootTopup;
    @BindView(R.id.clipToCopy)
    ImageView clipToCopy;
    private GetWalletDetailResponse walletResponse;
    private LoginResponse loginResponse;
    private KazangAirtimeResponse kazangAirtimeResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airtime_pin);
        ButterKnife.bind(this);
        getDataFromIntent();
        initToolbar();
        setDetails();

    }


    private void getDataFromIntent() {
        Intent intent = getIntent();
        kazangAirtimeResponse = (KazangAirtimeResponse) intent.getSerializableExtra(Intent.EXTRA_TEXT);
        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);

    }

    private void initToolbar() {
        if (toolbar != null) {
            try {
                Functions.setToolbarWallet(toolbar, walletResponse, loginResponse);
            } catch (Exception e) {
                Log.d("error","Exception");
            }
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntentWithClearFlag(AirtimePinActivity.this, HomeActivity.class);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.fireIntentWithClearFlag(AirtimePinActivity.this, HomeActivity.class);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void setDetails() {

        pinTextView.setText(kazangAirtimeResponse.getPin());
        detailsTextView.setText(kazangAirtimeResponse.getResponse_message());
        transactionIDTextView.setText(String.format("TransactionID  : %s", kazangAirtimeResponse.getZemullaTransactionID()));
        rechargeAmoTextView.setText(String.format("Recharge Amount : %s", kazangAirtimeResponse.getAmount()));
        packageTextView.setText(String.format("Package : %s", kazangAirtimeResponse.getProduct()));
    }

    @OnClick(R.id.clipToCopy)
    public void onClick() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Pin", kazangAirtimeResponse.getPin().trim());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(AirtimePinActivity.this, "copied", Toast.LENGTH_SHORT).show();
    }
}
