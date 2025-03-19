package com.example.cinemaspot

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cinemaspot.data.local.EncryptedPrefsManager
import com.example.cinemaspot.ui.routes.AppNavGraph
import com.example.cinemaspot.ui.screens.BottomNavBarScreens
import com.example.cinemaspot.ui.theme.Blue
import com.example.cinemaspot.ui.theme.CinemaSpotTheme
import com.example.cinemaspot.ui.theme.Grey
import com.example.cinemaspot.ui.theme.Naive
import com.example.cinemaspot.ui.theme.Poppins
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val prefManager = EncryptedPrefsManager(this)
            Log.e("session id", "onCreate: ${prefManager.getSessionId()}")

            CinemaSpotTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                val bottomBarScreens = BottomNavBarScreens.getBottomNavBarItems()
                val shouldShowBottomBar = bottomBarScreens.any {
                    it.route == navBackStackEntry?.destination?.route
                }

                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomBar) {
                            BottomNavigationBar(
                                navController = navController,
                                items = bottomBarScreens
                            )
                        }
                    }
                ) { paddingValues ->
                    Box(modifier = androidx.compose.ui.Modifier.padding(paddingValues)) {
                        AppNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: androidx.navigation.NavHostController,
    items: List<BottomNavBarScreens>
) {
    NavigationBar(containerColor = Naive) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = screen.itemImage), contentDescription = null) },
                label = {
                    Text(
                        text = stringResource(id = screen.resourceId),
                        fontFamily = Poppins
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Blue,
                    selectedTextColor = Blue,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = Grey,
                    unselectedTextColor = Grey
                )
            )
        }
    }
}
