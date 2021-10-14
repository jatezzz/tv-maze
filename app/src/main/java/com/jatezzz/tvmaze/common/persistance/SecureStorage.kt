package com.jatezzz.tvmaze.common.persistance

import android.content.SharedPreferences
import androidx.core.content.edit

enum class KeyType(val value: String) {
    PASSWORD("PASSWORD"),
    DO_NOT_ASK_AGAIN("DO_NOT_ASK_AGAIN"),
}

object SecureStorage {

    private val storage: SharedPreferences by lazy {
        AndroidStorageProvider.preferences("STORAGE")
    }

    fun getString(key: KeyType, defaultValue: String = ""): String =
        storage.getString(key.value, defaultValue) ?: ""

    fun putString(key: KeyType, value: String) = storage.edit { putString(key.value, value) }

    fun getBoolean(key: KeyType, defaultValue: Boolean = false): Boolean =
        storage.getBoolean(key.value, defaultValue)

    fun putBoolean(key: KeyType, value: Boolean) = storage.edit { putBoolean(key.value, value) }

    fun contains(key: KeyType): Boolean = storage.contains(key.value)

}
