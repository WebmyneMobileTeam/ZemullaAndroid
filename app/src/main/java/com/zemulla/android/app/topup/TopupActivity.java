package com.zemulla.android.app.topup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import com.zemulla.android.app.R;

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
                case PAYPAL:
                case MTN:
                case AIRTEL_MONEY:
                case BANK_TRANSFER:
                case SUPPORTED_BANK:
                case ZOONA:
                    Intent iInitialTransactionActivity = new Intent(TopupActivity.this,TopupInitialTransactionActivity.class);
                    iInitialTransactionActivity.putExtra("wayname",tile.getTile().getTileName());
                    startActivity(iInitialTransactionActivity);
                    break;

            }

        }
    };
}
