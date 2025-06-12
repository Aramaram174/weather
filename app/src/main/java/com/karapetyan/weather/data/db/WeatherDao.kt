package com.karapetyan.weather.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.karapetyan.weather.data.network.model.WeatherData

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherData: WeatherData)

    @Update
    fun update(weatherData: WeatherData)

    @Query( "DELETE FROM weatherData WHERE name = :nameCity")
    fun delete(nameCity: String)

    @Query("SELECT * FROM weatherData")
    fun getAll(): LiveData<MutableList<WeatherData>>

    @Query("SELECT name FROM weatherData")
    fun getAllCityNames(): List<String>

    @Query("SELECT * FROM weatherData WHERE name = :nameCity")
    fun getCity(nameCity: String?): LiveData<WeatherData>

    @Query("SELECT count(*) FROM weatherData")
    fun getCount(): LiveData<Int>
}