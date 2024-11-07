package com.example.cinemaspot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.cinemaspot.ui.MovieViewModel
import com.example.cinemaspot.ui.theme.CinemaSpotTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieViewModel: MovieViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            CinemaSpotTheme {
                MovieScreen(movieViewModel = movieViewModel)

            }
        }
    }

}

@Composable
fun MovieScreen(movieViewModel: MovieViewModel) {
    LaunchedEffect(Unit) {
        movieViewModel.fetchTopRatedMovies(page = 1)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Text(
            text = "Top Rated Movies",
            modifier = Modifier.padding(paddingValues)
        )
    }
}
