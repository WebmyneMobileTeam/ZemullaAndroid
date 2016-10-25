package com.zemulla.android.app.fundtransfer.transaction;

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
import com.zemulla.android.app.fundtransfer.FundTransferActivity;
import com.zemulla.android.app.fundtransfer.FundTransferConfiguration;
import com.zemulla.android.app.fundtransfer.FundTransferTileBean;
import com.zemulla.android.app.fundtransfer.transaction.bank.FundTransferBankHistoryFragment;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.helper.ServiceDetails;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.transaction.topup.TopupHistoryPageAdapter;
import com.zemulla.android.app.widgets.TfTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FundTransferHistoryActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtMainTitle)
    TfTextView txtMainTitle;
    @BindView(R.id.viewpagertab)
    TabLayout viewpagertab;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    private LoginResponse loginResponse;
    private GetWalletDetailResponse walletResponse;
    private TopupHistoryPageAdapter historyPageAdapter;
    private static ArrayList<FundTransferTileBean> fundTransferTileBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_transfer_history);
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

        walletResponse = PrefUtils.getBALANCE(this);
        loginResponse = PrefUtils.getUserProfile(this);
        initToolbar();
        initTabs();
    }

    private void initToolbar() {
        if (toolbar != null) {
            try {
                toolbar.setTitle(String.format("%s", "Fund Transfer"));
            } catch (Exception e) {
                Log.d("error","Exception");
            }
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntentWithClearFlagWithWithPendingTransition(FundTransferHistoryActivity.this, FundTransferActivity.class);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.fireIntentWithClearFlagWithWithPendingTransition(FundTransferHistoryActivity.this, FundTransferActivity.class);
    }

    private void initTabs() {
        fundTransferTileBeen = FundTransferConfiguration.getAllFundTransferOptions();

        historyPageAdapter = new TopupHistoryPageAdapter(getSupportFragmentManager());
        historyPageAdapter.addPage(W2WFundTransferHistoryFragment.newInstance(), fundTransferTileBeen.get(0).getTileName());
        historyPageAdapter.addPage(FundTransferHistoryFragment.newInstance(ServiceDetails.MTNDebit.getId()), fundTransferTileBeen.get(1).getTileName());
        historyPageAdapter.addPage(FundTransferHistoryFragment.newInstance(ServiceDetails.AirtelDebit.getId()), fundTransferTileBeen.get(2).getTileName());
        historyPageAdapter.addPage(FundTransferHistoryFragment.newInstance(ServiceDetails.ZoonaDebit.getId()), fundTransferTileBeen.get(3).getTileName());
        historyPageAdapter.addPage(FundTransferBankHistoryFragment.newInstance(), fundTransferTileBeen.get(4).getTileName());
        viewpager.setAdapter(historyPageAdapter);
        viewpagertab.setupWithViewPager(viewpager);
        viewpager.setOffscreenPageLimit(4);


    }


}
