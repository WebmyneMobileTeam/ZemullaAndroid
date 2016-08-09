package com.zemulla.android.app.api.account;

import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.base.ZemullaApplication;
import com.zemulla.android.app.model.account.contact.ContactUsRequest;
import com.zemulla.android.app.model.account.contact.ContactUsResopnse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public class ContactUsAPI {
    private AccountAPI accountAPI;
    private APIListener apiListener;

    public ContactUsAPI() {

        accountAPI = ZemullaApplication.getRetrofit().create(AccountAPI.class);
    }

    public void contactUs(final ContactUsRequest contactUsRequest, final APIListener apiListener) {
        Call<ContactUsResopnse> loginResponseCall = accountAPI.ContactMail(contactUsRequest);
        loginResponseCall.enqueue(new Callback<ContactUsResopnse>() {
            @Override
            public void onResponse(Call<ContactUsResopnse> call, Response<ContactUsResopnse> response) {
                apiListener.onResponse(response);
            }

            @Override
            public void onFailure(Call<ContactUsResopnse> call, Throwable t) {
                apiListener.onFailure(call, t);
            }
        });

    }


    public void onDestory() {
        if (accountAPI != null) {
            accountAPI = null;
        }
        if (apiListener != null) {
            apiListener = null;
        }
    }
}
