package com.zemulla.android.app.fundtransfer;

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
import com.zemulla.android.app.fundtransfer.airtelmoney.AirtelMoneyFundTransferActivity;
import com.zemulla.android.app.fundtransfer.banktransfer.BankTransferActivity;
import com.zemulla.android.app.fundtransfer.mtn.MTNFundTransferActivity;
import com.zemulla.android.app.fundtransfer.transaction.FundTransferTransactionActivity;
import com.zemulla.android.app.fundtransfer.zemullawallet.ZemullaWalletFundTransferActivity;
import com.zemulla.android.app.fundtransfer.zoona.ZoonaCashTransferActivity;
import com.zemulla.android.app.helper.Serivces;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FundTransferActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.gridTopupOptions)
    GridLayout gridTopupOptions;
    @BindView(R.id.fundTransferholder)
    LinearLayout fundTransferholder;
    Unbinder unbinder;

    private ArrayList<FundTransferTileBean> tiles_topup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_transfer);
        unbinder = ButterKnife.bind(this);

        init();
    }

    private void init() {
        initToolbar();

        setupGridView();
    }

    private void setupGridView() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        FundTransferConfiguration configuration = new FundTransferConfiguration();
        tiles_topup = configuration.getAllFundTransferOptions();


        for (FundTransferTileBean bean : tiles_topup) {
            FundTransferTile fundTransferTile = new FundTransferTile(FundTransferActivity.this);
            fundTransferTile.setupTile(bean);
            fundTransferTile.setOnClickListener(tileClick);
            gridTopupOptions.addView(fundTransferTile, params);

        }
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
                Intent intent = new Intent(this, FundTransferTransactionActivity.class);
                intent.putExtra("type", Serivces.TOPUP);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initToolbar() {

        if (toolbar != null) {
            toolbar.setTitle("Dhruvil Patel");
            toolbar.setSubtitle("Effective Balance : ZMW 1222.5");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private View.OnClickListener tileClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FundTransferTile tile = (FundTransferTile) v;

            switch (tile.getTile().getId()) {
                case Zemulla_Wallet:
                    Intent cyberIntent = new Intent(FundTransferActivity.this, ZemullaWalletFundTransferActivity.class);
                    startActivity(cyberIntent);
                    break;

                case MTN:
                    Intent paypalIntent = new Intent(FundTransferActivity.this, MTNFundTransferActivity.class);
                    startActivity(paypalIntent);
                    break;

                case Airtel_Money:
                    Intent mtnIntent = new Intent(FundTransferActivity.this, AirtelMoneyFundTransferActivity.class);
                    startActivity(mtnIntent);
                    break;

                case Zoona_Cash:
                    Intent airtelIntent = new Intent(FundTransferActivity.this, ZoonaCashTransferActivity.class);
                    startActivity(airtelIntent);
                    break;

                case Bank_Transfer:
                    Intent bankIntent = new Intent(FundTransferActivity.this, BankTransferActivity.class);
                    startActivity(bankIntent);
                    break;


            }
        }
    };
}
