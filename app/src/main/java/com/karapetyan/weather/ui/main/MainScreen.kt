package com.karapetyan.weather.ui.main

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.karapetyan.weather.R
import com.karapetyan.weather.ui.city.WeatherCityViewModel
import com.karapetyan.weather.ui.city_list.CityListScreen
import com.karapetyan.weather.ui.city_list.CityListViewModel
import com.karapetyan.weather.ui.pager.PagerScreen
import com.karapetyan.weather.ui.pager.PagerViewModel
import com.karapetyan.weather.ui.search.SearchScreen
import com.karapetyan.weather.ui.search.SearchViewModel
import com.karapetyan.weather.utils.InternetConnectionLiveData

@Composable
fun MainActivityContent(
    pagerViewModel: PagerViewModel,
    weatherCityViewModel: WeatherCityViewModel,
    cityListViewModel: CityListViewModel,
    searchViewModel: SearchViewModel
) {
    val context = LocalContext.current
    val isConnected = observeInternetConnection(context)

    if (isConnected) {
        MainNavigation(
            pagerViewModel = pagerViewModel,
            weatherCityViewModel = weatherCityViewModel,
            cityListViewModel = cityListViewModel,
            searchViewModel = searchViewModel
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFD194),
                            Color(0xFF70E1F5)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.internet_connection),
                fontSize = 22.sp,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MainNavigation(
    pagerViewModel: PagerViewModel,
    weatherCityViewModel: WeatherCityViewModel,
    cityListViewModel: CityListViewModel,
    searchViewModel: SearchViewModel
) {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "pager/{cityName}") {
        composable(
            route = "pager/{cityName}",
            arguments = listOf(navArgument("cityName") {
                defaultValue = ""
            })
        ) { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: ""
            PagerScreen(
                pagerViewModel = pagerViewModel,
                weatherCityViewModel = weatherCityViewModel,
                initialCityName = cityName,
                onOpenCityList = {
                    navController.navigate("city_list")
                }
            )
        }
        composable("city_list") {
            CityListScreen(
                cityListViewModel = cityListViewModel,
                onOpenSearchPage = { navController.navigate("search") },
                onCityItemClick = { cityName -> navController.navigate("pager/$cityName") }
            )
        }
        composable("search") {
            SearchScreen(
                searchViewModel = searchViewModel,
                onBackPress = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun observeInternetConnection(context: Context): Boolean {
    val connectionLiveData = remember { InternetConnectionLiveData(context) }
    var isConnected by remember { mutableStateOf(true) }

    DisposableEffect(Unit) {
        val observer = Observer<Boolean> { connected ->
            isConnected = connected
        }
        connectionLiveData.observeForever(observer)

        onDispose {
            connectionLiveData.removeObserver(observer)
        }
    }
    return isConnected
}