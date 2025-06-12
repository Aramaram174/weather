package com.karapetyan.weather.data.repository

import androidx.lifecycle.LiveData
import com.karapetyan.weather.data.db.WeatherDao
import com.karapetyan.weather.data.network.model.WeatherData

interface WeatherRepositoryRoom {
    suspend fun saveCity(weatherData: WeatherData)
    suspend fun updateCity(weatherData: WeatherData)
    suspend fun deleteCity(cityName: String)
    suspend fun getAll(): LiveData<MutableList<WeatherData>>
    suspend fun getAllCityNames(): List<String>
    suspend fun getCity(cityName: String?): LiveData<WeatherData>
    suspend fun getCount(): LiveData<Int>
}

class WeatherRepositoryRoomImpl(private val weatherDao: WeatherDao) : WeatherRepositoryRoom {
    override suspend fun saveCity(weatherData: WeatherData) = weatherDao.insert(weatherData)
    override suspend fun updateCity(weatherData: WeatherData) = weatherDao.update(weatherData)
    override suspend fun deleteCity(cityName: String) = weatherDao.delete(cityName)
    override suspend fun getAll(): LiveData<MutableList<WeatherData>> = weatherDao.getAll()
    override suspend fun getAllCityNames(): List<String> = weatherDao.getAllCityNames()
    override suspend fun getCity(cityName: String?): LiveData<WeatherData> = weatherDao.getCity(cityName)
    override suspend fun getCount(): LiveData<Int> = weatherDao.getCount()
}

