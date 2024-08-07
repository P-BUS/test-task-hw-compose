package com.books.app.presentation.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.books.app.presentation.navigation.Home
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(Home)
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "this is a Splash screen")
    }
}