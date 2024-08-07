package com.example.test_task_hw.data

import com.example.test_task_hw.data.model.RemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ConfigApiImpl @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) : ConfigAPI {
    fun fetchConfig(remoteConfig: FirebaseRemoteConfig, onComplete: (RemoteConfig?) -> Unit) {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val jsonString = remoteConfig.getString(HOME_BOOKS_KEY)
                val config: RemoteConfig = jsonString.toDataClass()
                onComplete(config)
            } else {
                onComplete(null)
            }
        }
    }

    companion object {
        private const val HOME_BOOKS_KEY = "json_data"
    }
}

inline fun <reified T> String.toDataClass(ignoreUnknownKeys: Boolean = true): T {
    val json = Json {
        this.ignoreUnknownKeys = ignoreUnknownKeys
        this.explicitNulls = false
    }
    return json.decodeFromString(this)
}