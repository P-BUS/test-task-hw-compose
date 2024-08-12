package com.books.app.presentation.navigation

sealed class NavigationAction {
    data class NavigateToDetails(val id: Int) : NavigationAction()
    data class NavigateToRead(val id: Int) : NavigationAction()
    data object GoBack : NavigationAction()
    data object None : NavigationAction()
}