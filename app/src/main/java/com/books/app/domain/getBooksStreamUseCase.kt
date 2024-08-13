package com.books.app.domain

import com.books.app.data.config.ConfigServiceAPI
import com.books.app.data.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBooksByIdStreamUseCase @Inject constructor(
    private val configServiceApi: ConfigServiceAPI
) {
    operator fun invoke(bookId: Int): Flow<List<Book>> =
        configServiceApi.config.map { config ->
            val allBooks = config.books
            val book = allBooks.find { it.id == bookId }
            if (book != null) {
                listOf(book) + allBooks.filterNot { it == book }
            } else {
                allBooks
            }
        }
}