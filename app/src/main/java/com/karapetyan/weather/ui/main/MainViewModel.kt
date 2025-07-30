package com.karapetyan.weather.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karapetyan.weather.data.network.model.WeatherData
import com.karapetyan.weather.data.repository.NetworkRepository
import com.karapetyan.weather.data.repository.WeatherRepositoryRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.onSuccess

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
                val result = networkRepository.getWeatherData(name)
                result.onSuccess {
                    updateCity(it)
                }.onFailure {
                    // show error toast
                }
            }
        }
    }

    private suspend fun updateCity(weatherData: WeatherData) {
        repositoryRoom.updateCity(weatherData)
    }
}