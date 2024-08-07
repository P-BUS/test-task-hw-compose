package com.example.test_task_hw.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.test_task_hw.presentation.screens.details.DetailsScreen
import com.example.test_task_hw.presentation.screens.home.HomeScreen
import com.example.test_task_hw.presentation.screens.splash.SplashScreen

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        navController = navController,
        startDestination = Splash
    ) {
        composable<Splash> {
            SplashScreen( /* ... */)
        }
        composable<Home> {
            HomeScreen( /* ... */)
        }
        composable<Details> {
            DetailsScreen( /* ... */)
        }
    }
}