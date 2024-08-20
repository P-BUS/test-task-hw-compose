package com.books.app.presentation.screens.details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.books.app.R
import com.books.app.data.model.Book
import com.books.app.presentation.screens.home.HorizontalCarousel
import com.books.app.presentation.theme.Grey
import kotlinx.coroutines.launch
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    bookId: Int,
    navigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sendEvent: (DetailsAction) -> Unit = viewModel.send

    LaunchedEffect(Unit) {
        viewModel.send(DetailsAction.InitialLoading(bookId))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (uiState.isLoading) {
            LoadingState()
        } else {
            DetailsLayout(
                books = uiState.books,
                likedBooks = uiState.likedBooks,
                booksSize = uiState.books.size,
                navigateBack = navigateBack,
                sendEvent = sendEvent,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsLayout(
    books: List<Book>,
    likedBooks: List<Book>,
    booksSize: Int,
    navigateBack: () -> Unit,
    sendEvent: (DetailsAction) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val pagerState = rememberPagerState { booksSize }
    val pagerHorizontalPadding: Dp = (configuration.screenWidthDp.dp - 200.dp) / 2
    val bottomSheetHeight = (configuration.screenHeightDp / 2).dp
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = false,
            initialValue = SheetValue.PartiallyExpanded,
            skipHiddenState = true
        )
    )
    var item by remember { mutableStateOf(Book()) }

    LaunchedEffect(key1 = pagerState.currentPage) {
        item = books[pagerState.currentPage]
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        Column(
            modifier = Modifier
                .background(Color.Black)
                .align(Alignment.TopCenter)
        ) {
            TopAppBar(
                title = { /*TODO*/ },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color.White
                        )
                    ) {
                        Icons.Default.ArrowBack
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier,
                contentPadding = PaddingValues(horizontal = pagerHorizontalPadding),
                pageSpacing = 8.dp,
                pageSize = PageSize.Fixed(pageSize = 200.dp),
            ) { pageIndex ->
                val pageOffset by remember {
                    derivedStateOf {
                        pagerState.currentPageOffsetFraction + (pagerState.currentPage - pageIndex)
                    }
                }
                val scaleFactor = 1f - (abs(pageOffset) * 0.2f).coerceIn(0f, 0.2f)
                val imageSize by animateFloatAsState(
                    targetValue = scaleFactor,
                    label = "carousel_size_animation"
                )
                val book = books[pageIndex]
                HeaderCarouselCard(
                    imageUrl = book.coverUrl,
                    bookName = book.name,
                    author = book.author,
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = imageSize
                            scaleY = imageSize
                        }
                )
            }
        }
        BottomSheetScaffold(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            scaffoldState = sheetState,
            sheetDragHandle = null,
            sheetPeekHeight = bottomSheetHeight,
            sheetSwipeEnabled = true,
            sheetContent = {
                Row {
                    listOf(
                        Triple(item.views, null, stringResource(R.string.readers)),
                        Triple(item.likes, null, stringResource(R.string.likes)),
                        Triple(item.quotes, null, stringResource(R.string.quotes)),
                        Triple(item.genre, R.drawable.paper_icon, stringResource(R.string.genre)),
                    )
                        .forEach { details ->
                            DetailsDescriptionElement(
                                detailInfo = details.first,
                                imageId = details.second,
                                description = details.third,
                            )
                        }
                }
                BaseHorizontalDivider(color = Grey)
                Column {
                    Text(text = "Summary")
                    Text(
                        text = item.summary,
                    )
                }
                BaseHorizontalDivider(color = Grey)
                HorizontalCarousel(
                    title = "You will also like",
                    books = likedBooks,
                    onCardClick = { bookId ->
                        scope.launch {
                            pagerState.scrollToPage(bookId)
                        }
                    }
                )
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Text(text = "Read now")
                }
            },
            content = {}
        )
    }
}