package com.karapetyan.weather.di.module

import com.karapetyan.weather.data.repository.NetworkRepository
import com.karapetyan.weather.data.repository.NetworkRepositoryImpl
import com.karapetyan.weather.data.repository.WeatherRepositoryRoom
import com.karapetyan.weather.data.repository.WeatherRepositoryRoomImpl
import org.koin.dsl.module

val repoModule = module {

    single<NetworkRepository> {
        NetworkRepositoryImpl(apiService = get())
    }

    single<WeatherRepositoryRoom> {
        WeatherRepositoryRoomImpl(weatherDao = get())
    }
}
