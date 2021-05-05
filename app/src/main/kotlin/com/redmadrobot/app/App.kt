package com.redmadrobot.app

import android.app.Application
import com.redmadrobot.app.di.AppComponent
import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.utils.logging.PrettyLoggingTree
import timber.log.Timber

class App : Application() {
    private val appComponent: AppProvider by lazy {
        AppComponent.init(this)
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(PrettyLoggingTree(this))
    }

    fun getApplicationProvider(): AppProvider = appComponent
}
