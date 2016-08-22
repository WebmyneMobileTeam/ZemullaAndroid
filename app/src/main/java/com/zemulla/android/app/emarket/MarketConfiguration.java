package com.zemulla.android.app.emarket;

import com.zemulla.android.app.R;

import java.util.ArrayList;

/**
 * Created by dhruvil on 27-07-2016.
 */

public class MarketConfiguration {

    public static String[] names = {"AirTime Topup Zambia", "Pay Electricity Bill", "Direct Recharge", "DSTV Payment"};
    //    private String[] colors = {"#80CBC4", "#A5D6A7", "#C5E1A5", "#E6EE9C", "#80CBC4", "#A5D6A7", "#C5E1A5"};
//    private String[] colorsDark = {"#00695C", "#2E7D32", "#558B2F", "#9E9D24", "#00695C", "#2E7D32", "#558B2F"};
    private static String[] colors = {"#ffffff", "#EEEEEE", "#EEEEEE", "#ffffff"};
    private static String[] colorsDark = {"#494949", "#9E9E9E", "#9E9E9E", "#494949"};


    private static TOPUP_IDTILE[] ids = {TOPUP_IDTILE.AIRTIME_TOPUP, TOPUP_IDTILE.ELECTRICITY, TOPUP_IDTILE.DTH, TOPUP_IDTILE.DSTV};
    private static int[] icons = {R.drawable.ic_action_account_balance_wallet,
            R.drawable.ic_action_account_balance_wallet,
            R.drawable.ic_action_account_balance_wallet,
            R.drawable.ic_action_account_balance_wallet};

    public enum TOPUP_IDTILE {

        AIRTIME_TOPUP,
        ELECTRICITY,
        DTH,
        DSTV,
    }

    public MarketConfiguration() {
    }

    public static ArrayList<MarketTileBean> getAllMarketOptions() {

        ArrayList<MarketTileBean> arr = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            MarketTileBean tile = new MarketTileBean();
            tile.setTileName(names[i]);
            tile.setBackgroundColor(colors[i]);
            tile.setId(ids[i]);
            tile.setTileIcon(icons[i]);
            tile.setBackgroundColorDark(colorsDark[i]);
            arr.add(tile);
        }
        return arr;
    }
}
