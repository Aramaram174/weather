package com.karapetyan.weather.ui.pager

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.karapetyan.weather.data.repository.WeatherRepositoryRoom
import com.karapetyan.weather.data.network.model.WeatherData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class PagerViewModel(
    private val repositoryRoom: WeatherRepositoryRoom
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    suspend fun getAllCities(): LiveData<MutableList<WeatherData>> {
        return repositoryRoom.getAll()
    }
}
