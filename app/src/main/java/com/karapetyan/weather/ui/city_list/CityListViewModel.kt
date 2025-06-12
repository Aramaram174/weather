package com.karapetyan.weather.ui.city_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.karapetyan.weather.data.repository.WeatherRepositoryRoom
import com.karapetyan.weather.data.network.model.WeatherData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class CityListViewModel(private val repositoryRoom: WeatherRepositoryRoom) : ViewModel(),
    CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    suspend fun getAllCities(): LiveData<MutableList<WeatherData>> {
        return repositoryRoom.getAll()
    }

    suspend fun deleteCity(cityName: String) {
        return repositoryRoom.deleteCity(cityName)
    }

    suspend fun getCount(): LiveData<Int> {
        return repositoryRoom.getCount()
    }
}
