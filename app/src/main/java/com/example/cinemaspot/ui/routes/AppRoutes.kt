package com.example.cinemaspot.ui.routes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cinemaspot.ui.screens.details.DetailsScreen
import com.example.cinemaspot.ui.screens.details.DetailsViewModel
import com.example.cinemaspot.ui.screens.home.HomeScreen
import com.example.cinemaspot.ui.screens.home.MovieViewModel

object AppRoutes {
    const val HOME = "home"
    const val DETAILS = "details"
}

@Composable
fun AppNavGraph(navController: NavController) {
    NavHost(navController = navController as NavHostController, startDestination = AppRoutes.HOME) {
        composable(AppRoutes.HOME) {
            val movieViewModel = hiltViewModel<MovieViewModel>()
            HomeScreen(movieViewModel = movieViewModel)
            { route, movieId ->
                navController.navigate("$route/$movieId")
            }
        }
        composable(
            route = "${AppRoutes.DETAILS}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            val movieId = it.arguments?.getInt("movieId")!!
            val detailsViewModel = hiltViewModel<DetailsViewModel>()
            DetailsScreen(detailsViewModel = detailsViewModel , movieId = movieId)
        }
    }

}