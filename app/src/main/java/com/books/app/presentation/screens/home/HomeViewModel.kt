package com.books.app.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.books.app.data.ConfigAPI
import com.books.app.data.model.RemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val configApi: ConfigAPI
) : ViewModel() {
    private val _config = MutableStateFlow(RemoteConfig())
    val config: StateFlow<RemoteConfig> = _config.asStateFlow()

    fun updateConfig() {
        _config.update { configApi.config.value }
    }

}

