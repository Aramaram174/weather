package com.karapetyan.weather.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.karapetyan.weather.R

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    onBackPress: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    val cityName by searchViewModel.currentCityName.observeAsState("")

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.search_top_view_color))
            .padding(top = 32.dp)
    ) {
        val (titleHint, searchView, cancelButton, lineView, cityButton) = createRefs()

        Text(
            text = stringResource(R.string.search_title_hint),
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier
                .constrainAs(titleHint) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
        )

        SearchView(
            searchText = searchText,
            onTextChanged = { searchText = it },
            onSearch = {
                searchViewModel.searchCity(searchText)
            },
            onClear = { searchText = "" },
            modifier = Modifier
                .constrainAs(searchView) {
                    top.linkTo(titleHint.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(cancelButton.start, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = stringResource(id = R.string.cancel),
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier
                .clickable {
                    onBackPress()
                    searchViewModel.setCityName("")
                }
                .constrainAs(cancelButton) {
                    top.linkTo(searchView.top)
                    bottom.linkTo(searchView.bottom)
                    end.linkTo(parent.end, margin = 16.dp)
                }
        )

        LineView(
            modifier = Modifier
                .constrainAs(lineView) {
                    top.linkTo(searchView.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(1.dp)
                .fillMaxWidth()
        )

        if (cityName.isNotEmpty()) {
            Button(
                onClick = {
                    searchViewModel.insertSelectedCityToDb()
                    searchViewModel.setCityName("")
                    onBackPress()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(8.dp)
                    .constrainAs(cityButton) {
                        top.linkTo(lineView.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Text(
                    text = cityName,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun LineView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.White)
    )
}
