package com.books.app.presentation.screens.details

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.app.data.model.Book
import com.books.app.domain.GetBooksStreamUseCase
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
data class DetailsUiState(
    val isLoading: Boolean = true,
    val sortedBooks: Map<String, List<Book>> = emptyMap(),
    val error: String? = null,
    val navigationAction: NavigationAction = NavigationAction.None
)

sealed class DetailsAction {
    data class OnBookClick(val bookId: Int) : DetailsAction()
}

@HiltViewModel
class DetailsViewModel @Inject constructor(
    getBooksStreamUseCase: GetBooksStreamUseCase,
) : ViewModel() {
    private val _sortedBooks = getBooksStreamUseCase()
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow(null)
    private val _navigationAction =
        MutableStateFlow<NavigationAction>(NavigationAction.None) // simple solution to not create more complex navigation with manager
    val uiState: StateFlow<DetailsUiState> = combine(
        _sortedBooks,
        _isLoading,
        _error,
        _navigationAction,
    ) { sortedBooks, isLoading, error, navigationAction ->
        DetailsUiState(
            isLoading = isLoading,
            sortedBooks = sortedBooks,
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
            is DetailsAction.OnBookClick -> _navigationAction.update {
                NavigationAction.NavigateToDetails(action.bookId)
            }
        }
    }
}