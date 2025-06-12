package com.karapetyan.weather.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karapetyan.weather.data.network.model.WeatherData
import com.karapetyan.weather.data.repository.NetworkRepository
import com.karapetyan.weather.data.repository.Result
import com.karapetyan.weather.data.repository.WeatherRepositoryRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel(
    private val networkRepository: NetworkRepository,
    private val repositoryRoom: WeatherRepositoryRoom
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    init {
        fetchWeatherData()
    }

    fun fetchWeatherData() {
        updateWeatherData()
    }

    private suspend fun getAllCityNames(): List<String> {
        return repositoryRoom.getAllCityNames()
    }

    private fun updateWeatherData() {
        launch {
            if (getAllCityNames().isNotEmpty()) {
                launch {
                    updateAllCityWeathers()
                }
            }
        }
    }

    private suspend fun updateAllCityWeathers() {
        for (name in getAllCityNames()) {
            viewModelScope.launch {
                when (val result = networkRepository.getWeatherData(name)) {
                    is Result.Success -> {
                        updateCity(result.data)
                    }
                    is Result.Error -> {}
                }
            }
        }
    }

    private suspend fun updateCity(weatherData: WeatherData) {
        repositoryRoom.updateCity(weatherData)
    }
}