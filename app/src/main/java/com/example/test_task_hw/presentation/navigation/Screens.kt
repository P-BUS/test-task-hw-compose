package com.example.test_task_hw.presentation.navigation

import kotlinx.serialization.Serializable

// Types
@Serializable
object Splash

@Serializable
object Home

@Serializable
data class Details(val id: String)