package com.example.cinemaspot.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.cinemaspot.data.Constants
import com.example.cinemaspot.data.models.movies.cast.Cast
import com.example.cinemaspot.data.models.movies.cast.MovieCastResponse
import com.example.cinemaspot.ui.theme.Poppins

@Composable
fun CastTab(modifier: Modifier = Modifier , cast : MovieCastResponse){
   CastList(modifier = modifier, cast = cast)
}

@Composable
fun CastList (modifier: Modifier , cast : MovieCastResponse){
    LazyVerticalGrid( columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize()){
        items(cast.cast.size){
            CastItem(modifier = modifier.size(100.dp), cast = cast.cast[it])
        }
    }
}
@Composable
fun CastItem (modifier: Modifier, cast :Cast){
    val personPicture = (Constants.BASE_IMAGE_URL + cast.profile_path )
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)) {
        AsyncImage( model = ImageRequest.Builder(LocalContext.current)
            .data(personPicture)
            .crossfade(true)
            .build(),
            error = painterResource(R.drawable.ic_placeholder),
            contentDescription = "",
            modifier = modifier
                .size(24.dp)
                .clip(RoundedCornerShape(8.dp)))
        Text(text = cast.name,style = TextStyle(
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        ), color = Color.White)
    }
}