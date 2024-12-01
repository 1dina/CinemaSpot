package com.example.cinemaspot.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.cinemaspot.ui.theme.Poppins

@Composable
fun HomeScreen(movieViewModel: MovieViewModel) {

    val topRatedMovies by movieViewModel.topRatedMovies.collectAsState()
    val nowPlayingMovies by movieViewModel.nowPlayingMovies.collectAsState()
    val upcomingMovies by movieViewModel.upcomingMovies.collectAsState()
    val popularMovies by movieViewModel.popularMovies.collectAsState()
    val isLoading by movieViewModel.isLoading.collectAsState()
    val topFiveMovies by movieViewModel.topFiveMovies.collectAsState()

    LaunchedEffect(Unit) {
        movieViewModel.fetchAllMovies()
    }

    HomeScreenContent(
        isLoading,
        topRatedMovies,
        nowPlayingMovies,
        upcomingMovies,
        popularMovies,
        topFiveMovies
    )

}

@Composable
private fun HomeScreenContent(
    isLoading: Boolean,
    topRatedMovies: List<Result>,
    nowPlayingMovies: List<Result>,
    upcomingMovies: List<Result>,
    popularMovies: List<Result>,
    topFiveMovies: List<Result>
) {
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }

    val tabTitles = listOf("Now Playing", "Upcoming", "Top Rated", "Popular")

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF242A32)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (isLoading) {
                LoadingScreen()
            } else {
                TopHeader()
                Spacer(modifier = Modifier.height(24.dp))
                MovieList(topFiveMovies)
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
                            0 -> MoviesGrid(movies = nowPlayingMovies)
                            1 -> MoviesGrid(movies = upcomingMovies)
                            2 -> MoviesGrid(movies = topRatedMovies)
                            3 -> MoviesGrid(movies = popularMovies)
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
            text = "Fetching data from server",
            color = Color.White,
            style = TextStyle(
                fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 18.sp
            )
        )
        CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp), color = Color.White)
    }
}


@Composable
private fun MovieList(topFiveMovies: List<Result>) {
    LazyRow(
        contentPadding = PaddingValues(start = 8.dp)
    ) {
        itemsIndexed(topFiveMovies) { index, movie ->
            MovieCardWithNumber(movie, index = index + 1)
        }
    }
}

@Composable
fun MoviesGrid(movies: List<Result>) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(movies) { movie ->
            MovieCard(movie)
        }
    }
}

@Composable
fun MovieCardWithNumber(
    movie: Result,
    index: Int,
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

    Box(
        modifier = Modifier
            .height(210.dp)
            .padding(horizontal = 16.dp)
            .clip(shape = RoundedCornerShape(16.dp))
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imgUrl)
                .crossfade(true)
                .build(),
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
            modifier = Modifier
                .align(Alignment.BottomStart),
            tint = Color.Unspecified
        )

    }
}

@Composable
fun MovieCard(movie: Result) {
    val imgUrl = BASE_IMAGE_URL + movie.poster_path

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imgUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_placeholder),
        contentDescription = "Movie Poster",
        modifier = Modifier
            .height(144.dp)
            .width(100.dp)
            .padding(horizontal = 6.dp)
            .padding(bottom = 18.dp)
            .clip(shape = RoundedCornerShape(16.dp)),
        contentScale = ContentScale.Crop
    )
}


@Composable
fun TopHeader() {
    var textInput by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 42.dp)
    ) {
        Text(
            text = "What do you want to watch",
            color = Color.White,
            style = TextStyle(
                fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = textInput,
            onValueChange = { textInput = it },
            placeholder = {
                Text(text = "Search", color = Color.Gray)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Gray,
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(24.dp),
            trailingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search icon",
                    tint = Color.Gray,
                    modifier = Modifier.graphicsLayer(
                        rotationZ = 90f
                    )
                )
            },
            textStyle = TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )

        )
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

