package com.zemulla.android.app.topup;

import com.zemulla.android.app.R;

import java.util.ArrayList;

/**
 * Created by dhruvil on 27-07-2016.
 */

public class TopupTileConfiguration {

    public static String[] names = {"Cyber Source", "PayPal", "MTN", "Airtel Money", "Zoona Cash", "Bank Transfer", "Our Supported Bank"};
    //    private String[] colors = {"#80CBC4", "#A5D6A7", "#C5E1A5", "#E6EE9C", "#80CBC4", "#A5D6A7", "#C5E1A5"};
//    private String[] colorsDark = {"#00695C", "#2E7D32", "#558B2F", "#9E9D24", "#00695C", "#2E7D32", "#558B2F"};
    private static String[] colors = {"#ffffff", "#EEEEEE", "#EEEEEE", "#ffffff", "#ffffff", "#EEEEEE", "#EEEEEE"};
    private static String[] colorsDark = {"#494949", "#9E9E9E", "#9E9E9E", "#494949", "#494949", "#9E9E9E", "#9E9E9E"};


    private static TOPUP_IDTILE[] ids = {TOPUP_IDTILE.CYBER_SOURCE, TOPUP_IDTILE.PAYPAL, TOPUP_IDTILE.MTN, TOPUP_IDTILE.AIRTEL_MONEY, TOPUP_IDTILE.ZOONA, TOPUP_IDTILE.BANK_TRANSFER, TOPUP_IDTILE.SUPPORTED_BANK};
    private static int[] icons = {R.drawable.ic_action_account_balance_wallet,
            R.drawable.ic_action_account_balance_wallet,
            R.drawable.ic_action_account_balance_wallet,
            R.drawable.ic_action_account_balance_wallet,
            R.drawable.ic_action_account_balance_wallet,
            R.drawable.ic_action_account_balance_wallet,
            R.drawable.ic_action_account_balance_wallet};

    public enum TOPUP_IDTILE {

        CYBER_SOURCE,
        PAYPAL,
        MTN,
        AIRTEL_MONEY,
        ZOONA,
        BANK_TRANSFER,
        SUPPORTED_BANK

    }


    public TopupTileConfiguration() {
    }

    public static ArrayList<TopupTileBean> getAllTopupOptions() {

        ArrayList<TopupTileBean> arr = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            TopupTileBean tile = new TopupTileBean();
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
