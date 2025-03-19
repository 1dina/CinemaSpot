package com.example.cinemaspot.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinemaspot.ui.common.HeaderUI
import com.example.cinemaspot.ui.theme.Naive

@Composable
fun SearchScreen( modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(color = Naive)){
        Column (modifier = modifier.fillMaxSize().padding(16.dp)) {
            HeaderUI("Search", onClickBackButton = {//pop back stack
            })


        }

    }
}


@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen()

}