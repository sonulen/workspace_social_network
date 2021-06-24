package com.redmadrobot.app.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.ActivityMainBinding
import com.redmadrobot.app.di.AppComponent
import com.redmadrobot.app.ui.base.activity.BaseActivity
import com.redmadrobot.app.ui.base.events.ErrorMessage
import com.redmadrobot.app.ui.base.events.EventError
import com.redmadrobot.app.ui.base.events.EventMessage
import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.app.utils.extension.dispatchApplyWindowInsetsToChild
import com.redmadrobot.extensions.lifecycle.Event
import com.redmadrobot.extensions.lifecycle.observe
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (appComponent as AppComponent).inject(this)
        val navController = findNavController(R.id.nav_host_fragment)
        binding.menuNavigation.setupWithNavController(navController)
        setupBottomNavigationBarVisibility(navController)

        binding.activityStartContainerScreens.dispatchApplyWindowInsetsToChild()

        observe(viewModel.eventsQueue, ::onEvent)
        viewModel.requestDirections()
    }

    private fun onEvent(event: Event) {
        when (event) {
            is EventMessage -> showMessage(event.message)
            is EventError -> showError(event.errorMessage)
            is EventNavigateTo -> navigateTo(event.direction)
            else -> Timber.e(IllegalArgumentException("Unknown Event Type"))
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showError(errorMessage: ErrorMessage) {
        val contentView = this.findViewById<View>(android.R.id.content)

        when (errorMessage) {
            is ErrorMessage.Text -> {
                Snackbar.make(contentView, errorMessage.message, Snackbar.LENGTH_SHORT).show()
            }

            is ErrorMessage.Id -> {
                Snackbar.make(contentView, getString(errorMessage.messageId), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateTo(direction: NavDirections) {
        findNavController(R.id.nav_host_fragment).navigate(direction)
    }

    private fun setupBottomNavigationBarVisibility(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.feedFragment,
                R.id.profileFragment,
                -> {
                    binding.menuNavigation.isVisible = true
                }

                else -> binding.menuNavigation.isVisible = false
            }
        }
    }
}
