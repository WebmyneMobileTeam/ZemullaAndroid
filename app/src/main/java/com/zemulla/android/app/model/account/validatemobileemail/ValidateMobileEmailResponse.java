package com.zemulla.android.app.model.account.validatemobileemail;

import com.zemulla.android.app.model.base.BaseSmallCaseResponse;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class ValidateMobileEmailResponse extends BaseSmallCaseResponse {


    private boolean result;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }


}

