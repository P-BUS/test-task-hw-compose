package com.books.app.domain

import com.books.app.data.config.ConfigServiceAPI
import com.books.app.data.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSortedBooksStreamUseCase @Inject constructor(
    private val configServiceApi: ConfigServiceAPI
) {
    operator fun invoke(): Flow<Map<String, List<Book>>> =
        configServiceApi.config.map { config ->
            config.books
                .groupBy { it.genre }
        }
}