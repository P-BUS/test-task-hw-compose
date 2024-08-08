package com.books.app.di

import com.books.app.data.config.ConfigServiceAPI
import com.books.app.data.config.ConfigService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InterfacesModule {
    @Binds
    @Singleton
    abstract fun bindConfigAPI(
        configServiceImpl: ConfigService,
    ): ConfigServiceAPI
}