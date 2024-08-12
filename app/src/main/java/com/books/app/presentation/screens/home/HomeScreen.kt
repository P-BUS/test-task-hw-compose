package com.books.app.presentation.screens.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.books.app.data.model.TopBannerSlide
import com.books.app.presentation.navigation.NavigationAction
import com.books.app.presentation.theme.NunitoSans
import com.books.app.presentation.theme.TransparentLight70
import kotlinx.coroutines.delay

private const val DELAY_TIME = 3000L

@Composable
fun HomeScreen(
    navigateToDetails: (Int) -> Unit,
    viewModel: HomeViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState.navigationAction) {
        when (val action = uiState.navigationAction) {
            is NavigationAction.NavigateToDetails -> navigateToDetails(action.id)
            else -> {}
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .statusBarsPadding(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
    ) {
        item {
            Text(text = "Library")
        }
        item {
            BannerSlider(uiState.bannerSlides)
        }

        uiState.sortedBooks.forEach { sortedBook ->
            item {
                HorizontalCarousel(
                    title = sortedBook.key,
                    books = sortedBook.value,
                    onCardClick = { bookId ->
                        viewModel.send(HomeAction.OnBookClick(bookId))
                    }
                )
            }
        }
    }
}

@Composable
fun BannerSlider(
    bannerSlides: List<TopBannerSlide>
) {
    val fakeListSize = 1000
    val initialPage = fakeListSize / 2
    val pagerState = rememberPagerState(
        pageCount = { fakeListSize },
        initialPage = initialPage
    )
    LaunchedEffect(Unit) {
        while (true) {
            with(pagerState) {
                val targetPage = if (canScrollForward) currentPage + 1 else 0
                delay(DELAY_TIME)
                animateScrollToPage(
                    page = targetPage,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }
    }
    Box {
        var actualPageIndex by remember { mutableIntStateOf(0) }
        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp
        ) { pageIndex ->
            if (bannerSlides.isNotEmpty()) {
                val index = pageIndex % bannerSlides.size
                actualPageIndex = index
                SliderCard(
                    imageUrl = bannerSlides[index].cover
                )
            }
        }
        PagerDotIndicators(
            pageCount = bannerSlides.size,
            currentPageIndex = actualPageIndex,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
        )
    }
}

@Composable
fun SliderCard(
    imageUrl: String,
) {
    BaseImage(
        url = imageUrl,
        modifier = Modifier
            .height(160.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
    )
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
                .fillMaxWidth()
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

