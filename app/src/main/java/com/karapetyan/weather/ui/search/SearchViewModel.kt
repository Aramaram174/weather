package com.karapetyan.weather.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karapetyan.weather.data.network.model.WeatherData
import com.karapetyan.weather.data.repository.NetworkRepository
import com.karapetyan.weather.data.repository.WeatherRepositoryRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import com.karapetyan.weather.data.repository.Result

class SearchViewModel(
    private val networkRepository: NetworkRepository,
    private val repositoryRoom: WeatherRepositoryRoom
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private var currentCity = WeatherData()
    private val _currentCityName: MutableLiveData<String> = MutableLiveData()
    val currentCityName: MutableLiveData<String>
        get() = _currentCityName

    fun searchCity(text: String) {
        viewModelScope.launch {
            when (val result = networkRepository.getWeatherData(text)) {
                is Result.Success -> {
                    currentCity = result.data
                    currentCityName.postValue(currentCity.name)
                }
                is Result.Error -> {
                    currentCityName.postValue(null)
                }
            }
        }
    }

    suspend fun insertSelectedCityToDb() {
        repositoryRoom.saveCity(currentCity)
    }
}
