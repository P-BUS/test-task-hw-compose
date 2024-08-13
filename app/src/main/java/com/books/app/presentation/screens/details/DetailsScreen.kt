package com.books.app.presentation.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.books.app.R
import com.books.app.data.model.Book
import com.books.app.presentation.screens.home.HorizontalCarousel
import com.books.app.presentation.theme.Grey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    bookId: Int,
    navigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var itemIndex by remember { mutableIntStateOf(0) }
    val books by remember { mutableStateOf(uiState.books) }
    var item by remember { mutableStateOf(Book()) }
    val bottomSheetHeight = (LocalConfiguration.current.screenHeightDp / 2).dp
    val pagerState = rememberPagerState { books.size }
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = false,
            initialValue = SheetValue.PartiallyExpanded,
            skipHiddenState = true
        )
    )

    LaunchedEffect(Unit) {
        // TODO: to move param getting to viewModel with savedStateHandle
        viewModel.updateBooksById(bookId)
    }

    LaunchedEffect(itemIndex) {
        if (books.isNotEmpty()) {
            item = books[itemIndex]
        }
    }

    Column(Modifier.background(Color.Black)) {
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
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            itemIndex = page
            HeaderCarouselCard(item.coverUrl, item.name, item.author)
        }
        BottomSheetScaffold(
            scaffoldState = sheetState,
            sheetDragHandle = null,
            sheetPeekHeight = bottomSheetHeight,
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
                    Text(text = item.summary)
                }
                BaseHorizontalDivider(color = Grey)
                HorizontalCarousel(
                    title = "You will also like",
                    books = emptyList(),
                    onCardClick = {})
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Text(text = "Read now")
                }
            },
            content = {}
        )
    }
}