package com.books.app.presentation.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SplashScreen(
    viewModel: SplashViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "this is a screen")
    }
}