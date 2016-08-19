package com.zemulla.android.app.fundtransfer.transaction;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.zemulla.android.app.R;
import com.zemulla.android.app.fundtransfer.FundTransferConfiguration;
import com.zemulla.android.app.fundtransfer.FundTransferTileBean;
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
    @BindView(R.id.activity_topup)
    LinearLayout activityTopup;
    @BindView(R.id.viewpagertab)
    TabLayout viewpagertab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
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
        initToolbar();
        initTabs();
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


    private void initTabs() {
        fundTransferTileBeen = FundTransferConfiguration.getAllFundTransferOptions();

        historyPageAdapter = new TopupHistoryPageAdapter(getSupportFragmentManager());
        historyPageAdapter.addPage(FundTransferHistoryFragment.newInstance(), fundTransferTileBeen.get(0).getTileName());
        historyPageAdapter.addPage(FundTransferHistoryFragment.newInstance(), fundTransferTileBeen.get(1).getTileName());
        historyPageAdapter.addPage(FundTransferHistoryFragment.newInstance(), fundTransferTileBeen.get(2).getTileName());
        historyPageAdapter.addPage(FundTransferHistoryFragment.newInstance(), fundTransferTileBeen.get(3).getTileName());
        historyPageAdapter.addPage(FundTransferHistoryFragment.newInstance(), fundTransferTileBeen.get(4).getTileName());
        viewpager.setAdapter(historyPageAdapter);
        viewpagertab.setupWithViewPager(viewpager);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Fragment fragment = historyPageAdapter.getRegisteredFragment(position);

                if (position == 0) {
                } else if (position == 1) {
                    FundTransferHistoryFragment fundTransferHistoryFragment = (FundTransferHistoryFragment) fragment;
                    fundTransferHistoryFragment.setData(ServiceDetails.MTNDebit.getId());
                } else if (position == 2) {
                    FundTransferHistoryFragment fundTransferHistoryFragment = (FundTransferHistoryFragment) fragment;
                    fundTransferHistoryFragment.setData(ServiceDetails.AirtelDebit.getId());
                } else if (position == 3) {
                    FundTransferHistoryFragment fundTransferHistoryFragment = (FundTransferHistoryFragment) fragment;
                    fundTransferHistoryFragment.setData(ServiceDetails.ZoonaDebit.getId());
                } else if (position == 4) {
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
