package com.example.cinemaspot.ui.screens.wishList

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cinemaspot.R
import com.example.cinemaspot.data.Constants.BASE_IMAGE_URL
import com.example.cinemaspot.data.models.movies.watchList.Result
import com.example.cinemaspot.ui.common.HeaderUI
import com.example.cinemaspot.ui.routes.AppRoutes
import com.example.cinemaspot.ui.screens.details.LoadingIndicator
import com.example.cinemaspot.ui.theme.Naive
import com.example.cinemaspot.ui.theme.Orange
import com.example.cinemaspot.ui.theme.Poppins

val genreMap = mapOf(
    28 to "Action",
    12 to "Adventure",
    16 to "Animation",
    35 to "Comedy",
    18 to "Drama",
    878 to "Science Fiction",
    53 to "Thriller",
    10749 to "Romance",
    9648 to "Mystery",
    10751 to "Family",
    14 to "Fantasy",
    36 to "History",
    27 to "Horror",
    10402 to "Music",
    99 to "Documentary",
    10752 to "War",
    37 to "Western"
)

fun getGenres(ids: List<Int>): String {
    return ids.mapNotNull { genreMap[it] }.joinToString(", ")
}

@Composable
fun WishListScreen(
    wishListViewModel: WishListViewModel, modifier: Modifier = Modifier,
    onBackIconNavigate: () -> Unit, onItemSelected: (String, Int) -> Unit
) {
    LaunchedEffect(Unit) {
        wishListViewModel.getWatchListMovies()
    }
    val isLoading by wishListViewModel.isLoading.collectAsState()
    val isLoadingAnotherPage by wishListViewModel.isLoadingAnotherPage.collectAsState()
    val watchList = wishListViewModel.watchList.collectAsState().value
    val listState = rememberLazyListState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Naive)
    ) {
        if (isLoading) {
            LoadingIndicator()
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            HeaderUI("Watch list", onClickBackButton = {
                onBackIconNavigate()
            })
            Spacer(modifier = Modifier.size(16.dp))
            LazyColumn(modifier = modifier.fillMaxSize(), state = listState) {
                items(watchList.size) {
                    WishListItem(movie = watchList[it]) { route, id ->
                        onItemSelected(route, id)
                    }
                    if (it == watchList.size - 1) {
                        Log.e("WishListScreen", "Reached the end of the list")
                        wishListViewModel.loadNextPage()
                    }
                }
                item {
                    Log.d("WishListScreen", "Recomposing Loading Indicator: $isLoadingAnotherPage")
                    if (isLoadingAnotherPage && !isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun WishListItem(movie: Result, onItemSelected: (String, Int) -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .height(120.dp)
            .fillMaxWidth()
            .clickable { onItemSelected(AppRoutes.DETAILS, movie.id) }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(BASE_IMAGE_URL + movie.poster_path).crossfade(true).build(),
            contentDescription = "movie poster", contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(120.dp)
                .width(95.dp)
                .clip(RoundedCornerShape(16.dp)),

            )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = movie.title,
                color = Color.White,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = Poppins
            )
            Spacer(modifier = Modifier.weight(0.3f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star), contentDescription = "",
                    tint = Color.Unspecified, modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = movie.vote_average.toString().take(3), color = Orange, fontSize = 12.sp,
                    fontWeight = Bold,
                    fontFamily = Poppins
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_ticket), contentDescription = "",
                    tint = Color.White, modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = getGenres(listOf(movie.genre_ids[0])),
                    color = Color.White,
                    fontFamily = Poppins,
                    fontSize = 12.sp
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendarblank),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = movie.release_date.take(4),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = Poppins
                )
            }
        }
    }
}


