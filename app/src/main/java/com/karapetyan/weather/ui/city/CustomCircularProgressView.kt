package com.karapetyan.weather.ui.city

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karapetyan.weather.R
import com.karapetyan.weather.ui.theme.WeatherAppTheme

@Composable
fun CustomCircularProgressView(
    modifier: Modifier = Modifier,
    progress: Float,
    maxValue: Float = 100f,
    primaryColor: Color,
    trackColor: Color,
    strokeWidth: Dp = 8.dp,
    centerText: String? = null,
    centerTextStyle: TextStyle = TextStyle(fontSize = 18.sp, color = Color.Black, textAlign = TextAlign.Center),
    centerIcon: Painter? = null,
    iconSize: Dp = 24.dp,
    iconTint: Color = Color.Unspecified,
) {
    val strokeWidthPx = with(LocalDensity.current) { strokeWidth.toPx() }
    val currentProgress = if (progress.isNaN() || progress.isInfinite()) 0f else progress
    val currentMaxValue = if (maxValue.isNaN() || maxValue.isInfinite() || maxValue == 0f) 100f else maxValue
    val sweepAngle = (currentProgress / currentMaxValue) * 360f

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier.fillMaxSize() // Canvas fills the Box
        ) {
            val canvasSize = size.minDimension
            val arcRadius = (canvasSize / 2) - (strokeWidthPx / 2)
            val arcActualSize = Size(arcRadius * 2, arcRadius * 2)
            val topLeftOffset = Offset(
                x = (size.width - arcActualSize.width) / 2,
                y = (size.height - arcActualSize.height) / 2
            )

            drawArc(
                color = trackColor,
                startAngle = -90f, // Start from the top
                sweepAngle = 360f, // Full circle
                useCenter = false, // Do not fill the center
                style = Stroke(width = strokeWidthPx),
                size = arcActualSize,
                topLeft = topLeftOffset
            )

            if (sweepAngle > 0f) { // Only draw if there's progress
                drawArc(
                    color = primaryColor,
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round), // Round cap for progress
                    size = arcActualSize,
                    topLeft = topLeftOffset
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            centerIcon?.let {
                Icon(
                    painter = it,
                    contentDescription = centerText ?: "Progress Icon", // More descriptive
                    modifier = Modifier.size(iconSize),
                    tint = iconTint
                )
            }
            centerText?.let {
                if (centerIcon != null) Spacer(modifier = Modifier.height(2.dp)) // Small space if icon exists
                Text(
                    text = it,
                    style = centerTextStyle
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, name = "Light Preview")
@Composable
fun CustomCircularProgressViewPreviewLight() {
    WeatherAppTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomCircularProgressView(
                modifier = Modifier.size(100.dp),
                progress = 75f,
                maxValue = 100f,
                primaryColor = Color(0xFF4CAF50), // Green
                trackColor = Color.LightGray,
                strokeWidth = 10.dp,
                centerText = "75%",
                centerTextStyle = TextStyle(fontSize = 20.sp, color = Color.DarkGray)
            )

            CustomCircularProgressView(
                modifier = Modifier.size(80.dp),
                progress = 1020f,
                maxValue = 1086f, // Example max pressure
                primaryColor = Color(0xFF00ACC1), // Cyan
                trackColor = Color.Gray.copy(alpha = 0.3f),
                strokeWidth = 8.dp,
                centerText = "1020",
                centerTextStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF006064)),
                centerIcon = painterResource(id = R.drawable.icon_pressure),
                iconTint = Color(0xFF00ACC1),
                iconSize = 20.dp
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF212121, name = "Dark Preview")
@Composable
fun CustomCircularProgressViewPreviewDark() {
    WeatherAppTheme(darkTheme = true) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomCircularProgressView(
                modifier = Modifier.size(100.dp),
                progress = 60f,
                maxValue = 100f,
                primaryColor = Color(0xFFBB86FC),
                trackColor = Color.DarkGray,
                strokeWidth = 10.dp,
                centerText = "60%",
                centerTextStyle = TextStyle(fontSize = 20.sp, color = Color.White)
            )
            CustomCircularProgressView(
                modifier = Modifier.size(80.dp),
                progress = 65f,
                maxValue = 100f,
                primaryColor = Color(0xFF03DAC5),
                trackColor = Color.Gray.copy(alpha = 0.5f),
                strokeWidth = 8.dp,
                centerText = "65%",
                centerTextStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                iconTint = Color(0xFF03DAC5),
                iconSize = 20.dp
            )
        }
    }
}