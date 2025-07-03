package com.karapetyan.weather.ui.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.karapetyan.weather.R
import com.karapetyan.weather.ui.city.WeatherCityScreen
import com.karapetyan.weather.ui.city.WeatherCityViewModel
import com.karapetyan.weather.utils.DateUtils.Companion.getHourAndMinuteByOffset
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun PagerScreen(
    pagerViewModel: PagerViewModel,
    weatherCityViewModel: WeatherCityViewModel,
    onOpenCityList: () -> Unit,
    initialCityName: String = ""
) {
    val cityList by pagerViewModel.cityList.collectAsState()

    val lazyListState = rememberLazyListState()
    val pagerState = rememberPagerState(pageCount = { cityList.size })

    val currentPage = pagerState.currentPage
    val currentCity = cityList.getOrNull(currentPage)

    val time: Pair<Int, Int> = currentCity?.let {
        getHourAndMinuteByOffset(it.timezone)
    } ?: run {
        val calendar = Calendar.getInstance()
        calendar.get(Calendar.HOUR_OF_DAY) to calendar.get(Calendar.MINUTE)
    }

    val gradientColors = getTimeBasedGradientColors(time.first, time.second)
    val contrastDotColor = getContrastingColor(gradientColors.first())
    val borderColor = getContrastingColor(gradientColors.first())

    LaunchedEffect(pagerState.currentPage) {
        delay(100)
        lazyListState.animateScrollToItem(pagerState.currentPage)
    }

    LaunchedEffect(cityList) {
        if (initialCityName.isNotEmpty()) {
            val index = cityList.indexOfFirst { it.name == initialCityName }
            if (index != -1) {
                pagerState.scrollToPage(index)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = gradientColors
                )
            ),

        ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                WeatherCityScreen(cityList[page].name, weatherCityViewModel)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                LazyRow(
                    state = lazyListState,
                    modifier = Modifier
                        .padding(vertical = 18.dp, horizontal = 55.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    itemsIndexed(cityList) { index, _ ->
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .then(
                                    if (pagerState.currentPage == index)
                                        Modifier.background(contrastDotColor, CircleShape)
                                    else
                                        Modifier.border(2.dp, borderColor, CircleShape)
                                )
                        )
                    }
                }
            }
        }

        IconButton(
            onClick = onOpenCityList,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(48.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_list),
                contentDescription = "City List",
                tint = contrastDotColor
            )
        }
    }
}

fun getContrastingColor(backgroundColor: Color): Color {
    return if (backgroundColor.luminance() > 0.5f) Color("#000080".toColorInt()).copy(alpha = 0.7f) else Color.White
}

fun getTimeBasedGradientColors(hour: Int, minute: Int): List<Color> {
    val slot = hour * 2 + if (minute < 30) 0 else 1
    return when (slot) {
        in 10..11 -> listOf("#020111", "#191621")
        in 12..13 -> listOf("#191621", "#FFA500")
        in 14..17 -> listOf("#87CEFA", "#FFFFFF")
        in 18..31 -> listOf("#87CEEB", "#E0FFFF")
        in 32..35 -> listOf("#FBD786", "#f7797d")
        in 36..39 -> listOf("#FFA17F", "#00223E")
        in 40..43 -> listOf("#141E30", "#243B55")
        else -> listOf("#0F2027", "#2C5364")
    }.map { Color(it.toColorInt()) }
}