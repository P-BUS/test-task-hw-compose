package com.books.app.presentation.screens.details

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.app.data.model.Book
import com.books.app.domain.GetBooksByIdStreamUseCase
import com.books.app.domain.GetLikedBooksStreamUseCase
import com.books.app.presentation.navigation.NavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class DetailsUiState(
    val isLoading: Boolean = true,
    val books: List<Book> = emptyList(),
    val likedBooks: List<Book> = emptyList(),
    val error: String? = null,
    val navigationAction: NavigationAction = NavigationAction.None
)

sealed class DetailsAction {
    data class InitialLoading(val bookId: Int) : DetailsAction()
    data class OnBookSelected(val bookId: Int) : DetailsAction()
    data class OnReadClick(val bookId: Int) : DetailsAction()
}

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getBooksByIdStreamUseCase: GetBooksByIdStreamUseCase,
    private val getLikedBooksStreamUseCase: GetLikedBooksStreamUseCase,
) : ViewModel() {
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    private val _likedBooks = MutableStateFlow<List<Book>>(emptyList())
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow(null)
    private val _navigationAction =
        MutableStateFlow<NavigationAction>(NavigationAction.None) // simple solution to not create more complex navigation with manager
    val uiState: StateFlow<DetailsUiState> = combine(
        _books,
        _likedBooks,
        _isLoading,
        _error,
        _navigationAction,
    ) { books, likedBooks, isLoading, error, navigationAction ->
        DetailsUiState(
            books = books,
            isLoading = isLoading,
            likedBooks = likedBooks,
            error = error,
            navigationAction = navigationAction,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DetailsUiState()
    )
    val send: (DetailsAction) -> Unit = { action ->
        when (action) {
            is DetailsAction.InitialLoading -> {
                updateBooksById(action.bookId)
                updateLikedBooks()
            }

            is DetailsAction.OnBookSelected -> updateBooksById(action.bookId)
            is DetailsAction.OnReadClick -> _navigationAction.update {
                NavigationAction.NavigateToRead(action.bookId)
            }
        }
    }

    private fun updateBooksById(bookId: Int) {
        viewModelScope.launch {
            _isLoading.update { true }
            getBooksByIdStreamUseCase(bookId).collect { books ->
                _books.update { books }
                if (books.isNotEmpty()) _isLoading.update { false }
            }
        }
    }

    private fun updateLikedBooks() {
        viewModelScope.launch {
            getLikedBooksStreamUseCase().collect { books ->
                _likedBooks.update { books }
            }
        }
    }
}