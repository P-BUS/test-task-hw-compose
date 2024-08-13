package com.books.app.domain

import com.books.app.data.config.ConfigServiceAPI
import com.books.app.data.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLikedBooksStreamUseCase @Inject constructor(
    private val configServiceApi: ConfigServiceAPI
) {
    operator fun invoke(): Flow<List<Book>> =
        configServiceApi.config.map { config ->
            config.youWillLikeSection.mapNotNull { bookId ->
                config.books.find { it.id == bookId }
            }
        }
}