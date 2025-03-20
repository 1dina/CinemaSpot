package com.example.cinemaspot.ui.routes

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cinemaspot.data.local.EncryptedPrefsManager
import com.example.cinemaspot.ui.screens.auth.AuthViewModel
import com.example.cinemaspot.ui.screens.auth.login.LoginScreen
import com.example.cinemaspot.ui.screens.auth.splash.SplashScreen

object AuthRoutes {
    const val SPLASH = "splash"
    const val AUTH = "auth"
}

@Composable
fun AuthNavigation(
    navController: NavHostController,
    prefsManager: EncryptedPrefsManager,
    context: Context
) {

    NavHost(navController = navController, startDestination = AuthRoutes.SPLASH) {
        composable(AuthRoutes.SPLASH) { SplashScreen(navController, prefsManager, context) }
        composable(AuthRoutes.AUTH) {
            val authViewModel = hiltViewModel<AuthViewModel>()
            LoginScreen(authViewModel, context)
        }
    }
}
