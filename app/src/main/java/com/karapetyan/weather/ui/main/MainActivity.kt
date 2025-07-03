package com.karapetyan.weather.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.karapetyan.weather.ui.city.WeatherCityViewModel
import com.karapetyan.weather.ui.city_list.CityListViewModel
import com.karapetyan.weather.ui.pager.PagerViewModel
import com.karapetyan.weather.ui.search.SearchViewModel
import com.karapetyan.weather.ui.theme.WeatherAppTheme
import com.karapetyan.weather.utils.hideSystemUI
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val pagerViewModel: PagerViewModel by viewModel()
    private val weatherCityViewModel: WeatherCityViewModel by viewModel()
    private val cityListViewModel: CityListViewModel by viewModel()
    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI(this)

        setContent {
            WeatherAppTheme {
                MainActivityContent(
                    pagerViewModel = pagerViewModel,
                    weatherCityViewModel = weatherCityViewModel,
                    cityListViewModel = cityListViewModel,
                    searchViewModel = searchViewModel
                )
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideSystemUI(this)
    }
}
