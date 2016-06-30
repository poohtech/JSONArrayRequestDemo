package com.example.user.jsonarrayrequestdemo.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.ConnectivityManager;


public class Utils {


    /**
     * Check Connectivity of network.
     */
    public static boolean isOnline(Context context) {
        try {
            if (context == null)
                return false;

            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (cm != null) {
                if (cm.getActiveNetworkInfo() != null) {
                    return cm.getActiveNetworkInfo().isConnected();
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.error("Exception", e);
            return false;
        }

    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static Typeface getFont(Context context, int tag) {
        if (tag == 100) {
            return Typeface.createFromAsset(context.getAssets(),
                    "Roboto-Regular.ttf");
        }
        return Typeface.DEFAULT;
    }

}
