package com.example.cinemaspot.ui.screens.home

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cinemaspot.R
import com.example.cinemaspot.data.Constants.BASE_IMAGE_URL
import com.example.cinemaspot.data.models.movies.Result
import com.example.cinemaspot.ui.common.CustomTabLayout
import com.example.cinemaspot.ui.common.HomeScreenLoadingPlaceholder
import com.example.cinemaspot.ui.routes.AppRoutes
import com.example.cinemaspot.ui.theme.Poppins
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    movieViewModel: MovieViewModel,
    onNavigationCallBack: (String, Int) -> Unit,
    onSearchBarClick: () -> Unit
) {

    val topRatedMovies by movieViewModel.topRatedMovies.collectAsState()
    val nowPlayingMovies by movieViewModel.nowPlayingMovies.collectAsState()
    val upcomingMovies by movieViewModel.upcomingMovies.collectAsState()
    val popularMovies by movieViewModel.popularMovies.collectAsState()
    val isLoading by movieViewModel.isLoading.collectAsState()
    val topFiveMovies by movieViewModel.topFiveMovies.collectAsState()

    LaunchedEffect(Unit) {
        if (topRatedMovies.isEmpty() && nowPlayingMovies.isEmpty() && upcomingMovies.isEmpty()
            && popularMovies.isEmpty()
        ) movieViewModel.fetchAllMovies()
    }

    HomeScreenContent(
        isLoading, topRatedMovies, nowPlayingMovies, upcomingMovies, popularMovies, topFiveMovies,
        onAnyItemClick = { route, movieId -> onNavigationCallBack(route, movieId) },
        onSearchBarClick = { onSearchBarClick() },movieViewModel)
}

@Composable
private fun HomeScreenContent(
    isLoading: Boolean,
    topRatedMovies: List<Result>,
    nowPlayingMovies: List<Result>,
    upcomingMovies: List<Result>,
    popularMovies: List<Result>,
    topFiveMovies: List<Result>,
    onAnyItemClick: (String, Int) -> Unit,
    onSearchBarClick: () -> Unit,
    movieViewModel: MovieViewModel
) {
    var selectedCategoryIndex by rememberSaveable { mutableIntStateOf(0) }

    val tabTitles = listOf("Now Playing", "Upcoming", "Top Rated", "Popular")

    Surface(
        modifier = Modifier.fillMaxSize(), color = Color(0xFF242A32)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                HomeScreenLoadingPlaceholder(tabTitles)
            } else {
                TopHeader { onSearchBarClick() }
                Spacer(modifier = Modifier.height(24.dp))
                MovieList(topFiveMovies) { id -> onAnyItemClick(AppRoutes.DETAILS, id) }
                Spacer(modifier = Modifier.height(32.dp))
                Column {
                    CustomTabLayout(
                        modifier = Modifier.fillMaxWidth(),
                        tabTitles = tabTitles,
                        selectedTabIndex = selectedCategoryIndex
                    ) {
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
                            0 -> MoviesGrid(movies = nowPlayingMovies, onItemClick = { id ->
                                onAnyItemClick(AppRoutes.DETAILS, id)
                            }) {
                                movieViewModel.loadNextPage("NowPlaying")
                            }

                            1 -> MoviesGrid(movies = upcomingMovies, onItemClick = { id ->
                                onAnyItemClick(AppRoutes.DETAILS, id)
                            }) {
                                movieViewModel.loadNextPage("Upcoming")
                            }

                            2 -> MoviesGrid(movies = topRatedMovies, onItemClick = { id ->
                                onAnyItemClick(AppRoutes.DETAILS, id)
                            }) {
                                movieViewModel.loadNextPage("TopRated")
                            }

                            3 -> MoviesGrid(movies = popularMovies, onItemClick = { id ->
                                onAnyItemClick(AppRoutes.DETAILS, id)
                            }) {
                                movieViewModel.loadNextPage("Popular")
                            }
                        }

                    }
                    }

                }
            }
        }
    }


@Composable
private fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Fetching data from server", color = Color.White, style = TextStyle(
                fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 18.sp
            )
        )
        CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp), color = Color.White)
    }
}


@Composable
private fun MovieList(topFiveMovies: List<Result>, onItemClick: (Int) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(start = 8.dp)
    ) {
        itemsIndexed(topFiveMovies) { index, movie ->
            MovieCardWithNumber(movie, index = index + 1) { id ->
                onItemClick(id)
            }
        }
    }
}

@Composable
fun MoviesGrid(movies: List<Result>, onItemClick: (Int) -> Unit, loadNextPage: () -> Unit) {
    val listState = rememberLazyGridState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex != null && lastVisibleIndex >= movies.size - 1) {
                    loadNextPage()
                }
            }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {
        items(movies) { movie ->
            MovieCard(movie) { id -> onItemClick(id) }
        }
    }
}


@Composable
fun MovieCardWithNumber(
    movie: Result, index: Int, onItemClick: (Int) -> Unit
) {

    val imgUrl = BASE_IMAGE_URL + movie.poster_path
    val vectorResource = when (index) {
        1 -> R.drawable.number_one
        2 -> R.drawable.number_two
        3 -> R.drawable.number_three
        4 -> R.drawable.number_four
        5 -> R.drawable.number_five
        else -> R.drawable.ic_launcher_background
    }

    Box(modifier = Modifier
        .height(210.dp)
        .padding(horizontal = 16.dp)
        .clip(shape = RoundedCornerShape(16.dp))
        .clickable { onItemClick(movie.id) }) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(imgUrl).crossfade(true).build(),
            placeholder = painterResource(R.drawable.ic_placeholder),
            contentDescription = "Movie Poster",
            modifier = Modifier
                .fillMaxHeight()
                .width(144.dp)
                .padding(bottom = 20.dp, start = 12.dp)
                .clip(shape = RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
        )


        Icon(
            painter = painterResource(id = vectorResource),
            contentDescription = "Number Icon",
            modifier = Modifier.align(Alignment.BottomStart),
            tint = Color.Unspecified
        )

    }
}

@Composable
fun MovieCard(movie: Result, onItemClick: (Int) -> Unit) {
    val imgUrl = BASE_IMAGE_URL + movie.poster_path

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(imgUrl).crossfade(true).build(),
        placeholder = painterResource(R.drawable.ic_placeholder),
        contentDescription = "Movie Poster",
        modifier = Modifier
            .height(144.dp)
            .width(100.dp)
            .padding(horizontal = 6.dp)
            .padding(bottom = 18.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable { onItemClick(movie.id) },
        contentScale = ContentScale.Crop
    )
}


@Composable
fun TopHeader(onSearchBarClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 42.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "What do you want to watch", color = Color.White, style = TextStyle(
                    fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 18.sp
                )
            )
            Icon(
                Icons.Default.Search,
                contentDescription = "Search icon",
                tint = Color.White,
                modifier = Modifier
                    .graphicsLayer(
                     rotationZ = 90f
                    )
                    .clickable { onSearchBarClick() }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun TabText(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        ),
        maxLines = 1,
    )
}





