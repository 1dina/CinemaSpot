package com.example.cinemaspot.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.cinemaspot.R
import com.example.cinemaspot.ui.routes.AppRoutes

sealed class BottomNavBarScreens(
    val route: String,
    @DrawableRes val itemImage: Int,
    @StringRes val resourceId: Int
) {
    object HomeScreen : BottomNavBarScreens(AppRoutes.HOME, R.drawable.ic_home, R.string.home)
    object SearchScreen :
        BottomNavBarScreens(AppRoutes.SEARCH, R.drawable.ic_search, R.string.search)

    object WatchListScreen :
        BottomNavBarScreens(AppRoutes.WATCH_LIST, R.drawable.ic_watch_list, R.string.watchlist)

    companion object {
        fun getBottomNavBarItems(): List<BottomNavBarScreens> {
            val items = listOf(
                HomeScreen, SearchScreen, WatchListScreen
            )
            return items
        }
    }
}