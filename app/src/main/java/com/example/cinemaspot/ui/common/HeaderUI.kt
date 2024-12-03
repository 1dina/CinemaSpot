package com.example.cinemaspot.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemaspot.R
import com.example.cinemaspot.ui.theme.Poppins

@Composable
fun HeaderUI(headerTitle: String,
             onClickBackButton: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back Button",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { onClickBackButton() }
                .padding(8.dp)
                .size(24.dp)

        )

        Text(
            text = headerTitle,
            modifier = Modifier
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontFamily = Poppins, fontWeight = FontWeight.Medium, fontSize = 18.sp
            )
        )
    }
}

@Composable
fun HeaderUIWithBookmark(
    headerTitle: String,
    onClickBackButton: () -> Unit,
    onBookmarkClick:()->Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back Button",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { onClickBackButton() }
                .size(24.dp),
            tint = Color.Unspecified
        )

        Text(
            text = headerTitle,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
            ,style = TextStyle(
                fontFamily = Poppins, fontWeight = FontWeight.Medium, fontSize = 18.sp
            )

        )

        Icon(
            painter = painterResource(id = R.drawable.ic_bookmark),
            contentDescription = "Bookmark icon",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable { onBookmarkClick() }
                .padding(8.dp)
                .size(24.dp),
            tint = Color.Unspecified)

    }
}

@Preview(showBackground = true)
@Composable
fun LabelUIPreview() {
 HeaderUIWithBookmark("Details", onClickBackButton = {}, onBookmarkClick = {})
}