package com.karapetyan.weather.data.api

import com.karapetyan.weather.data.network.model.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String = "f4948314b992da783412f827df85d1f1",
        @Query("units") units: String = "metric"
    ): WeatherData
}