package com.books.app.presentation.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.books.app.presentation.screens.home.BaseImage

@Composable
fun HeaderCarouselCard(
    imageUrl: String,
    bookName: String,
    author: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BaseImage(
            url = imageUrl,
            modifier = Modifier
                .height(250.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = bookName,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = author,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun BaseHorizontalDivider(
    color: Color = Color.White
) {
    Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp,
        color = color
    )
}

@Composable
fun DetailsDescriptionElement(
    detailInfo: String,
    imageId: Int?,
    description: String,
) {
    Column {
        Row {
            Text(text = detailInfo)
            imageId?.let { resourceId ->
                Image(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(resourceId),
                    contentDescription = null
                )
            }
        }
        Text(text = description)
    }
}

@Composable
fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.Center)
        )
    }
}