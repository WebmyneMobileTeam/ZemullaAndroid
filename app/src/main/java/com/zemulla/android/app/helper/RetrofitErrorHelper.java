package com.zemulla.android.app.helper;

import android.content.Context;

import com.zemulla.android.app.R;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Created by raghavthakkar on 31-08-2016.
 */
public class RetrofitErrorHelper {

    public static void showErrorMsg(Throwable throwable, Context context) {

        if (throwable instanceof IOException) {
            Functions.showError(context, context.getResources().getString(R.string.no_internet_connection), false);
        } else if (throwable instanceof SocketTimeoutException) {
            Functions.showError(context, context.getResources().getString(R.string.time_out), false);
        } else {
            Functions.showError(context, context.getResources().getString(R.string.server_error), false);
        }
    }
}
