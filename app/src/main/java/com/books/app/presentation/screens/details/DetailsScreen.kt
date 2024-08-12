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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.books.app.presentation.screens.home.HorizontalCarousel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    bookId: Int,
    navigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val books = uiState.books
    val pagerState = rememberPagerState { books.size }
    val sheetState = rememberBottomSheetScaffoldState()

    LaunchedEffect(Unit) {
        viewModel.updateBooksById(bookId)
        sheetState.bottomSheetState.partialExpand()
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
                IconButton(onClick = navigateBack) {
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
            val item = books[page]
            HeaderCarouselCard(item.coverUrl, item.name, item.author)
        }

        BottomSheetScaffold(
            scaffoldState = sheetState,
            sheetDragHandle = null,
            sheetContent = {
                Row {
                }
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 2.dp,
                    color = Color.White
                )
                Column {
                    Text(text = "")
                    Text(text = "")
                }
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 2.dp,
                    color = Color.White
                )
                HorizontalCarousel(title = "", books = emptyList(), onCardClick = {})
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Text(text = "Read now")
                }
            }
        ) {
            Row {
            }
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = Color.White
            )
            Column {
                Text(text = "")
                Text(text = "")
            }
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = Color.White
            )
            HorizontalCarousel(title = "", books = emptyList(), onCardClick = {})
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "Read now")
            }
        }
    }
}