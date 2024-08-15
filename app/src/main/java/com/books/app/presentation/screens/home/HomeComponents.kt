package com.books.app.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.books.app.data.model.Book
import com.books.app.presentation.theme.NunitoSans
import com.books.app.presentation.theme.TransparentLight70

@Composable
fun BaseImage(
    url: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

@Composable
fun PagerDotIndicators(
    pageCount: Int,
    currentPageIndex: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .wrapContentSize()
    ) {
        repeat(pageCount) { currentPageIndicator ->
            val isSelected = currentPageIndicator == currentPageIndex
            val color = if (isSelected) Color.Red else Color.White
            Box(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Composable
fun HorizontalCarousel(
    title: String,
    books: List<Book>,
    onCardClick: (Int) -> Unit,
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
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(books) { book ->
                CarouselCard(
                    url = book.coverUrl,
                    text = book.name,
                    id = book.id,
                    onCardClick = onCardClick
                )
            }
        }
    }
}

@Composable
fun CarouselCard(
    url: String,
    text: String,
    id: Int,
    onCardClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .clickable { onCardClick(id) }
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
                color = TransparentLight70,
                fontSize = 16.sp,
                lineHeight = 17.6.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = NunitoSans,
            )
        )
    }
}

@Composable
fun SliderCard(
    imageUrl: String,
    bookId: Int,
    onCardClick: (Int) -> Unit
) {
    BaseImage(
        url = imageUrl,
        modifier = Modifier
            .clickable { onCardClick(bookId) }
            .height(160.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
    )
}