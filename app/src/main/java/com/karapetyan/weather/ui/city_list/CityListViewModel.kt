package com.karapetyan.weather.ui.city_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karapetyan.weather.data.network.model.WeatherData
import com.karapetyan.weather.data.repository.WeatherRepositoryRoom
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CityListViewModel(private val repositoryRoom: WeatherRepositoryRoom) : ViewModel() {

    val cityList: StateFlow<List<WeatherData>> = repositoryRoom.getAllCities()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteCity(cityName: String) {
        viewModelScope.launch {
            repositoryRoom.deleteCity(cityName)
        }
    }
}
