package com.karapetyan.weather.ui.pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.karapetyan.weather.data.network.model.WeatherData
import com.karapetyan.weather.data.repository.WeatherRepositoryRoom
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class PagerViewModel(
    repositoryRoom: WeatherRepositoryRoom
) : ViewModel() {

    val cityList: StateFlow<List<WeatherData>> =
        repositoryRoom.getAllCities()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}
