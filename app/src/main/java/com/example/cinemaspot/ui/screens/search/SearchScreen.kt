package com.example.cinemaspot.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cinemaspot.data.Constants.BASE_IMAGE_URL
import com.example.cinemaspot.data.models.movies.Result
import com.example.cinemaspot.ui.common.HeaderUI
import com.example.cinemaspot.ui.routes.AppRoutes
import com.example.cinemaspot.ui.screens.details.LoadingIndicator
import com.example.cinemaspot.ui.theme.Grey
import com.example.cinemaspot.ui.theme.Naive
import com.example.cinemaspot.ui.theme.NavieLight
import com.example.cinemaspot.ui.theme.Poppins

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier, onBackIconNavigate: () -> Unit = {},
    onMovieClicked: (String, Int) -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    val searchViewModel = hiltViewModel<MovieSearchViewModel>()
    val resultList by searchViewModel.resultList.collectAsState()
    val isLoading by searchViewModel.isLoading.collectAsState()
    LaunchedEffect(inputText) {
        searchViewModel.updateQuery(inputText)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Naive)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            HeaderUI("Search", onClickBackButton = { onBackIconNavigate() })
            Spacer(modifier = modifier.size(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        NavieLight,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(2.dp, Grey, RoundedCornerShape(16.dp))
                    .padding(2.dp)
            ) {
                TextField(
                    value = inputText,
                    onValueChange = { it ->
                        inputText = it
                    },
                    placeholder = {
                        Text(
                            "Search for movies..", color = Grey,
                            fontFamily = Poppins,
                            maxLines = 1
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = NavieLight,
                        unfocusedContainerColor = Naive,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = White,
                        unfocusedTextColor = White,
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
            }
            Spacer(modifier = modifier.size(24.dp))
            if (isLoading) LoadingIndicator()
            else
                LazyColumn {
                    items(resultList.size) { index ->
                        ResultItem(result = resultList[index]) { route, id ->
                            onMovieClicked(route, id)
                        }
                    }
                }
        }

    }
}

@Composable
fun ResultItem(result: Result, onMovieClicked: (String, Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .clickable { onMovieClicked(AppRoutes.DETAILS, result.id) }, colors =
        CardDefaults.cardColors(containerColor = Naive)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(BASE_IMAGE_URL + result.poster_path).crossfade(true).build(),
                contentDescription = "movie poster", contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .width(95.dp)
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
            )
            Text(
                text = result.title, fontSize = 16.sp,
                color = White, modifier = Modifier.padding(start = 12.dp),
                maxLines = 1, overflow = TextOverflow.Ellipsis,
                fontFamily = Poppins
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen( onMovieClicked = {_,_ ->}, onBackIconNavigate = {})

}