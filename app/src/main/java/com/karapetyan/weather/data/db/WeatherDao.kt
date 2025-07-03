package com.karapetyan.weather.data.db

import androidx.room.*
import com.karapetyan.weather.data.network.model.WeatherData
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(weatherData: WeatherData)

    @Update
    fun update(weatherData: WeatherData)

    @Query( "DELETE FROM weatherData WHERE name = :nameCity")
    fun delete(nameCity: String)

    @Query("SELECT * FROM weatherData")
    fun getAllCities(): Flow<List<WeatherData>>

    @Query("SELECT name FROM weatherData")
    fun getAllCityNames(): List<String>

    @Query("SELECT * FROM weatherData WHERE name = :nameCity")
    fun getWeatherData(nameCity: String?): Flow<WeatherData>
}