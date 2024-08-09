package com.books.app.domain

import com.books.app.data.config.ConfigServiceAPI
import com.books.app.data.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBooksStreamUseCase @Inject constructor(
    private val configServiceApi: ConfigServiceAPI
) {
    operator fun invoke(): Flow<Map<String, List<Book>>> = flow {
        val sortedBooks = configServiceApi.config.value
            .books
            .groupBy { it.genre }
        emit(sortedBooks)
    }
}