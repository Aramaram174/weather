package com.karapetyan.weather.ui.city

import androidx.lifecycle.ViewModel
import com.karapetyan.weather.data.network.model.WeatherData
import com.karapetyan.weather.data.repository.WeatherRepositoryRoom
import kotlinx.coroutines.flow.Flow

class WeatherCityViewModel(private var repositoryRoom: WeatherRepositoryRoom) : ViewModel() {

    fun getWeatherDataFlow(city: String): Flow<WeatherData?> {
        return repositoryRoom.getWeatherData(city)
    }
}
