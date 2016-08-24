package com.zemulla.android.app.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.meetic.marypopup.MaryPopup;
import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.user.GetWalletDetailAPI;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.emarket.MarketActivity;
import com.zemulla.android.app.fundtransfer.FundTransferActivity;
import com.zemulla.android.app.helper.Functions;
import com.zemulla.android.app.helper.PrefUtils;
import com.zemulla.android.app.model.account.login.LoginResponse;
import com.zemulla.android.app.model.user.getwalletdetail.GetWalletDetailResponse;
import com.zemulla.android.app.report.ReportsActivity;
import com.zemulla.android.app.topup.TopupActivity;
import com.zemulla.android.app.transaction.topup.TopUpTransactionHistoryActivity;
import com.zemulla.android.app.user.ChangePasswordActivity;
import com.zemulla.android.app.user.ContactUsActivity;
import com.zemulla.android.app.user.KYCActivity;
import com.zemulla.android.app.user.UserProfileActivity;
import com.zemulla.android.app.widgets.DrawerDialogView;
import com.zemulla.android.app.widgets.TfTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.linearTop)
    LinearLayout linearTop;
    @BindView(R.id.effectiveBalance)
    TfTextView effectiveBalance;
    @BindView(R.id.availableBalance)
    TfTextView availableBalance;

    private Toolbar toolbar;
    private GridLayout gridHomeOptions;
    private ArrayList<HomeTileBean> tiles;
    MaryPopup popup;
    private DrawerDialogView drawerDialogView;
    private RelativeLayout btnTransactionHistory;
    private LoginResponse loginResponse;
    private ImageView imgProfilePic;
    Unbinder unbinder;
    private GetWalletDetailAPI getWalletDetailAPI;
    private float IMAGE_SCALE = 1.5f;
    private Animation rotation;
    private ImageView refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        unbinder = ButterKnife.bind(this);

        loginResponse = PrefUtils.getUserProfile(this);

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

        toolbar.setTitle(loginResponse.getFirstName() + " " + loginResponse.getLastName());

        if (TextUtils.isEmpty(loginResponse.getProfilePic())) {
            imgProfilePic.setImageResource(R.drawable.default_user);
        } else {
            Functions.setRoundImage(this, imgProfilePic, loginResponse.getProfilePicURL() + loginResponse.getProfilePic());
        }

        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        gridHomeOptions = (GridLayout) findViewById(R.id.gridHomeOptions);
        btnTransactionHistory = (RelativeLayout) findViewById(R.id.btnTransactionHistory);
        btnTransactionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent history = new Intent(HomeActivity.this, TopUpTransactionHistoryActivity.class);
                startActivity(history);
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        tiles = HomeTileConfiguration.getAllTiles();

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_balance, menu);

        refresh = (ImageView) menu.findItem(R.id.action_refresh).getActionView();
        if (refresh != null) {
            refresh.setImageResource(R.drawable.ic_autorenew_white_24dp);

            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(rotation);
                    getWalletDetail();

                }
            });
        }
        return true;

    }


    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.LOGE("UserID", PrefUtils.getUserID(this) + "");

        getWalletDetail();
    }

    private void getWalletDetail() {

        if (loginResponse != null && loginResponse.getUserID() != 0) {

            getWalletDetailAPI.getWalletDetail(String.valueOf(loginResponse.getUserID()), getWalletDetailResponseAPIListener);
        }
    }


    APIListener<GetWalletDetailResponse> getWalletDetailResponseAPIListener = new APIListener<GetWalletDetailResponse>() {
        @Override
        public void onResponse(Response<GetWalletDetailResponse> response) {

            try {
                if (response.isSuccessful()) {
                    GetWalletDetailResponse getWalletDetailResponse = response.body();
                    effectiveBalance.setText(String.format("%s %.2f", AppConstant.ZMW, getWalletDetailResponse.getEffectiveBalance()));
                    availableBalance.setText(String.format("%s %.2f", AppConstant.ZMW, getWalletDetailResponse.getAvailableBalance()));
                    PrefUtils.setBALANCE(HomeActivity.this, getWalletDetailResponse);
                    rotation.cancel();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<GetWalletDetailResponse> call, Throwable t) {

        }
    };

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
        getWalletDetailAPI = new GetWalletDetailAPI();
        rotation = AnimationUtils.loadAnimation(this, R.anim.rotation);
        rotation.setRepeatCount(Animation.INFINITE);
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
                    Intent iReport = new Intent(HomeActivity.this, ReportsActivity.class);
                    startActivity(iReport);
                    break;


            }
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        }
    };


}
