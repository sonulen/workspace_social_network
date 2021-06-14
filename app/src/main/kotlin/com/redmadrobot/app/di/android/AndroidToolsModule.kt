package com.redmadrobot.app.di.android

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
object AndroidToolsModule {
    private const val SHARED_PREFS_NAME = "workspace_prefs"

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideGeocoder(context: Context): Geocoder = Geocoder(context, Locale.getDefault())
}
