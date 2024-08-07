package com.books.app.data

import android.util.Log
import com.books.app.data.model.RemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ConfigApiImpl @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) : ConfigAPI {
    private val _config = MutableStateFlow(RemoteConfig())
    override val config: StateFlow<RemoteConfig> = _config.asStateFlow()

    override fun fetchConfig() {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val jsonString = remoteConfig.getString(HOME_BOOKS_KEY)
                val config: RemoteConfig? = jsonString.toDataClassOrNull()
                config?.let {
                    _config.update { it }
                }
            }
        }
    }

    companion object {
        private const val HOME_BOOKS_KEY = "json_data"
    }
}

inline fun <reified T> String.toDataClassOrNull(ignoreUnknownKeys: Boolean = true): T? {
    val json = Json {
        this.ignoreUnknownKeys = ignoreUnknownKeys
        this.explicitNulls = false
    }
    return try {
        json.decodeFromString(this)
    } catch (e: Exception) {
        Log.e("ConfigApiImpl", "error: $e")
        null
    }
}