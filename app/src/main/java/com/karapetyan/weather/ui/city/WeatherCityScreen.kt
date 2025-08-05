package com.karapetyan.weather.ui.city

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.karapetyan.weather.R
import com.karapetyan.weather.data.network.model.WeatherData
import com.karapetyan.weather.utils.DateUtils
import com.karapetyan.weather.utils.MathFunctions

@Composable
fun WeatherCityScreen(cityName: String, viewModel: WeatherCityViewModel? = null) {

    val vm = viewModel ?: return
    val weatherData by vm.getWeatherDataFlow(cityName).collectAsState(initial = null)

    if (weatherData == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.clouds),
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground)
            )
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeatherHeader(weatherData!!)
        Spacer(Modifier.height(24.dp))
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 48.dp)
                .background(
                    color = Color(0x50000000),
                    shape = RoundedCornerShape(11.dp)
                )
                .border(1.dp, Color(0xFF00ACC1), RoundedCornerShape(11.dp))
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                WeatherStatsSection(weatherData!!)
                Spacer(Modifier.height(24.dp))
                WeatherDetailsSection(weatherData!!)
            }
        }
        BannerAdView()
    }
}

@Composable
fun WeatherHeader(data: WeatherData) {
    val fontFamily = FontFamily(Font(R.font.roboto))

    Box(
        modifier = Modifier
            .size(200.dp),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(Color(0x3250514F))
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize() // Fill the oval
        ) {

            Spacer(Modifier.height(8.dp))

            Text(
                text = data.name,
                style = TextStyle(fontSize = if (data.name.length > 11 ) 21.sp else 28.sp, color = Color.White, textAlign = TextAlign.Center, fontFamily = fontFamily)
            )

            Text(
                text = data.weather?.firstOrNull()?.description?.uppercase() ?: "",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    fontFamily = fontFamily
                )
            )

            Text(
                text = "${MathFunctions.roundNumber(data.main.temp)}°C",
                style = TextStyle(fontSize = 64.sp, color = Color.White, fontFamily = fontFamily)
            )
        }
    }
}

@Composable
fun WeatherStatsSection(data: WeatherData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        WeatherStatItem(
            title = stringResource(R.string.pressure),
            value = data.main.pressure.toFloat(),
            maxValue = 1086f,
            iconRes = R.drawable.icon_pressure
        )
        WeatherStatItem(
            title = stringResource(R.string.humidity),
            value = data.main.humidity.toFloat(),
            maxValue = 100f,
            iconRes = R.drawable.icon_humidity
        )
        WeatherStatItem(
            title = stringResource(R.string.clouds),
            value = data.clouds.all.toFloat(),
            maxValue = 100f,
            iconRes = R.drawable.icon_clouds
        )
    }
}

@Composable
fun WeatherStatItem(title: String, value: Float, maxValue: Float, iconRes: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, color = Color.White)
        CustomCircularProgressView(
            progress = value,
            maxValue = maxValue,
            primaryColor = Color(0xFF00ACC1),
            trackColor = Color.White.copy(alpha = 0.3f),
            strokeWidth = 6.dp,
            centerText = "${value.toInt()}",
            centerTextStyle = TextStyle(fontSize = 16.sp, color = Color.White),
            centerIcon = painterResource(id = iconRes),
            iconTint = Color.White,
            iconSize = 20.dp,
            modifier = Modifier.size(80.dp)
        )
    }
}

@Composable
fun WeatherDetailsSection(data: WeatherData) {
    Column(modifier = Modifier.fillMaxWidth()) {
        DetailRow(
            stringResource(R.string.max),
            "${MathFunctions.roundNumber(data.main.temp_max)}°C",
            R.drawable.icon_celsius
        )
        HorizontalDivider(color = Color(0xFF00ACC1).copy(alpha = 0.7f))
        DetailRow(
            stringResource(R.string.min),
            "${MathFunctions.roundNumber(data.main.temp_min)}°C",
            R.drawable.icon_celsius
        )
        DetailRow(
            stringResource(R.string.sunrise),
            DateUtils.calcCurrentTime(6784768534867867432),
            R.drawable.icon_sunrise
        )
        HorizontalDivider(color = Color(0xFF00ACC1).copy(alpha = 0.7f))
        DetailRow(
            stringResource(R.string.sunset),
            DateUtils.calcCurrentTime(6784768534867867432),
            R.drawable.icon_sunset
        )
        DetailRow(
            stringResource(R.string.visibility),
            "${data.visibility / 1000} ${stringResource(R.string.km)}",
            R.drawable.icon_visibility
        )
        HorizontalDivider(color = Color(0xFF00ACC1).copy(alpha = 0.7f))
        DetailRow(
            stringResource(R.string.wind),
            "${data.wind.speed} m/s",
            R.drawable.icon_wind_speed
        )
    }
}

@Composable
fun DetailRow(label: String, value: String, icon: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = Color.White, fontSize = 18.sp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = value, color = Color.White, fontSize = 18.sp)
            Spacer(Modifier.width(8.dp))
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun BannerAdView() {
    AndroidView(
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = "ca-app-pub-3940256099942544/6300978111"
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}