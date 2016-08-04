package com.zemulla.android.app.base;

import android.app.Application;

import com.zemulla.android.app.R;
import com.zemulla.android.app.constant.AppConstant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by krishnakumar on 22-07-2016.
 */
public class ZemullaApplication extends Application {
    private static ZemullaApplication zemullaApplication;
    private static Retrofit retrofit;

    public static ZemullaApplication getZemullaApplication() {
        return zemullaApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        zemullaApplication = this;
        initRetrofit();

    }

    private void initRetrofit() {

        retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.BASEURLSERVICEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static void setRetrofit(Retrofit retrofit) {
        ZemullaApplication.retrofit = retrofit;
    }
}
