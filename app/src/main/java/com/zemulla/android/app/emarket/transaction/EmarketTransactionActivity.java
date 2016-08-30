package com.zemulla.android.app.emarket.transaction;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zemulla.android.app.R;
import com.zemulla.android.app.emarket.MarketConfiguration;
import com.zemulla.android.app.emarket.MarketTileBean;
import com.zemulla.android.app.transaction.topup.TopupHistoryPageAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmarketTransactionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.container)
    ViewPager container;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;

    private TopupHistoryPageAdapter historyPageAdapter;
    private static ArrayList<MarketTileBean> marketTileArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emarket_transaction);
        ButterKnife.bind(this);
        init();


    }

    private void init() {
        initToolbar();
        initTabs();
    }

    private void initToolbar() {

        if (toolbar != null) {
            try {
                toolbar.setTitle(String.format("%s", "Emarket Transaction"));
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

        marketTileArrayList = MarketConfiguration.getAllMarketOptions();
        historyPageAdapter = new TopupHistoryPageAdapter(getSupportFragmentManager());
        historyPageAdapter.addPage(AirTimeHistoryFragment.newInstance(), marketTileArrayList.get(0).getTileName());
        historyPageAdapter.addPage(ElectricityPaymentHistoryFragment.newInstance(), marketTileArrayList.get(1).getTileName());
        historyPageAdapter.addPage(DirectRechargeHistoryFragment.newInstance(), marketTileArrayList.get(2).getTileName());
        historyPageAdapter.addPage(DSTVPaymentHistoryFragment.newInstance(), marketTileArrayList.get(3).getTileName());
        container.setAdapter(historyPageAdapter);
        tabs.setupWithViewPager(container);
        container.setOffscreenPageLimit(3);
    }





}
