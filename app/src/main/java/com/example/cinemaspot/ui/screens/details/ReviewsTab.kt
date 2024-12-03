package com.example.cinemaspot.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun ReviewsTab(modifier: Modifier = Modifier, movieReviews: MovieReviewsResponse) {
    if (movieReviews.results.isNotEmpty()) {
        ReviewsList(modifier = modifier, movieReviews = movieReviews)
    }else {
        Text(text = "No Reviews", style = TextStyle(
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ), color = Color.White,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center)
    }

}

@Composable
fun ReviewsList(modifier: Modifier, movieReviews: MovieReviewsResponse) {
    LazyColumn(modifier = modifier) {
        items(movieReviews.results) {
            ReviewItem(modifier = modifier, result = it)
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
                ), color = Color.White,
                modifier = modifier.padding(bottom = 8.dp)
            )
            Text(
                text = result.content, style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                ), color = Color.White
            )
            HorizontalDivider(
                thickness = 0.2.dp,
                color = Color.Gray,
                modifier = modifier.padding(vertical = 8.dp)
            )

        }


    }
}

