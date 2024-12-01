package com.example.cinemaspot.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemaspot.R
import com.example.cinemaspot.ui.screens.HeaderUIWithBookmark
import com.example.cinemaspot.ui.theme.Naive
import com.example.cinemaspot.ui.theme.NavieLight
import com.example.cinemaspot.ui.theme.Orange

@Composable
fun DetailsScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Naive)
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Column(modifier = modifier.padding(16.dp)) {
                HeaderUIWithBookmark("Detail", onClickBackButton = {//pop back stack
                }, onBookmarkClick = { // add to bookmark
                })
            }
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .height(360.dp)
            ) {
                Box {
                    LoadingIndicator()
                    /*Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "",
                        modifier = modifier
                            .fillMaxSize()
                            .padding(bottom = 56.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 0.dp,
                                    bottomStart = 8.dp,
                                    bottomEnd = 8.dp
                                )
                            ),
                        contentScale = ContentScale.FillBounds
                    ) */
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
                        Row(modifier = modifier.padding(horizontal = 6.dp, vertical = 6.dp)) {
                            Icon(
                                painter = painterResource(R.drawable.ic_star),
                                contentDescription = "",
                                tint = Color.Unspecified,
                                modifier = modifier.padding(start = 4.dp),

                                )
                            Text(
                                text = "9.9",
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
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "",
                        modifier = modifier.clip(RoundedCornerShape(16.dp))
                    )
                    Text(
                        text = "Movie Namedfdfkdfkdkfdkfkdkfdkfkdfkd",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = modifier
                            .align(Alignment.Bottom)
                            .padding(
                                start = 8.dp, end = 8.dp, top = 16.dp
                            ),
                        maxLines = 2, overflow = TextOverflow.Ellipsis
                    )
                }
            }


        }

    }
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

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    DetailsScreen()

}