package com.example.cinemaspot.ui.screens.wishList

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
fun WishListScreen( modifier: Modifier = Modifier) {
  Box(modifier = modifier
          .fillMaxSize()
          .background(color = Naive)){
          Column (modifier = modifier.fillMaxSize().padding(16.dp)) {
                  HeaderUI("Watch list", onClickBackButton = {//pop back stack
                  })


          }

  }
}
//@Composable
//fun WishListItem(modifier: Modifier) {
//
//}

@Preview(showBackground = true)
@Composable
fun WishListScreenPreview() {
        WishListScreen()

}