package com.zemulla.android.app.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.zemulla.android.app.R;


public class Functions {

    public static SharedPreferences preferences;

    public static SharedPreferences.Editor editor;


    public static String regularFont = "fonts/Roboto-Light.ttf";

    public static String LATO_FONT = "fonts/Lato-Regular.ttf";


    public static DisplayMetrics getDeviceMetrics(Activity context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }


    public static Typeface getRegularTypeFace(Context ctx) {
        Typeface typeface = Typeface.createFromAsset(ctx.getAssets(), regularFont);
        return typeface;
    }

    public static void fireIntent(Context context, Class cls) {
        Intent i = new Intent(context, cls);
        context.startActivity(i);
    }

    public static Typeface getLatoFont(Context ctx) {
        Typeface typeface = Typeface.createFromAsset(ctx.getAssets(), LATO_FONT);
        return typeface;
    }

    public static boolean haveNetworkConnection(Activity context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public static boolean isEmpty(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean emailValidation(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }


    public static int getLength(EditText editText) {
        return editText.getText().toString().trim().length();
    }

    public static String toStingEditText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static void showError(final Context context, String errorMsg, final boolean isFinish) {
        new MaterialDialog.Builder(context)
                .content(errorMsg)
                .typeface(Functions.getLatoFont(context), Functions.getLatoFont(context))
                .positiveText(android.R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        if (isFinish)
                            ((Activity) context).finish();
                    }
                })
                .show();
    }

    public static AdvancedSpannableString getTermsAndConditionAndPrivacyAndContecPolicy(final String text, final String url, final Context context, TextView tv) {

        String fullName = text.trim();
        AdvancedSpannableString advFullName = new AdvancedSpannableString(fullName);
        advFullName.setClickableSpanTo(fullName);
        advFullName.setBold(fullName);
        advFullName.setUnderLine(fullName);

        advFullName.setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark), fullName);
        advFullName.setSpanClickListener(new AdvancedSpannableString.OnClickableSpanListner() {
            @Override
            public void onSpanClick() {

                Toast.makeText(context, "text", Toast.LENGTH_LONG).show();
            }
        });
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        return advFullName;
    }

    public static void initProgressDialog(Context context, ProgressDialog progressDialog) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
    }
}
