package com.zemulla.android.app.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.meetic.marypopup.MaryPopup;
import com.zemulla.android.app.R;
import com.zemulla.android.app.user.UserProfileActivity;
import com.zemulla.android.app.widgets.DrawerDialogView;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private GridLayout gridHomeOptions;
    private ArrayList<HomeTileBean> tiles;
    MaryPopup popup;
    private DrawerDialogView drawerDialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dhruvil Patel");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setNavigationIcon(R.drawable.ic_action_person);
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
                switch (id) {

                    case VIEW_PROFILE:

                        LinearLayout linearprofile_trans = (LinearLayout)drawerDialogView.findViewById(R.id.linearprofile_trans);


                        Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
                        String transitionName = "profile_trans";

                        if (android.os.Build.VERSION.SDK_INT >= 21) {
                            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this, linearprofile_trans, transitionName);
                            startActivity(intent, transitionActivityOptions.toBundle());

                        } else {
                            startActivity(intent);
                        }

                        break;

                    case UPDATE_KYC:

                        break;

                    case CHANGE_PASSWORD:

                        break;

                    case LOGOUT:

                        break;

                }
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up rectangle, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
