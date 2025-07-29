package com.karapetyan.weather.data.api

import com.google.gson.JsonObject
import com.karapetyan.weather.data.network.model.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): WeatherData
}