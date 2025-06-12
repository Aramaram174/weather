package com.karapetyan.weather.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

//    @SuppressLint("SimpleDateFormat")
//    fun calcCurrentTimeWithTimeZone(miliseconds: Long): String {
//        return SimpleDateFormat("HH:mm").format(Date(Calendar.getInstance().timeInMillis + (miliseconds * 1000)))
//    }

    @SuppressLint("SimpleDateFormat")
    fun calcCurrentTimeWithTimeZone(miliseconds: Long): String {
        return SimpleDateFormat("HH:mm").format(Date( miliseconds * 1000))
    }

    fun calcCurrentTimeWithTimeZoneee(miliseconds: Long): String {
        val timeZone = TimeZone.getDefault()
        return timeZone.getOffset(miliseconds).toString()
    }

//    DateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.US);
//    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
//    String text = formatter.format(new Date(millis));

}