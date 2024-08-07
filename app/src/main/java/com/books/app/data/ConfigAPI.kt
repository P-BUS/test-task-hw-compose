package com.books.app.data

import com.books.app.data.model.RemoteConfig

interface ConfigAPI {
    fun fetchConfig(onComplete: (RemoteConfig?) -> Unit)
}