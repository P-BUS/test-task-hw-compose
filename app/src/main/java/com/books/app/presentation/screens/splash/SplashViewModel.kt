package com.books.app.presentation.screens.splash

import androidx.lifecycle.ViewModel
import com.books.app.data.ConfigAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    configAPI: ConfigAPI
) : ViewModel() {

    init {
        configAPI.fetchConfig {
            it?.let {
                val config = it
                config
            }
        }
    }

}