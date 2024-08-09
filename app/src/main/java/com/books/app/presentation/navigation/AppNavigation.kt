package com.books.app.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.books.app.presentation.screens.details.DetailsScreen
import com.books.app.presentation.screens.details.DetailsViewModel
import com.books.app.presentation.screens.home.HomeScreen
import com.books.app.presentation.screens.home.HomeViewModel
import com.books.app.presentation.screens.splash.SplashScreen
import com.books.app.presentation.screens.splash.SplashViewModel

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
            val splashViewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(
                viewModel = splashViewModel,
                navigateToHome = {
                    navController.navigate(Home)
                }
            )
        }
        composable<Home> {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = homeViewModel,
                navigateToDetails = { bookId ->
                    navController.navigate(Details(bookId))
                }
            )
        }
        composable<Details> { backStackEntry ->
            val detailsViewModel = hiltViewModel<DetailsViewModel>()
            val details: Details = backStackEntry.toRoute()
            DetailsScreen(
                viewModel = detailsViewModel,
                bookId = details.id,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}