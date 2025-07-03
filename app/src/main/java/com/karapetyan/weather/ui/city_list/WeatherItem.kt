package com.karapetyan.weather.ui.city_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karapetyan.weather.R
import com.karapetyan.weather.data.network.model.WeatherData
import com.karapetyan.weather.utils.DateUtils
import com.karapetyan.weather.utils.MathFunctions

@Composable
fun WeatherItem(
    weatherData: WeatherData,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.item_background),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .weight(1f)
            ) {
                Text(
                    text = DateUtils.formatUnixTime(weatherData.timezone),
                    fontSize = 12.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.roboto_medium))
                )

                Text(
                    text = weatherData.name,
                    fontSize = 22.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                )
            }

            Text(
                text = "${MathFunctions.roundNumber(weatherData.main.temp)}°C",
                fontSize = 32.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.roboto_light)),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
    }
}