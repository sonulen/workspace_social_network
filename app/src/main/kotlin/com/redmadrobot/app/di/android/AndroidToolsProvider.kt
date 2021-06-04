package com.redmadrobot.app.di.android

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager

interface AndroidToolsProvider {
    fun context(): Context
    fun sharedPrefs(): SharedPreferences
    fun connectivityManager(): ConnectivityManager
}
