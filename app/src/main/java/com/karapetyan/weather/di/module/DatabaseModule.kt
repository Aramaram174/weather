package com.karapetyan.weather.di.module

import android.app.Application
import androidx.room.Room
import com.karapetyan.weather.data.db.AppDatabase
import com.karapetyan.weather.data.db.WeatherDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

fun provideDatabase(application: Application): AppDatabase {
    return Room.databaseBuilder(application, AppDatabase::class.java, "weather_database")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()
}

fun provideDao(database: AppDatabase): WeatherDao {
    return database.weatherDao()
}