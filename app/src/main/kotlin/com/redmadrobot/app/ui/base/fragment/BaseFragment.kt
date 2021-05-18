package com.redmadrobot.app.ui.base.fragment

import android.view.View
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.redmadrobot.app.App
import com.redmadrobot.app.di.AppProvider
import com.redmadrobot.app.ui.base.events.EventError
import com.redmadrobot.app.ui.base.events.EventMessage
import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.extensions.lifecycle.Event

open class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {
    protected val appComponent: AppProvider by lazy {
        (requireActivity().applicationContext as App).getApplicationProvider()
    }

    @CallSuper
    protected open fun onEvent(event: Event) {
        when (event) {
            is EventMessage -> showMessage(event.message)
            is EventError -> showError(event.errorMessage)
            is EventNavigateTo -> navigateTo(event.direction)
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun showError(errorMessage: String) {
        val contentView = requireActivity().findViewById<View>(android.R.id.content)
        Snackbar.make(contentView, errorMessage, Snackbar.LENGTH_SHORT).show()
    }

    protected fun navigateTo(direction: NavDirections) {
        findNavController().navigate(direction)
    }
}
