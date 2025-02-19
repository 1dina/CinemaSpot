package com.example.cinemaspot.ui.screens.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.cinemaspot.data.local.EncryptedPrefsManager
import com.example.cinemaspot.ui.routes.AuthNavigation
import com.example.cinemaspot.ui.theme.CinemaSpotTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    @Inject
    lateinit var prefsManager: EncryptedPrefsManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()
            CinemaSpotTheme {
                AuthNavigation(navController, prefsManager, context)
            }

        }
    }
}

