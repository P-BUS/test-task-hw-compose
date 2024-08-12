package com.books.app.presentation.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.books.app.presentation.screens.home.BaseImage

@Composable
fun HeaderCarouselCard(
    imageUrl: String,
    bookName: String,
    author: String,
) {
    Column(
        modifier = Modifier
            .width(200.dp)
    ) {
        BaseImage(
            url = imageUrl,
            modifier = Modifier
                .height(250.dp)
                .width(200.dp)
        )
        Text(text = bookName, color = Color.White)
        Text(text = author, color = Color.White)
    }
}