package com.zemulla.android.app.helper;

import android.content.Context;

import com.google.gson.Gson;
import com.zemulla.android.app.model.login.LoginResponse;

/**
 * Created by xitij on 17-03-2015.
 */
public class PrefUtils {

    private static final String USER_PROFILE = "USER_PROFILE";
    private static final String USER_ID = "USER_ID";
    private static final String IS_LOGGED_IN = "IS_LOGGED_IN";


    public static void setLoggedIn(Context context, boolean isLoggedIn) {
        Prefs.with(context).save(IS_LOGGED_IN, isLoggedIn);
    }

    public static boolean getLoggedIn(Context context) {
        return Prefs.with(context).getBoolean(IS_LOGGED_IN, false);
    }

    public static void setUserProfile(Context context, LoginResponse response) {
        String toJson = new Gson().toJson(response);
        setUserID(context, response.getUserID());
        Prefs.with(context).save(USER_PROFILE, toJson);
    }

    public static void setUserID(Context context, int userID) {
        Prefs.with(context).save(USER_ID, userID);
    }

    public static int getUserID(Context context) {
        return Prefs.with(context).getInt(USER_ID, 0);
    }

    public static LoginResponse getUserProfile(Context context) {
        Gson gson = new Gson();
        LoginResponse response = null;

        String jsonString = Prefs.with(context).getString(USER_PROFILE, "");
        try {
            response = gson.fromJson(jsonString, LoginResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }


}
