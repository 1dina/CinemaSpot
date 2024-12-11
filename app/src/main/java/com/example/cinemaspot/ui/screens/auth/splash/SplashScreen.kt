package com.example.cinemaspot.ui.screens.auth.splash

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.cinemaspot.data.local.EncryptedPrefsManager
import com.example.cinemaspot.MainActivity
import com.example.cinemaspot.R


@Composable
fun SplashScreen(
    navController: NavHostController,
    prefsManager: EncryptedPrefsManager,
    context: Context
) {
    val sessionId = prefsManager.getSessionId()

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cinema))
    val progress by animateLottieCompositionAsState(composition)

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    LaunchedEffect(progress) {
        if (progress == 1.0f) {
            if (sessionId != null) {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                (context as Activity).finish()
            } else {
                navController.navigate("auth") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF242A32)),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(screenWidth * .7f, screenHeight * 0.4f)
        )
    }
}
