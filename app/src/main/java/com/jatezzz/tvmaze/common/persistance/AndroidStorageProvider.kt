package com.jatezzz.tvmaze.common.persistance

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

@SuppressLint("StaticFieldLeak")
internal object AndroidStorageProvider {

    internal lateinit var appContext: Context

    internal fun preferences(name: String? = null): SharedPreferences {
        return getSharedPreferences(name)
    }

    private fun getSharedPreferences(name: String?) = if (name == null) {
        PreferenceManager.getDefaultSharedPreferences(appContext)
    } else {
        appContext.getSharedPreferences(name, Context.MODE_PRIVATE)
    }
}
