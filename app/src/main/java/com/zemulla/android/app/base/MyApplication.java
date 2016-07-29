package com.zemulla.android.app.base;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.zemulla.android.app.R;

/**
 * Created by krishnakumar on 22-07-2016.
 */
public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
