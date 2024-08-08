package com.books.app.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.books.app.R
import com.books.app.presentation.navigation.Home
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        viewModel.loadConfig()
    }
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(Home)
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.back),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.heart_back),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Text(text = "this is a Splash screen")
    }
}