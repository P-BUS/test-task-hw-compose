package com.books.app.di

import com.books.app.data.ConfigAPI
import com.books.app.data.ConfigApiImpl
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
        configAPIImpl: ConfigApiImpl,
    ): ConfigAPI
}