package com.example.test_task_hw.data

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ConfigApiImpl @Inject constructor(
    private val config: FirebaseRemoteConfig
) : ConfigAPI {
    suspend fun updateLatestValues() {
        config.fetchAndActivate().await()
    }
    suspend fun updateLatestValues() {
        config["asdf"]
    }
}