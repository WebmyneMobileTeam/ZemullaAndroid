package com.zemulla.android.app.home;

import com.zemulla.android.app.R;

import java.util.ArrayList;

/**
 * Created by dhruvil on 27-07-2016.
 */

public class HomeTileConfiguration {

    private static String[] names = {"Topup", "Fund Transfer", "E-Market", "Reports"};
    //private String[] colors = {"#80CBC4","#A5D6A7","#C5E1A5","#E6EE9C"};
    private static String[] colors = {"#4ca8b3", "#329ca8", "#329ca8", "#4ca8b3"};
    private static String[] colorsDark = {"#00695C", "#2E7D32", "#558B2F", "#9E9D24"};
//
//    private String[] colorsDark = {"#ffffff","#ffffff","#ffffff","#ffffff"};

    private static IDTILE[] ids = {IDTILE.TOPUP, IDTILE.FUND_TRANSFER, IDTILE.EMARKET, IDTILE.REPORTS};
    private static int[] icons = {R.drawable.ic_action_account_balance_wallet, R.drawable.ic_action_account_balance_wallet, R.drawable.earth, R.drawable.ic_action_account_balance_wallet};

    public enum IDTILE {
        TOPUP,
        FUND_TRANSFER,
        EMARKET,
        REPORTS
    }


    public HomeTileConfiguration() {
    }

    public static ArrayList<HomeTileBean> getAllTiles() {
        ArrayList<HomeTileBean> arr = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            HomeTileBean tile = new HomeTileBean();
            tile.setTileName(names[i]);
            tile.setBackgroundColor(colors[i]);
            tile.setId(ids[i]);
            tile.setTileIcon(icons[i]);
            // tile.setBackgroundColorDark(colorsDark[i]);
            arr.add(tile);
        }
        return arr;
    }


}
