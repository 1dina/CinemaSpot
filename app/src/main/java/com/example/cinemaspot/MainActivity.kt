package com.example.cinemaspot

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.cinemaspot.data.local.EncryptedPrefsManager
import com.example.cinemaspot.ui.routes.AppNavGraph
import com.example.cinemaspot.ui.theme.CinemaSpotTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val prefManager = EncryptedPrefsManager(this)
            Log.e("session id", "onCreate: ${prefManager.getSessionId()}")
            val navController = rememberNavController()
            CinemaSpotTheme {
                AppNavGraph(navController = navController)

            }
        }
    }

}

