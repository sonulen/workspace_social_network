package com.redmadrobot.app.ui

import android.os.Bundle
import com.redmadrobot.app.databinding.ActivityMainBinding
import com.redmadrobot.app.di.AppComponent
import com.redmadrobot.app.ui.base.activity.BaseActivity
import com.redmadrobot.app.utils.extension.dispatchApplyWindowInsetsToChild

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (appComponent as AppComponent).inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityStartContainerScreens.dispatchApplyWindowInsetsToChild()
    }
}
