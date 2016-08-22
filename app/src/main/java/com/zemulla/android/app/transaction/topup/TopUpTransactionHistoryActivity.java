package com.zemulla.android.app.transaction.topup;

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
import com.zemulla.android.app.helper.ServiceDetails;
import com.zemulla.android.app.topup.TopupTileBean;
import com.zemulla.android.app.topup.TopupTileConfiguration;
import com.zemulla.android.app.widgets.TfTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopUpTransactionHistoryActivity extends AppCompatActivity {
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
        topupTileBeen = TopupTileConfiguration.getAllTopupOptions();

        historyPageAdapter = new TopupHistoryPageAdapter(getSupportFragmentManager());
        historyPageAdapter.addPage(CyberSourceHistoryFragment.newInstance(), topupTileBeen.get(0).getTileName());
        historyPageAdapter.addPage(PayPalHistoryFragment.newInstance(), topupTileBeen.get(1).getTileName());
        historyPageAdapter.addPage(TopupHistoryFragment.newInstance(), topupTileBeen.get(2).getTileName());
        historyPageAdapter.addPage(TopupHistoryFragment.newInstance(), topupTileBeen.get(3).getTileName());
        historyPageAdapter.addPage(TopupHistoryFragment.newInstance(), topupTileBeen.get(4).getTileName());
        historyPageAdapter.addPage(TopUpBankHistoryFragment.newInstance(), topupTileBeen.get(5).getTileName());
        viewpager.setAdapter(historyPageAdapter);
        viewpagertab.setupWithViewPager(viewpager);
        viewpager.setOffscreenPageLimit(3);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Fragment fragment = historyPageAdapter.getRegisteredFragment(position);

                if (position == 1) {
                    PayPalHistoryFragment payPalHistoryFragment = (PayPalHistoryFragment) fragment;
                    payPalHistoryFragment.setData(ServiceDetails.PaypalPayment.getId());
                } else if (position == 2) {
                    TopupHistoryFragment historyFragment = (TopupHistoryFragment) fragment;
                    historyFragment.setData(ServiceDetails.MTNCredit.getId());
                } else if (position == 3) {
                    TopupHistoryFragment historyFragment = (TopupHistoryFragment) fragment;
                    historyFragment.setData(ServiceDetails.AirtelCredit.getId());
                } else if (position == 4) {
                    TopupHistoryFragment historyFragment = (TopupHistoryFragment) fragment;
                    historyFragment.setData(ServiceDetails.ZoonaCredit.getId());
                } else if (position == 5) {
                    TopUpBankHistoryFragment topUpBankHistoryFragment = (TopUpBankHistoryFragment) fragment;
                    topUpBankHistoryFragment.setData(ServiceDetails.TopUpByAdmin.getId());
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}
