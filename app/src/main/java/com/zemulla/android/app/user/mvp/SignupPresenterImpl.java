package com.zemulla.android.app.user.mvp;

import android.content.Context;

/**
 * Created by sagartahelyani on 02-08-2016.
 */
public class SignupPresenterImpl implements SignupPresenter {

    private Context context;
    private SignupView signupView;

    public SignupPresenterImpl(Context context, SignupView signupView) {
        this.context = context;
        this.signupView = signupView;
    }
}
