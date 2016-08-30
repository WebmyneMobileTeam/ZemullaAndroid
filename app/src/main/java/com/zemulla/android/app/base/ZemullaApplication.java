package com.zemulla.android.app.base;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zemulla.android.app.constant.AppConstant;
import com.zemulla.android.app.helper.DatabaseHandler;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by krishnakumar on 22-07-2016.
 */
public class ZemullaApplication extends Application {
    private static ZemullaApplication zemullaApplication;
    private static Retrofit retrofit;
    private static Gson gson;

    public static ZemullaApplication getZemullaApplication() {
        return zemullaApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        zemullaApplication = this;
        initDataBase();
        initGson();
        initRetrofit();
       // initStetho();

    }

//    private void initStetho() {
//        Stetho.initialize(Stetho.newInitializerBuilder(getApplicationContext())
//                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(getApplicationContext()))
//                .build());
//    }

    private void initDataBase() {
        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
        try {
            handler.createDataBase();
        } catch (Exception e) {

        }
    }

    private void initGson() {
        gson = new GsonBuilder()
                .setLenient()
                .create();
    }

    public static Gson getGson() {
        return gson;
    }

    private void initRetrofit() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.BASEURLSERVICEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static void setRetrofit(Retrofit retrofit) {
        ZemullaApplication.retrofit = retrofit;
    }
}
