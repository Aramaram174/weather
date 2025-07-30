package com.karapetyan.weather.data.repository

import com.karapetyan.weather.data.api.ApiService
import com.karapetyan.weather.data.network.model.WeatherData

interface NetworkRepository {
    suspend fun getWeatherData(name: String): Result<WeatherData>
}

class NetworkRepositoryImpl(private val apiService: ApiService) : NetworkRepository {
    override suspend fun getWeatherData(name: String): Result<WeatherData> {
        return try {
            val response = apiService.getWeather(name, API_KEY, "metric")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        private const val API_KEY = "f4948314b992da783412f827df85d1f1"
    }
}