package com.zemulla.android.app.helper;

import android.content.Context;
import android.widget.Toast;

import com.zemulla.android.app.R;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Created by raghavthakkar on 31-08-2016.
 */
public class RetrofitErrorHelper {

    public static void showErrorMsg(Throwable throwable, Context context) {

        if (throwable instanceof IOException) {
            Toast.makeText(context, context.getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else if (throwable instanceof SocketTimeoutException) {
            Toast.makeText(context, context.getResources().getString(R.string.time_out), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
        }
    }
}
