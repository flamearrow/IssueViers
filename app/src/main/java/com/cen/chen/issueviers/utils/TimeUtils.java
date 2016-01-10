package com.cen.chen.issueviers.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by flamearrow on 1/7/16.
 */
public class TimeUtils {
    private static final String TAG = "TimeUtils";
    public static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    //    "EEE MMM dd HH:mm:ss z yyyy";
    private static SimpleDateFormat SDF = new SimpleDateFormat(DATE_FORMAT);

    public static Calendar parseFromString(String str) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(SDF.parse(str));
        } catch (ParseException e) {
            Log.d(TAG, "Incorrect string format: " + str);
            e.printStackTrace();
        }
        return cal;
    }
}
