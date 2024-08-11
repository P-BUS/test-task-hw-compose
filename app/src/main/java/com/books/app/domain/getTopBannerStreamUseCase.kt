package com.books.app.domain

import com.books.app.data.config.ConfigServiceAPI
import com.books.app.data.model.TopBannerSlide
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTopBannerStreamUseCase @Inject constructor(
    private val configServiceApi: ConfigServiceAPI
) {
    operator fun invoke(): Flow<List<TopBannerSlide>> = flow {
        val topBannerSlides = configServiceApi.config.value
            .topBannerSlides
        emit(topBannerSlides)
    }
}