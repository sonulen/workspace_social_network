package com.redmadrobot.app.ui

import android.os.Bundle
import android.widget.FrameLayout
import com.redmadrobot.app.R
import com.redmadrobot.app.di.AppComponent
import com.redmadrobot.app.ui.base.activity.BaseActivity
import com.redmadrobot.app.utils.extension.dispatchApplyWindowInsetsToChild

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (appComponent as AppComponent).inject(this)

        setContentView(R.layout.activity_main)
        findViewById<FrameLayout>(R.id.activity_start_container_screens).dispatchApplyWindowInsetsToChild()
    }
}
