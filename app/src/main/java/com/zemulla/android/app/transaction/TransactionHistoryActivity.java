package com.zemulla.android.app.transaction;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Serivces;
import com.zemulla.android.app.topup.TopupTileBean;
import com.zemulla.android.app.topup.TopupTileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView txtMainTitle;
    private TabLayout viewpagertab;
    private ViewPager viewpager;
    private ArrayList<TopupTileBean> TILES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        initToolbar();

        init();
        setData();
        setTab();
    }

    private void setTab() {
        TopupTileConfiguration configuration = new TopupTileConfiguration();
        TILES = configuration.getAllTopupOptions();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.add(TILES.get(0).getTileName(), HistoryFragment.newInstance());
        adapter.add(TILES.get(1).getTileName(), HistoryFragment.newInstance());
        adapter.add(TILES.get(2).getTileName(), HistoryFragment.newInstance());
        adapter.add(TILES.get(3).getTileName(), HistoryFragment.newInstance());
        adapter.add(TILES.get(4).getTileName(), HistoryFragment.newInstance());
        adapter.add(TILES.get(5).getTileName(), HistoryFragment.newInstance());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        TabLayout viewPagerTab = (TabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setupWithViewPager(viewPager);
    }

    private void setData() {
        Serivces TYPE = (Serivces) getIntent().getSerializableExtra("type");
        switch (TYPE) {
            case TOPUP:
                txtMainTitle.setText("Topup Transactions");
                break;
            case FUNDTRANSFER:
                txtMainTitle.setText("FundTransfer Transactions");
                break;
            case EMARKET:
                txtMainTitle.setText("E-Market Transactions");
                break;
        }
    }

    private void init() {
        txtMainTitle = (TextView) findViewById(R.id.txtMainTitle);
        viewpagertab = (TabLayout) findViewById(R.id.viewpagertab);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dhruvil Patel");
        getSupportActionBar().setSubtitle("Effective Balance : ZMW 1222.5");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void add(String title, Fragment fragment) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
