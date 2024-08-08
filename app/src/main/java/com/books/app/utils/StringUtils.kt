package com.books.app.utils

import android.util.Log
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object StringUtils {
    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> String.toDataClassOrNull(ignoreUnknownKeys: Boolean = true): T? {
        val json = Json {
            this.ignoreUnknownKeys = ignoreUnknownKeys
            this.explicitNulls = false
        }
        return try {
            json.decodeFromString(this)
        } catch (e: Exception) {
            Log.e("toDataClassOrNull", "error: $e")
            null
        }
    }
}