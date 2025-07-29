package com.karapetyan.weather.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    @SuppressLint("SimpleDateFormat")
    fun calcCurrentTimeWithTimeZone(miliseconds: Long): String {
        return SimpleDateFormat("HH:mm").format(Date( miliseconds * 1000))
    }
}