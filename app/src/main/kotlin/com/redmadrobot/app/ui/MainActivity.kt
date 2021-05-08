package com.redmadrobot.app.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.ActivityMainBinding
import com.redmadrobot.app.di.AppComponent
import com.redmadrobot.app.ui.base.activity.BaseActivity
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
        if (event is EventNavigateTo) {
            findNavController(R.id.nav_host_fragment).navigate(event.direction)
        } else {
            Timber.e(IllegalArgumentException("Unknown Event Type"))
        }
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
