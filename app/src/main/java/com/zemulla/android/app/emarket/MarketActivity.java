package com.zemulla.android.app.emarket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.zemulla.android.app.R;
import com.zemulla.android.app.emarket.airtime.AirTimeActivity;
import com.zemulla.android.app.emarket.direct_recharge.DirectRechargeActivity;
import com.zemulla.android.app.emarket.dstv.DSTVEmarketActivity;
import com.zemulla.android.app.emarket.electricity.ElectricityActivity;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.home.HomeActivity;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MarketActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.gridTopupOptions)
    GridLayout gridTopupOptions;
    @BindView(R.id.activity_topup)
    LinearLayout activityTopup;
    Unbinder unbinder;
    private GetWalletDetailResponse walletResponse;
    private LoginResponse loginResponse;

    private ArrayList<MarketTileBean> tiles_topup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
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
        MarketConfiguration configuration = new MarketConfiguration();
        tiles_topup = configuration.getAllMarketOptions();


        for (MarketTileBean bean : tiles_topup) {
            MarketTile homeTile = new MarketTile(MarketActivity.this);
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

                Functions.fireIntentWithClearFlag(MarketActivity.this, HomeActivity.class);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.fireIntentWithClearFlag(MarketActivity.this, HomeActivity.class);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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
               /* Intent intent = new Intent(this, TransactionHistoryActivity.class);
                intent.putExtra("type", Serivces.TOPUP);
                startActivity(intent);*/
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private View.OnClickListener tileClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MarketTile tile = (MarketTile) v;

            switch (tile.getTile().getId()) {
                case AIRTIME_TOPUP:
                    Intent airIntent = new Intent(MarketActivity.this, AirTimeActivity.class);
                    startActivity(airIntent);
                    break;

                case ELECTRICITY:
                    Intent eleIntent = new Intent(MarketActivity.this, ElectricityActivity.class);
                    startActivity(eleIntent);
                    break;

                case DTH:
                    Intent dthIntent = new Intent(MarketActivity.this, DirectRechargeActivity.class);
                    startActivity(dthIntent);
                    break;

                case DSTV:
                    Intent dstvIntent = new Intent(MarketActivity.this, DSTVEmarketActivity.class);
                    startActivity(dstvIntent);
                    break;
            }

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    };
}
