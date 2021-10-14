package com.jatezzz.tvmaze.common

import android.content.Context
import android.content.SharedPreferences

const val PREFS_NAME = "isFirstEntry"

class SharedPreference(val context: Context) {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(status: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(PREFS_NAME, status)
        editor.apply()
    }

    fun getValueBoolean(): Boolean {
        return sharedPref.getBoolean(PREFS_NAME, false)
    }

}
