package com.zemulla.android.app.transaction;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.zemulla.android.app.R;
import com.zemulla.android.app.helper.Serivces;
import com.zemulla.android.app.topup.TopupTileBean;
import com.zemulla.android.app.topup.TopupTileConfiguration;

import java.util.ArrayList;

public class TransactionHistoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView txtMainTitle;
    private SmartTabLayout viewpagertab;
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

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(TILES.get(0).getTileName(), HistoryFragment.class)
                .add(TILES.get(1).getTileName(), HistoryFragment.class)
                .add(TILES.get(2).getTileName(), HistoryFragment.class)
                .add(TILES.get(3).getTileName(), HistoryFragment.class)
                .add(TILES.get(4).getTileName(), HistoryFragment.class)
                .add(TILES.get(5).getTileName(), HistoryFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }

    private void setData() {
        Serivces TYPE = (Serivces) getIntent().getSerializableExtra("type");
        switch (TYPE){
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
        txtMainTitle = (TextView)findViewById(R.id.txtMainTitle);
        viewpagertab = (SmartTabLayout)findViewById(R.id.viewpagertab);
        viewpager= (ViewPager)findViewById(R.id.viewpager);
    }

    private void initToolbar(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dhruvil Patel");
        getSupportActionBar().setSubtitle("Effective Balance : ZMW 1222.5");

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
