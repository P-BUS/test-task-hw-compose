package com.books.app.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.app.data.config.ConfigServiceAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val configServiceAPI: ConfigServiceAPI
) : ViewModel() {

    suspend fun loadConfig() {
        viewModelScope.launch {
            configServiceAPI.loadConfig()
        }
    }
}