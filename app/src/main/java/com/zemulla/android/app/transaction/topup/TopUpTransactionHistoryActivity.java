package com.zemulla.android.app.transaction.topup;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.ServiceDetails;
import com.zemulla.android.app.topup.TopupActivity;
import com.zemulla.android.app.topup.TopupTileBean;
import com.zemulla.android.app.topup.TopupTileConfiguration;
import com.zemulla.android.app.widgets.TfTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopUpTransactionHistoryActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpagertab)
    TabLayout viewpagertab;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.txtMainTitle)
    TfTextView txtMainTitle;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    //  private LoginResponse loginResponse;
    //  private GetWalletDetailResponse walletResponse;
    private TopupHistoryPageAdapter historyPageAdapter;
    private static ArrayList<TopupTileBean> topupTileBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        ButterKnife.bind(this);
        init();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void init() {

        //  walletResponse = PrefUtils.getBALANCE(this);
        // loginResponse = PrefUtils.getUserProfile(this);
        initToolbar();
        initTabs();
    }

    private void initToolbar() {
        if (toolbar != null) {
            try {
                toolbar.setTitle(String.format("%s", "Topup Transcations"));
            } catch (Exception e) {
                Log.d("error","Exception");
            }
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntentWithClearFlagWithWithPendingTransition(TopUpTransactionHistoryActivity.this, TopupActivity.class);
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.fireIntentWithClearFlagWithWithPendingTransition(TopUpTransactionHistoryActivity.this, TopupActivity.class);
    }

    private void initTabs() {
        topupTileBeen = TopupTileConfiguration.getAllTopupOptions();

        historyPageAdapter = new TopupHistoryPageAdapter(getSupportFragmentManager());
        historyPageAdapter.addPage(CyberSourceHistoryFragment.newInstance(), topupTileBeen.get(0).getTileName());
        historyPageAdapter.addPage(PayPalHistoryFragment.newInstance(), topupTileBeen.get(1).getTileName());
        historyPageAdapter.addPage(TopupHistoryFragment.newInstance(ServiceDetails.MTNCredit.getId()), topupTileBeen.get(2).getTileName());
        historyPageAdapter.addPage(TopupHistoryFragment.newInstance(ServiceDetails.AirtelCredit.getId()), topupTileBeen.get(3).getTileName());
        historyPageAdapter.addPage(TopupHistoryFragment.newInstance(ServiceDetails.ZoonaCredit.getId()), topupTileBeen.get(4).getTileName());
        historyPageAdapter.addPage(TopUpBankHistoryFragment.newInstance(), topupTileBeen.get(5).getTileName());
        viewpager.setAdapter(historyPageAdapter);
        viewpagertab.setupWithViewPager(viewpager);
        viewpager.setOffscreenPageLimit(5);

    }


}
