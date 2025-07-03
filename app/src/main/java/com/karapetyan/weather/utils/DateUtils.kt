package com.karapetyan.weather.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun calcCurrentTime(miliseconds: Long): String {
            return SimpleDateFormat("HH:mm").format(Date(miliseconds * 1000))
        }

        fun formatUnixTime(offsetSeconds: Long): String {
            val utcNowMillis = System.currentTimeMillis()
            val date = Date(utcNowMillis)
            val formatter = SimpleDateFormat("EEEE, MMMM dd, yyyy HH:mm", Locale.ENGLISH)
            val hoursOffset = offsetSeconds / 3600
            val timeZone = "GMT${if (hoursOffset >= 0) "+" else ""}$hoursOffset"
            formatter.timeZone = TimeZone.getTimeZone(timeZone)
            return formatter.format(date) + " " + timeZone
        }

        fun getHourAndMinuteByOffset(offsetSeconds: Long): Pair<Int, Int> {
            val utcNowMillis = System.currentTimeMillis()
            val dateWithOffset = Date(utcNowMillis + (offsetSeconds * 1000))

            val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT")).apply {
                time = dateWithOffset
            }

            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            return hour to minute
        }
    }
}