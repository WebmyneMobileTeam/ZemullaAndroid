package com.zemulla.android.app.topup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zemulla.android.app.R;
import com.zemulla.android.app.constant.IntentConstant;
import com.zemulla.android.app.helper.DynamicMasterId;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.helper.Serivces;
import com.zemulla.android.app.helper.ServiceDetails;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.topup.bank.BankActivity;
import com.zemulla.android.app.topup.bank.SupportedBankListActivity;
import com.zemulla.android.app.topup.cyber.CyberSourceActivity;
import com.zemulla.android.app.topup.mtn.MtnActivity;
import com.zemulla.android.app.topup.paypal.PaypalActivity;
import com.zemulla.android.app.transaction.topup.TopUpTransactionHistoryActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TopupActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.gridTopupOptions)
    GridLayout gridTopupOptions;
    @BindView(R.id.activity_topup)
    LinearLayout activityTopup;
    Unbinder unbinder;
    private LoginResponse loginResponse;
    private GetWalletDetailResponse walletResponse;
    private ArrayList<TopupTileBean> tiles_topup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);
        unbinder = ButterKnife.bind(this);

        init();

    }


    private void init() {
        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);
        initToolbar();
        setupGridView();
    }

    private void setupGridView() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TopupTileConfiguration configuration = new TopupTileConfiguration();
        tiles_topup = configuration.getAllTopupOptions();


        for (TopupTileBean bean : tiles_topup) {
            TopupTile homeTile = new TopupTile(TopupActivity.this);
            homeTile.setupTile(bean);
            homeTile.setOnClickListener(tileClick);
            gridTopupOptions.addView(homeTile, params);

        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history:
                Intent intent = new Intent(this, TopUpTransactionHistoryActivity.class);
                intent.putExtra("type", Serivces.TOPUP);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener tileClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TopupTile tile = (TopupTile) v;

            switch (tile.getTile().getId()) {
                case CYBER_SOURCE:
                    Intent cyberIntent = new Intent(TopupActivity.this, CyberSourceActivity.class);
                    startActivity(cyberIntent);
                    break;

                case PAYPAL:
                    Intent paypalIntent = new Intent(TopupActivity.this, PaypalActivity.class);
                    startActivity(paypalIntent);
                    break;

                case MTN:
                    Intent mtnIntent = new Intent(TopupActivity.this, MtnActivity.class);
                    mtnIntent.putExtra(IntentConstant.INTENT_EXTRA_MASTER_ID, DynamicMasterId.MTN.getId());
                    mtnIntent.putExtra(IntentConstant.INTENT_EXTRA_SERVICE_DETAILS, ServiceDetails.MTNCredit.getId());
                    startActivity(mtnIntent);
                    break;

                case AIRTEL_MONEY:
                    Intent airtelIntent = new Intent(TopupActivity.this, MtnActivity.class);
                    airtelIntent.putExtra(IntentConstant.INTENT_EXTRA_MASTER_ID, DynamicMasterId.Airtel.getId());
                    airtelIntent.putExtra(IntentConstant.INTENT_EXTRA_SERVICE_DETAILS, ServiceDetails.AirtelCredit.getId());

                    startActivity(airtelIntent);
                    break;

                case BANK_TRANSFER:
                    Intent bankIntent = new Intent(TopupActivity.this, BankActivity.class);
                    startActivity(bankIntent);
                    break;

                case SUPPORTED_BANK:
                    Intent supportedBankIntent = new Intent(TopupActivity.this, SupportedBankListActivity.class);
                    startActivity(supportedBankIntent);
                    break;

                case ZOONA:
                    Intent zoonaIntent = new Intent(TopupActivity.this, MtnActivity.class);
                    zoonaIntent.putExtra(IntentConstant.INTENT_EXTRA_MASTER_ID, DynamicMasterId.Zoona.getId());
                    zoonaIntent.putExtra(IntentConstant.INTENT_EXTRA_SERVICE_DETAILS, ServiceDetails.ZoonaCredit.getId());
                    startActivity(zoonaIntent);
                    break;


            }

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
