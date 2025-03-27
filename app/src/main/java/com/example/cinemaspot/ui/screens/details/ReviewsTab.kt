package com.example.cinemaspot.ui.screens.details

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cinemaspot.R
import com.example.cinemaspot.data.Constants
import com.example.cinemaspot.data.models.movies.reviews.MovieReviewsResponse
import com.example.cinemaspot.data.models.movies.reviews.ResultXXX
import com.example.cinemaspot.ui.theme.Poppins

@Composable
fun ReviewsTab(
    modifier: Modifier = Modifier, movieReviews: MovieReviewsResponse,
    isLoading: Boolean, onEndReached: () -> Unit
) {
    if (movieReviews.results.isNotEmpty()) {
        ReviewsList(
            modifier = modifier,
            movieReviews = movieReviews,
            isLoading = isLoading
        ) {
            onEndReached()
        }
    } else {
        Text(
            text = "No Reviews", style = TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            ), color = White,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }

}

@Composable
fun ReviewsList(
    modifier: Modifier,
    movieReviews: MovieReviewsResponse,
    isLoading: Boolean,
    onEndReached: () -> Unit,
) {
    LazyColumn(modifier = modifier, state = rememberLazyListState()) {
        items(movieReviews.results.size) { it ->
            ReviewItem(modifier = modifier, result = movieReviews.results[it])

            if (it == movieReviews.results.size - 1) {
                Log.e("ReviewTap", "Reached the end of the list")
                onEndReached()
            }
        }
        item {
            Log.d("ReviewTap", "Recomposing Loading Indicator: $isLoading")
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ReviewItem(modifier: Modifier, result: ResultXXX) {
    val personAvatar = (Constants.BASE_IMAGE_URL + result.author_details.avatar_path)
    Row(modifier = modifier.padding(vertical = 8.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(personAvatar)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.user),
            contentDescription = "",
            modifier = modifier
                .size(36.dp)
                .clip(CircleShape)
        )
        Column(modifier = modifier.padding(start = 8.dp)) {
            Text(
                text = result.author, style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                ), color = White,
                modifier = modifier.padding(bottom = 8.dp)
            )
            Text(
                text = result.content, style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                ), color =White
            )
            HorizontalDivider(
                thickness = 0.2.dp,
                color = Color.Gray,
                modifier = modifier.padding(vertical = 8.dp)
            )

        }


    }
}

