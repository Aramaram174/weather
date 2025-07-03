package com.karapetyan.weather.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.karapetyan.weather.R
import com.karapetyan.weather.ui.theme.WeatherAppTheme

@Composable
fun SearchView(
    searchText: String,
    onTextChanged: (String) -> Unit,
    onSearch: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(54.dp)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    color = colorResource(id = R.color.search_background),
                    shape = RoundedCornerShape(8.dp)
                )
        )

        ConstraintLayout(
            modifier = Modifier
                .matchParentSize()
                .padding(horizontal = 5.dp)
        ) {
            val (icon, textField, closeBtn) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .padding(start = 5.dp)
                    .constrainAs(icon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )

            TextField(
                value = searchText,
                onValueChange = onTextChanged,
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.search),
                        color = Color.White.copy(alpha = 0.5f)
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    disabledTextColor = Color.White.copy(alpha = 0.2f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedPlaceholderColor = Color.White.copy(alpha = 0.5f),
                    unfocusedPlaceholderColor = Color.White.copy(alpha = 0.5f)
                ),
                textStyle = TextStyle(fontSize = 16.sp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    onSearch()
                }),
                modifier = Modifier
                    .constrainAs(textField) {
                        start.linkTo(icon.end, margin = 5.dp)
                        end.linkTo(closeBtn.start, margin = 5.dp)
                        top.linkTo(parent.top, margin = 5.dp)
                        bottom.linkTo(parent.bottom, margin = 5.dp)
                        width = Dimension.fillToConstraints
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier
                    .size(14.dp)
                    .clickable { onClear() }
                    .constrainAs(closeBtn) {
                        end.linkTo(parent.end, margin = 5.dp)
                        top.linkTo(textField.top)
                        bottom.linkTo(textField.bottom)
                    }
            )
        }
    }
}

@Preview(showBackground = true, name = "Weather City Loading")
@Composable
fun WeatherCityScreenLoadingPreview() {
    WeatherAppTheme {
        SearchView(
            "",
            onTextChanged = {},
            onSearch = {},
            onClear = {},
            modifier = Modifier
        )
    }
}