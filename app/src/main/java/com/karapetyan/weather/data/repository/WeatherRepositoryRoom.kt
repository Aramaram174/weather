package com.karapetyan.weather.data.repository

import com.karapetyan.weather.data.db.WeatherDao
import com.karapetyan.weather.data.network.model.WeatherData
import kotlinx.coroutines.flow.Flow

interface WeatherRepositoryRoom {
    suspend fun updateCity(weatherData: WeatherData)
    suspend fun deleteCity(cityName: String)
    suspend fun getAllCityNames(): List<String>
    fun insertWeatherData(weatherData: WeatherData)
    fun getWeatherData(cityName: String?): Flow<WeatherData>
    fun getAllCities(): Flow<List<WeatherData>>
}

class WeatherRepositoryRoomImpl(private val weatherDao: WeatherDao) : WeatherRepositoryRoom {
    override suspend fun updateCity(weatherData: WeatherData) = weatherDao.update(weatherData)
    override suspend fun deleteCity(cityName: String) = weatherDao.delete(cityName)
    override suspend fun getAllCityNames(): List<String> = weatherDao.getAllCityNames()
    override fun insertWeatherData(weatherData: WeatherData) = weatherDao.insertWeatherData(weatherData)
    override fun getWeatherData(cityName: String?): Flow<WeatherData> = weatherDao.getWeatherData(cityName)
    override fun getAllCities(): Flow<List<WeatherData>> = weatherDao.getAllCities()
}

