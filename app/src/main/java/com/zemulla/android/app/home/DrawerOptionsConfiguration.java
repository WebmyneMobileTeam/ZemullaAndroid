package com.zemulla.android.app.home;

import com.zemulla.android.app.R;

import java.util.ArrayList;

/**
 * Created by dhruvil on 28-07-2016.
 */

public class DrawerOptionsConfiguration {

    // edit this values as per your need
    public String[] options = {"View Profile", "Update KYC", "Change Password", "Contact Zemulla", "Logout"};

    public int[] icons = {R.drawable.ic_action_account_balance_wallet,
            R.drawable.ic_action_account_balance_wallet,
            R.drawable.ic_action_account_balance_wallet,
            R.drawable.ic_action_account_balance_wallet,
            R.drawable.ic_action_account_balance_wallet};

    public OptionID[] ids = {OptionID.VIEW_PROFILE, OptionID.UPDATE_KYC, OptionID.CHANGE_PASSWORD, OptionID.Conatct_Zemulla, OptionID.LOGOUT};

    public enum OptionID {
        VIEW_PROFILE,
        UPDATE_KYC,
        CHANGE_PASSWORD,
        Conatct_Zemulla,
        LOGOUT
    }

    public ArrayList<DrawerOptionBean> getAllOptions() {

        ArrayList<DrawerOptionBean> arr = new ArrayList<>();
        for (int i = 0; i < options.length; i++) {
            DrawerOptionBean optionBean = new DrawerOptionBean();
            optionBean.setOptionName(options[i]);
            optionBean.setIcon(icons[i]);
            optionBean.setId(ids[i]);
            arr.add(optionBean);
        }
        return arr;
    }

}
