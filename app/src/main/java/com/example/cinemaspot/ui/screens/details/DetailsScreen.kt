package com.example.cinemaspot.ui.screens.details

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cinemaspot.R
import com.example.cinemaspot.data.Constants.BASE_IMAGE_URL
import com.example.cinemaspot.ui.CustomTabLayout
import com.example.cinemaspot.ui.screens.HeaderUIWithBookmark
import com.example.cinemaspot.ui.theme.Naive
import com.example.cinemaspot.ui.theme.NavieLight
import com.example.cinemaspot.ui.theme.Orange
import com.example.cinemaspot.ui.theme.Poppins


@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier, detailsViewModel: DetailsViewModel,
    movieId: Int
) {
    LaunchedEffect(movieId) {
        detailsViewModel.getMovieDetails(movieId)
    }
    val movieDetails by detailsViewModel.movieDetails.collectAsState()
    val isLoading by detailsViewModel.isLoading.collectAsState()
    val imageBackgroundURL = BASE_IMAGE_URL + movieDetails?.backdrop_path
    val imagePosterURL = BASE_IMAGE_URL + movieDetails?.poster_path
    val tabTitles = listOf("About Movie", "Reviews", "Cast")
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Naive)
    ) {
        if (isLoading) {
            LoadingIndicator()
        } else {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                ) {
                    HeaderUIWithBookmark("Detail", onClickBackButton = {//pop back stack
                    }, onBookmarkClick = { // add to bookmark
                    })
                }
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                        .height(280.dp)
                ) {
                    Box {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageBackgroundURL)
                                .crossfade(true)
                                .build(),
                            contentDescription = "",
                            modifier = modifier
                                .fillMaxSize()
                                .padding(bottom = 56.dp, start = 2.dp, end = 2.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 0.dp,
                                        topEnd = 0.dp,
                                        bottomStart = 8.dp,
                                        bottomEnd = 8.dp
                                    )
                                ),
                            contentScale = ContentScale.FillBounds
                        )
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = NavieLight,
                                contentColor = Orange,
                            ),
                            modifier = modifier
                                .align(Alignment.BottomEnd)
                                .padding(bottom = 64.dp, end = 8.dp)
                        ) {
                            Row(
                                modifier = modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_star),
                                    contentDescription = "",
                                    tint = Color.Unspecified,
                                    modifier = modifier.padding(start = 4.dp),

                                    )
                                Text(
                                    text = movieDetails?.vote_average.toString().take(3),
                                    modifier = modifier.padding(horizontal = 4.dp),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp
                                )
                            }
                        }

                    }
                    Row(
                        modifier = modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 16.dp)
                            .fillMaxHeight(0.6f)

                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imagePosterURL)
                                .crossfade(true)
                                .build(),
                            contentDescription = "",
                            modifier = modifier.clip(RoundedCornerShape(16.dp))
                        )
                        Text(
                            text = movieDetails?.title ?: "",
                            color = Color.White,
                            modifier = modifier
                                .align(Alignment.Bottom)
                                .padding(
                                    start = 8.dp, end = 8.dp, top = 16.dp, bottom = 4.dp
                                ),
                            maxLines = 2, overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontFamily = Poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            )
                        )
                    }
                }

                Row(
                    modifier = modifier
                        .padding(vertical = 24.dp)
                        .height(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MovieInfoDetails(
                        R.drawable.ic_calendarblank,
                        movieDetails?.release_date?.take(4),
                        modifier
                    )
                    VerticalDivider(
                        thickness = 1.dp,
                        color = Color.Gray,
                        modifier = modifier.padding(horizontal = 8.dp)
                    )
                    MovieInfoDetails(
                        R.drawable.ic_clock,
                        movieDetails?.runtime.toString() + " Minutes",
                        modifier
                    )
                    VerticalDivider(
                        thickness = 1.dp,
                        color = Color.Gray,
                        modifier = modifier.padding(horizontal = 8.dp)
                    )
                    MovieInfoDetails(
                        R.drawable.ic_ticket,
                        movieDetails?.genres?.first()?.name,
                        modifier
                    )

                }
                CustomTabLayout(tabTitles = tabTitles, selectedTabIndex = selectedCategoryIndex) {
                    selectedCategoryIndex = it
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 18.dp)
                        .padding(top = 20.dp)
                ) {
                    when (selectedCategoryIndex) {
                        0 -> TabText(text = movieDetails?.overview ?: "")
                        1 -> TabText(text = "")
                        2 -> TabText(text = "")
                    }
                }
            }

        }
    }
}

@Composable
private fun MovieInfoDetails(
    @DrawableRes icon: Int,
    text: String?,
    modifier: Modifier
) {
    Icon(
        painter = painterResource(id = icon),
        contentDescription = "",
        tint = Color.Unspecified
    )

    Text(
        text = text ?: "",
        color = Color.Gray,
        modifier = modifier.padding(start = 8.dp),
        style = TextStyle(
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    )
}

@Composable
private fun TabText(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal
        ),
        fontSize = 12.sp, color = Color.White
    )
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(64.dp))
    }
}

