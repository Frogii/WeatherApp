package com.example.weatherapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppDateUtils {

    public static String dayNum = "dd";
    public static String dayNum_day_month = "d MMM EEEE";
    public static String hours_minutes = "hh:mm aaa EE";

    public static String longDateToPattern(Long time, String pattern) {
        Date date = new Date(time * 1000L);
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }
}
