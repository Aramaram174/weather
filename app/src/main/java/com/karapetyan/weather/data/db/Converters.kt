package com.karapetyan.weather.data.db

import androidx.room.TypeConverter
import com.karapetyan.weather.data.network.model.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun stringToList(value: String): List<Weather> {
        val listType = object : TypeToken<List<Weather>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun listToString(list: List<Weather>): String {
        val listType = object : TypeToken<List<Weather>>() {}.type
        return Gson().toJson(list, listType)
    }
}