package com.books.app.presentation.screens.home

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.app.data.model.Book
import com.books.app.data.model.TopBannerSlide
import com.books.app.domain.GetBooksStreamUseCase
import com.books.app.domain.GetTopBannerStreamUseCase
import com.books.app.presentation.navigation.NavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@Immutable
data class HomeUiState(
    val isLoading: Boolean = true,
    val sortedBooks: Map<String, List<Book>> = emptyMap(),
    val bannerSlides: List<TopBannerSlide> = emptyList(),
    val error: String? = null,
    val navigationAction: NavigationAction = NavigationAction.None
)

sealed class HomeAction {
    data class OnBookClick(val bookId: Int) : HomeAction()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    getBooksStreamUseCase: GetBooksStreamUseCase,
    getTopBannerStreamUseCase: GetTopBannerStreamUseCase,
) : ViewModel() {
    private val _sortedBooks = getBooksStreamUseCase()
    private val _bannerSlides = getTopBannerStreamUseCase()
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow(null)
    private val _navigationAction =
        MutableStateFlow<NavigationAction>(NavigationAction.None) // simple solution to not create more complex navigation with manager
    val uiState: StateFlow<HomeUiState> = combine(
        _sortedBooks,
        _bannerSlides,
        _isLoading,
        _error,
        _navigationAction
    ) { sortedBooks, bannerSlides, isLoading, error, navigationAction  ->
        HomeUiState(
            isLoading = isLoading,
            sortedBooks = sortedBooks,
            bannerSlides = bannerSlides,
            error = error,
            navigationAction = navigationAction,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )
    val send: (HomeAction) -> Unit = { action ->
        when (action) {
            is HomeAction.OnBookClick -> _navigationAction.update {
                NavigationAction.NavigateToDetails(action.bookId)
            }
        }
    }
}