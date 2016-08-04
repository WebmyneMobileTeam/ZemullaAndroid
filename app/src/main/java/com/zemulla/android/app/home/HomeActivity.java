package com.zemulla.android.app.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.meetic.marypopup.MaryPopup;
import com.zemulla.android.app.R;
import com.zemulla.android.app.emarket.MarketActivity;
import com.zemulla.android.app.fundtransfer.FundTransferActivity;
import com.zemulla.android.app.topup.TopupActivity;
import com.zemulla.android.app.transaction.TransactionHistoryActivity;
import com.zemulla.android.app.user.ChangePasswordActivity;
import com.zemulla.android.app.user.ContactUsActivity;
import com.zemulla.android.app.user.UserProfileActivity;
import com.zemulla.android.app.widgets.DrawerDialogView;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private GridLayout gridHomeOptions;
    private ArrayList<HomeTileBean> tiles;
    MaryPopup popup;
    private DrawerDialogView drawerDialogView;
    private RelativeLayout btnTransactionHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initToolbar_Drawer();
        init();
    }

    private void initToolbar_Drawer() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dhruvil Patel");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setNavigationIcon(R.drawable.ic_action_person);
        setSupportActionBar(toolbar);

        gridHomeOptions = (GridLayout) findViewById(R.id.gridHomeOptions);
        btnTransactionHistory = (RelativeLayout) findViewById(R.id.btnTransactionHistory);
        btnTransactionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent history = new Intent(HomeActivity.this, TransactionHistoryActivity.class);
                startActivity(history);
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        HomeTileConfiguration configuration = new HomeTileConfiguration();
        tiles = configuration.getAllTiles();

        for (HomeTileBean bean : tiles) {

            HomeTile homeTile = new HomeTile(HomeActivity.this);
            homeTile.setupTile(bean);
            homeTile.setOnClickListener(tileClick);
            gridHomeOptions.addView(homeTile, params);


        }

        popup = MaryPopup.with(this)
                .cancellable(true)
                .draggable(true)
                .scaleDownDragging(true)
                .center(false)
                .fadeOutDragging(true)
                .blackOverlayColor(Color.parseColor("#60000000"))
                .backgroundColor(Color.parseColor("#EFF4F5"));

        drawerDialogView = new DrawerDialogView(HomeActivity.this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popup.content(drawerDialogView)
                        .from(toolbar)
                        .show();
            }
        });

        drawerDialogView.setItemsClickListner(new DrawerDialogView.OnItemsClickListner() {
            @Override
            public void onClick(DrawerOptionsConfiguration.OptionID id) {

                Intent intent = new Intent();

                switch (id) {

                    case VIEW_PROFILE:
                        intent.setClass(HomeActivity.this, UserProfileActivity.class);

                        break;

                    case UPDATE_KYC:
                        intent.setClass(HomeActivity.this, UserProfileActivity.class);
                        break;

                    case CHANGE_PASSWORD:
                        intent.setClass(HomeActivity.this, ChangePasswordActivity.class);
                        break;

                    case Conatct_Zemulla:
                        intent.setClass(HomeActivity.this, ContactUsActivity.class);
                        break;

                    case LOGOUT:
                        intent.setClass(HomeActivity.this, UserProfileActivity.class);
                        break;


                }

                if (android.os.Build.VERSION.SDK_INT >= 21) {

                    LinearLayout linearprofile_trans = (LinearLayout) drawerDialogView.findViewById(R.id.linearprofile_trans);
                    String transitionName = "profile_trans";
                    ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this, linearprofile_trans, transitionName);
                    startActivity(intent, transitionActivityOptions.toBundle());

                } else {
                    startActivity(intent);
                }
            }
        });
    }

    private void init() {

    }

    @Override
    public void onBackPressed() {
        if (popup.isOpened()) {
            popup.close(true);
        } else {
            super.onBackPressed();
        }
    }

    private void openDialogProfile() {


    }


    private View.OnClickListener tileClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            HomeTile tile = (HomeTile) v;

            switch (tile.getTile().getId()) {

                case TOPUP:
                    Intent iTopUp = new Intent(HomeActivity.this, TopupActivity.class);
                    startActivity(iTopUp);
                    break;

                case FUND_TRANSFER:
                    Intent fundTransfer = new Intent(HomeActivity.this, FundTransferActivity.class);
                    startActivity(fundTransfer);
                    break;

                case EMARKET:
                    Intent iMarket = new Intent(HomeActivity.this, MarketActivity.class);
                    startActivity(iMarket);
                    break;

                case REPORTS:

                    break;
            }

        }
    };


}
