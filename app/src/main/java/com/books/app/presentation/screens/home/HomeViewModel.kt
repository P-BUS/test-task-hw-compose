package com.books.app.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.books.app.data.config.ConfigServiceAPI
import com.books.app.data.model.RemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    configServiceApi: ConfigServiceAPI
) : ViewModel() {
    val config: StateFlow<RemoteConfig?> = configServiceApi.config

}

