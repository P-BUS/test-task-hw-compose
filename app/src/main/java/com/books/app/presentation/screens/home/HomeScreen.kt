package com.books.app.presentation.screens.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.books.app.data.model.TopBannerSlide
import com.books.app.presentation.navigation.NavigationAction
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
            BannerSlider(
                bannerSlides = uiState.bannerSlides,
                onCardClick = { bookId ->
                    viewModel.send(HomeAction.OnBookClick(bookId))
                }
            )
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
    bannerSlides: List<TopBannerSlide>,
    onCardClick: (Int) -> Unit
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
                val item = bannerSlides[index]
                SliderCard(
                    imageUrl = item.cover,
                    bookId = item.bookId,
                    onCardClick = onCardClick
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