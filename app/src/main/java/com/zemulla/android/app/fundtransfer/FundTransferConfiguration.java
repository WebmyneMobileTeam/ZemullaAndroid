package com.zemulla.android.app.fundtransfer;

import com.zemulla.android.app.R;

import java.util.ArrayList;

/**
 * Created by dhruvil on 27-07-2016.
 */

public class FundTransferConfiguration {

    public String[] names = {"Zemulla Wallet", "MTN", "Airtel Money", "Zoona Cash", "Bank Transfer"};
    //    private String[] colors = {"#80CBC4", "#A5D6A7", "#C5E1A5", "#E6EE9C", "#80CBC4", "#A5D6A7", "#C5E1A5"};
//    private String[] colorsDark = {"#00695C", "#2E7D32", "#558B2F", "#9E9D24", "#00695C", "#2E7D32", "#558B2F"};
    private String[] colors = {"#ffffff", "#EEEEEE", "#EEEEEE", "#ffffff", "#ffffff"};
    private String[] colorsDark = {"#494949", "#9E9E9E", "#9E9E9E", "#494949", "#494949"};


    private FundTransfer_IDTILE[] ids = {FundTransfer_IDTILE.Zemulla_Wallet, FundTransfer_IDTILE.MTN,
            FundTransfer_IDTILE.Airtel_Money, FundTransfer_IDTILE.Zoona_Cash, FundTransfer_IDTILE.Bank_Transfer};
    private int[] icons = {R.drawable.ic_action_account_balance_wallet,
            R.drawable.ic_action_account_balance_wallet,
            R.drawable.ic_action_account_balance_wallet,
            R.drawable.ic_action_account_balance_wallet,
            R.drawable.ic_action_account_balance_wallet};

    public enum     FundTransfer_IDTILE {

        Zemulla_Wallet,
        MTN,
        Airtel_Money,
        Zoona_Cash,
        Bank_Transfer
    }

    public FundTransferConfiguration() {
    }

    public ArrayList<FundTransferTileBean> getAllFundTransferOptions() {

        ArrayList<FundTransferTileBean> arr = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            FundTransferTileBean tile = new FundTransferTileBean();
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
