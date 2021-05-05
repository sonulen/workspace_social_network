package com.redmadrobot.app.di.android

import android.content.Context
import android.content.SharedPreferences

interface AndroidToolsProvider {
    fun context(): Context
    fun sharedPrefs(): SharedPreferences
}
