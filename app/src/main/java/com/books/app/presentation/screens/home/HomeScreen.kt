package com.books.app.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.books.app.data.model.Book
import com.books.app.presentation.theme.NunitoSans

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val uiState by viewModel.config.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val sortedBooks = uiState?.books?.groupBy { it.genre }
        sortedBooks?.forEach { sortedBook ->
            item {
                HorizontalCarousel(
                    title = sortedBook.key,
                    books = sortedBook.value
                )
            }
        }
    }
}

@Composable
fun HorizontalCarousel(
    title: String,
    books: List<Book>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                lineHeight = 17.6.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = NunitoSans,
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(books) { book ->
                CarouselCard(
                    url = book.coverUrl,
                    text = book.name
                )
            }
        }
    }
}

@Composable
fun CarouselCard(
    url: String,
    text: String,
) {
    Column(
        modifier = Modifier
            .width(120.dp)
    ) {
        BaseImage(
            url = url,
            modifier = Modifier
                .height(150.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                lineHeight = 17.6.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = NunitoSans,
            )
        )
    }
}

