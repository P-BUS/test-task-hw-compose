package com.books.app.data.config

import com.books.app.data.model.RemoteConfig
import kotlinx.coroutines.flow.StateFlow

interface ConfigServiceAPI {
    suspend fun loadConfig()
    val config: StateFlow<RemoteConfig>
}