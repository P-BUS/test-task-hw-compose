package com.books.app.domain

import com.books.app.data.config.ConfigServiceAPI
import com.books.app.data.model.TopBannerSlide
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTopBannerStreamUseCase @Inject constructor(
    private val configServiceApi: ConfigServiceAPI
) {
    // TODO: to add try catch
    operator fun invoke(): Flow<List<TopBannerSlide>> =
        configServiceApi.config.map { config ->
            config.topBannerSlides
        }
}