package com.books.app.data.config

import com.books.app.data.model.RemoteConfig
import com.books.app.utils.StringUtils.toDataClassOrNull
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigService @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    private val scope: CoroutineScope
) : ConfigServiceAPI {
    private val _config = MutableStateFlow(RemoteConfig())
    override val config: StateFlow<RemoteConfig> = _config.asStateFlow()

    override suspend fun loadConfig() {
        scope.launch {
            getConfigStream()
                .filterNotNull()
                .filterNot { it.books.isEmpty() }
                .collect { updatedConfig ->
                    _config.update { updatedConfig }
                }
        }
    }

    private suspend fun getConfigStream(): Flow<RemoteConfig?> = callbackFlow {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val jsonString = remoteConfig.getString(HOME_BOOKS_KEY)
                val config: RemoteConfig? = jsonString.toDataClassOrNull()
                trySend(config)
            } else {
                trySend(null)
            }
        }
        awaitClose { }
    }
    // TODO: to add addOnConfigUpdateListener to listen the updates in real time
    companion object {
        private const val HOME_BOOKS_KEY = "json_data"
    }
}