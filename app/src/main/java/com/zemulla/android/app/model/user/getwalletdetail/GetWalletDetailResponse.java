package com.zemulla.android.app.model.user.getwalletdetail;

import com.zemulla.android.app.model.base.BaseResponse;

/**
 * Created by raghavthakkar on 09-08-2016.
 */
public class GetWalletDetailResponse extends BaseResponse {


    private double AvailableBalance;
    private double EffectiveBalance;
    public double getAvailableBalance() {
        return AvailableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        AvailableBalance = availableBalance;
    }

    public double getEffectiveBalance() {
        return EffectiveBalance;
    }

    public void setEffectiveBalance(double effectiveBalance) {
        EffectiveBalance = effectiveBalance;
    }




}
