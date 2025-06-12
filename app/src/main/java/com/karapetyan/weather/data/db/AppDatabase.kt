package com.karapetyan.weather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.karapetyan.weather.data.network.model.WeatherData

@Database(entities = [WeatherData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}