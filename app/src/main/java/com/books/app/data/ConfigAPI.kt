package com.books.app.data

import com.books.app.data.model.RemoteConfig
import kotlinx.coroutines.flow.StateFlow

interface ConfigAPI {
    val config: StateFlow<RemoteConfig>
    fun fetchConfig()
}