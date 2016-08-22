package com.zemulla.android.app.emarket.transaction;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.zemulla.android.app.R;
import com.zemulla.android.app.emarket.MarketConfiguration;
import com.zemulla.android.app.emarket.MarketTileBean;
import com.zemulla.android.app.helper.ServiceDetails;
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
        container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Fragment fragment = historyPageAdapter.getRegisteredFragment(position);

                if (position == 1) {
                    ElectricityPaymentHistoryFragment electricityPaymentHistoryFragment = (ElectricityPaymentHistoryFragment) fragment;
                    electricityPaymentHistoryFragment.setData(ServiceDetails.KazangElectricity.getId());
                } else if (position == 2) {
                    DirectRechargeHistoryFragment directRechargeHistoryFragment = (DirectRechargeHistoryFragment) fragment;
                    directRechargeHistoryFragment.setData(ServiceDetails.KazangDirectRecharge.getId());
                } else if (position == 3) {
                    DSTVPaymentHistoryFragment dstvPaymentHistoryFragment = (DSTVPaymentHistoryFragment) fragment;
                    dstvPaymentHistoryFragment.setData(ServiceDetails.DSTVPayment.getId());
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_emarket_transaction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
