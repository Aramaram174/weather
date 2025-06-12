package com.karapetyan.weather.data.repository

import com.karapetyan.weather.data.api.ApiService
import com.karapetyan.weather.data.network.model.WeatherData

interface NetworkRepository {
    suspend fun getWeatherData(name: String): Result<WeatherData>
}

class NetworkRepositoryImpl(private val apiService: ApiService) : NetworkRepository {
    override suspend fun getWeatherData(name: String): Result<WeatherData> {
        return try {
            val response = apiService.getWeather(name)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}