package com.books.app.domain

import com.books.app.data.config.ConfigServiceAPI
import com.books.app.data.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBooksByIdStreamUseCase @Inject constructor(
    private val configServiceApi: ConfigServiceAPI
) {
    operator fun invoke(bookId: Int): Flow<List<Book>> = flow {
        val allBooks = configServiceApi.config.value.books
        val book = allBooks.find { it.id == bookId }
        val updatedBooks = if (book != null) {
            listOf(book) + allBooks.filterNot { it == book }
        } else {
            allBooks
        }
        emit(updatedBooks)
    }
}