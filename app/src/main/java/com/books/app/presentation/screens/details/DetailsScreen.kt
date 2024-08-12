package com.books.app.presentation.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    bookId: Int,
    navigateBack: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "This is book $bookId")
    }
}