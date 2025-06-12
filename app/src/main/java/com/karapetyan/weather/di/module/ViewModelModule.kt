package com.karapetyan.weather.di.module

import com.karapetyan.weather.ui.city.WeatherCityViewModel
import com.karapetyan.weather.ui.city_list.CityListViewModel
import com.karapetyan.weather.ui.main.MainViewModel
import com.karapetyan.weather.ui.pager.PagerViewModel
import com.karapetyan.weather.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(networkRepository = get(), repositoryRoom = get()) }
    viewModel { PagerViewModel(repositoryRoom = get()) }
    viewModel { WeatherCityViewModel(repositoryRoom = get()) }
    viewModel { CityListViewModel(repositoryRoom = get()) }
    viewModel { SearchViewModel(networkRepository = get(), repositoryRoom = get()) }
}





