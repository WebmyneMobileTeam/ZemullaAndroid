package com.zemulla.android.app.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.meetic.marypopup.MaryPopup;
import com.zemulla.android.app.R;
import com.zemulla.android.app.emarket.MarketActivity;
import com.zemulla.android.app.fundtransfer.FundTransferActivity;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.model.login.LoginResponse;
import com.zemulla.android.app.topup.TopupActivity;
import com.zemulla.android.app.transaction.TransactionHistoryActivity;
import com.zemulla.android.app.user.ChangePasswordActivity;
import com.zemulla.android.app.user.ContactUsActivity;
import com.zemulla.android.app.user.KYCActivity;
import com.zemulla.android.app.user.UserProfileActivity;
import com.zemulla.android.app.widgets.DrawerDialogView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.linearTop)
    LinearLayout linearTop;

    private Toolbar toolbar;
    private GridLayout gridHomeOptions;
    private ArrayList<HomeTileBean> tiles;
    MaryPopup popup;
    private DrawerDialogView drawerDialogView;
    private RelativeLayout btnTransactionHistory;
    private LoginResponse response;
    private ImageView imgProfilePic;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        unbinder = ButterKnife.bind(this);

        response = PrefUtils.getUserProfile(this);

        initToolbar_Drawer();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initToolbar_Drawer() {
        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dhruvil Patel");

        toolbar.setTitle(response.getFirstName() + " " + response.getLastName());

        if (TextUtils.isEmpty(response.getProfilePic())) {
            imgProfilePic.setImageResource(R.drawable.default_user);
        } else {
            Functions.setRoundImage(this, imgProfilePic, response.getProfilePicURL() + response.getProfilePic());
        }

        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
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
        linearTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.content(drawerDialogView)
                        .from(linearTop)
                        .show();
            }
        });

        drawerDialogView.setItemsClickListner(new DrawerDialogView.OnItemsClickListner() {
            @Override
            public void onClick(DrawerOptionsConfiguration.OptionID id) {
                popup.close(true);
                Intent intent = new Intent();

                switch (id) {
                    case VIEW_PROFILE:
                        intent.setClass(HomeActivity.this, UserProfileActivity.class);
                        startActivity(intent);
//                        redirectIntent(intent);
                        break;

                    case UPDATE_KYC:
                        intent.setClass(HomeActivity.this, KYCActivity.class);
                        redirectIntent(intent);
                        break;

                    case CHANGE_PASSWORD:
                        intent.setClass(HomeActivity.this, ChangePasswordActivity.class);
                        redirectIntent(intent);
                        break;

                    case Conatct_Zemulla:
                        intent.setClass(HomeActivity.this, ContactUsActivity.class);
                        redirectIntent(intent);
                        break;

                    case LOGOUT:
                        promptLogout();
                        break;
                }
            }
        });
    }

    private void promptLogout() {
        new MaterialDialog.Builder(HomeActivity.this)
                .content("Are you sure want to logout?")
                .typeface(Functions.getLatoFont(HomeActivity.this), Functions.getLatoFont(HomeActivity.this))
                .positiveText("Yes")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        Functions.closeSession(HomeActivity.this);
                    }
                })
                .negativeText("No")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void redirectIntent(Intent intent) {
        if (Build.VERSION.SDK_INT >= 21) {

            LinearLayout linearprofile_trans = (LinearLayout) drawerDialogView.findViewById(R.id.linearprofile_trans);
            String transitionName = "profile_trans";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this, linearprofile_trans, transitionName);
            startActivity(intent, transitionActivityOptions.toBundle());

        } else {
            startActivity(intent);
        }
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
                    Toast.makeText(HomeActivity.this, "Report page(s) will come", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };


}
