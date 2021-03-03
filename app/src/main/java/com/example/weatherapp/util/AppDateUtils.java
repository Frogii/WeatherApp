package com.example.weatherapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppDateUtils {
    public static Date convertLongToDate(Long time) {
        return new Date(time * 1000L);
    }

    public static String dateToString(Date date) {
        return new SimpleDateFormat("dd MMM EEEE", Locale.getDefault()).format(date);
    }
}
