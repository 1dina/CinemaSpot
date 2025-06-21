package com.example.cinemaspot.ui.common

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cinemaspot.ui.screens.home.TopHeader
import kotlinx.coroutines.delay

@Composable
fun MovieListPlaceholder() {
    LazyRow(contentPadding = PaddingValues(start = 8.dp)) {
        items(5) { index ->
            val animatedColor = AnimatedBorderColor()

            Box(
                modifier = Modifier
                    .height(210.dp)
                    .width(144.dp)
                    .padding(horizontal = 16.dp)
                    .border(1.dp, animatedColor, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray.copy(alpha = 0.15f))
            )
        }
    }
}

@Composable
fun MoviesGridPlaceholder() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp)
            .padding(top = 20.dp)
    ) {
        items(12) {
            val animatedColor = AnimatedBorderColor()

            Box(
                modifier = Modifier
                    .height(144.dp)
                    .width(100.dp)
                    .padding(horizontal = 6.dp)
                    .padding(bottom = 18.dp)
                    .border(1.dp, animatedColor, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray.copy(alpha = 0.15f))
            )
        }
    }
}
@Composable
fun HomeScreenLoadingPlaceholder(tabTitles: List<String>) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopHeader(onSearchBarClick = {})
        Spacer(modifier = Modifier.height(24.dp))
        MovieListPlaceholder()
        Spacer(modifier = Modifier.height(32.dp))
        CustomTabLayout(
            modifier = Modifier.fillMaxWidth(),
            tabTitles = tabTitles,
            selectedTabIndex = 0
        ) {
        }
        MoviesGridPlaceholder()
    }
}
@Composable
fun WishListItemPlaceholder() {
    val animatedColor = AnimatedBorderColor()
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .height(120.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .width(95.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, animatedColor, RoundedCornerShape(16.dp))
                .background(Color.Gray.copy(alpha = 0.2f))
        )

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth(0.5f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Gray.copy(alpha = 0.2f))
            )
            Spacer(modifier = Modifier.weight(0.3f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color.Gray.copy(alpha = 0.2f), CircleShape)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Box(
                    modifier = Modifier
                        .height(12.dp)
                        .width(40.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color.Gray.copy(alpha = 0.2f), CircleShape)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Box(
                    modifier = Modifier
                        .height(12.dp)
                        .width(60.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color.Gray.copy(alpha = 0.2f), CircleShape)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Box(
                    modifier = Modifier
                        .height(12.dp)
                        .width(40.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
            }
        }
    }
}

@Composable
fun AnimatedBorderColor(): Color {
    val colors = listOf(
        Color(0xFF833AB4),
        Color(0xFFE1306C),
        Color(0xFFF77737),
        Color(0xFFFFDC80)
    )
    val transition = rememberInfiniteTransition()
    val index = remember { mutableStateOf(0) }

    val color by transition.animateColor(
        initialValue = colors[index.value],
        targetValue = colors[(index.value + 1) % colors.size],
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Restart
        )
    )
    LaunchedEffect(color) {
        delay(1000)
        index.value = (index.value + 1) % colors.size
    }
    return color
}