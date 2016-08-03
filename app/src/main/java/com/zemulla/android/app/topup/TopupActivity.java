package com.zemulla.android.app.topup;

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
import com.zemulla.android.app.topup.airtel.AirtelMoneyActivity;
import com.zemulla.android.app.topup.bank.BankActivity;
import com.zemulla.android.app.topup.bank.SupportedBankListActivity;
import com.zemulla.android.app.topup.cyber.CyberSourceActivity;
import com.zemulla.android.app.topup.mtn.MtnActivity;
import com.zemulla.android.app.topup.paypal.PaypalActivity;
import com.zemulla.android.app.topup.zoona.ZoonaActivity;

import java.util.ArrayList;

public class TopupActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private GridLayout gridTopupOptions;
    private ArrayList<TopupTileBean> tiles_topup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);
        initToolbar();
        init();


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

    private void init() {
        gridTopupOptions = (GridLayout) findViewById(R.id.gridTopupOptions);
    }

    private void initToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dhruvil Patel");
        getSupportActionBar().setSubtitle("Effective Balance : ZMW 1222.5");
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
            case android.R.id.home:
                finish();
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
                    startActivity(mtnIntent);
                    break;

                case AIRTEL_MONEY:
                    Intent airtelIntent = new Intent(TopupActivity.this, AirtelMoneyActivity.class);
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
                    Intent zoonaIntent = new Intent(TopupActivity.this, ZoonaActivity.class);
                    startActivity(zoonaIntent);
                    break;
            }
        }
    };
}
