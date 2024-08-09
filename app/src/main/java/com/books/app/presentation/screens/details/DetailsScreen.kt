package com.books.app.presentation.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    bookId: Int,
    navigateBack: () -> Unit,
) {
    Box() {
        Text(text = "This is details screen")
    }
}