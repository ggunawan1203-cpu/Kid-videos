package com.example.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.BuildConfig

class ApiKeyManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("kids_creator_settings", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_GEMINI_API_KEY = "gemini_api_key"
        private const val DEFAULT_PLACEHOLDER = "MY_GEMINI_API_KEY"
    }

    /**
     * Gets the active Gemini API Key:
     * 1. Check custom personal key saved by user in-app
     * 2. Fall back to BuildConfig.GEMINI_API_KEY if configured and not placeholder
     */
    fun getApiKey(): String {
        val userSavedKey = prefs.getString(KEY_GEMINI_API_KEY, "")?.trim() ?: ""
        if (userSavedKey.isNotEmpty()) {
            return userSavedKey
        }
        val buildKey = BuildConfig.GEMINI_API_KEY.trim()
        if (buildKey.isNotEmpty() && buildKey != DEFAULT_PLACEHOLDER) {
            return buildKey
        }
        return ""
    }

    fun getCustomApiKey(): String {
        return prefs.getString(KEY_GEMINI_API_KEY, "") ?: ""
    }

    fun saveApiKey(apiKey: String) {
        prefs.edit().putString(KEY_GEMINI_API_KEY, apiKey.trim()).apply()
    }

    fun hasValidKey(): Boolean {
        val key = getApiKey()
        return key.isNotEmpty() && key != DEFAULT_PLACEHOLDER
    }

    fun isUsingCustomKey(): Boolean {
        val userSavedKey = prefs.getString(KEY_GEMINI_API_KEY, "")?.trim() ?: ""
        return userSavedKey.isNotEmpty()
    }

    fun clearCustomKey() {
        prefs.edit().remove(KEY_GEMINI_API_KEY).apply()
    }
}
