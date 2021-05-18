package com.redmadrobot.app.ui.base.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.redmadrobot.app.App
import com.redmadrobot.app.di.AppProvider

open class BaseActivity : AppCompatActivity() {
    protected val appComponent: AppProvider by lazy {
        (applicationContext as App).getApplicationProvider()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}
