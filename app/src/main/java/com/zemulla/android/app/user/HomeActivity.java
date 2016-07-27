package com.zemulla.android.app.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import com.zemulla.android.app.R;
import com.zemulla.android.app.home.HomeTile;
import com.zemulla.android.app.home.HomeTileBean;
import com.zemulla.android.app.home.HomeTileConfiguration;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private GridLayout gridHomeOptions;
    private ArrayList<HomeTileBean> tiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dhruvil Patel");
        setSupportActionBar(toolbar);

        gridHomeOptions = (GridLayout) findViewById(R.id.gridHomeOptions);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        HomeTileConfiguration configuration = new HomeTileConfiguration();
        tiles = configuration.getAllTiles();

        for (HomeTileBean bean : tiles) {
            HomeTile homeTile = new HomeTile(HomeActivity.this);
            homeTile.setupTile(bean);
            homeTile.setOnClickListener(tileClick);
            gridHomeOptions.addView(homeTile, params);
        }


    }

    private View.OnClickListener tileClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            HomeTile tile = (HomeTile) v;

            switch (tile.getTile().getId()) {

                case TOPUP:
                    break;
                case FUND_TRANSFER:
                    break;
                case EMARKET:
                    break;
                case REPORTS:
                    break;
            }

        }
    };
}
