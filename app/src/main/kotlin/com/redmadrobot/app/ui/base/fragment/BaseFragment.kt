package com.redmadrobot.app.ui.base.fragment

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.redmadrobot.app.App
import com.redmadrobot.app.di.AppProvider

open class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {
    protected val appComponent: AppProvider by lazy {
        (requireActivity().applicationContext as App).getApplicationProvider()
    }
}
